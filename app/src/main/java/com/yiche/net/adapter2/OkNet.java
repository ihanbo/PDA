package com.yiche.net.adapter2;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yiche.net.Delivery;
import com.yiche.net.INet;
import com.yiche.net.IReponseData;
import com.yiche.net.LL;
import com.yiche.net.NetParams;
import com.yiche.net.NetUtils;
import com.yiche.net.NetworkResponse;
import com.yiche.net.ReqBody;
import com.yiche.net.YCallback;
import com.yiche.net.RequestObjectTag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by hanbo1 on 2016/4/5.
 */
public class OkNet implements INet {
    private OkHttpClient mClient;
    private Delivery delivery;

    private static OkNet instance;

    public static OkNet getInstance() {
        if (instance == null) {
            throw new RuntimeException("not inited!");
        }
        return instance;
    }

    public static OkNet inital(Context context, Delivery delivery) {
        instance = new OkNet();
        instance.init(context, delivery);
        return instance;
    }

    private OkNet() {
    }

    @Override
    public void init(Context context, Delivery delivery) {
        synchronized (OkNet.class) {
            if (mClient == null) {
                OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
                //cookie支持
                okHttpClientBuilder.cookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
                okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
                okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
                okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
                okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
                mClient = okHttpClientBuilder.build();
            }
        }
        java.net.CookieManager cookieManager = new java.net.CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        this.delivery = delivery;
    }

    @Override
    public void newRequest(ReqBody rb, YCallback callback) {
        IOkRequest okRequest = OkRequestFactory.create(rb);
        YCall yCall = new YCall(okRequest);
        if (callback != null) {
            callback.setRb(rb);
        }
        yCall.execute(callback, delivery);
    }

    @Override
    public NetworkResponse newRequest(ReqBody rb) throws Exception {
        IOkRequest okRequest = OkRequestFactory.create(rb);
        YCall yCall = new YCall(okRequest);
        NetworkResponse response = yCall.execute();
        return response;
    }

    @Override
    public void clearCookie() {
        CookieJar cookieJar = mClient.cookieJar();
        if (cookieJar != null && cookieJar instanceof CookieJarImpl) {
            CookieJarImpl cookie = (CookieJarImpl) cookieJar;
            cookie.getCookieStore().removeAll();
        }
    }

    @Override
    public void authCookie() {
        PersistentCookieStore cookieStore = getCookieStore();
        if (cookieStore == null || cookieStore.getCookies() == null || cookieStore.getCookies().isEmpty()) {
            return;
        }
        boolean cookieExpired = cookieStore.getCookies().get(0).expiresAt() <= System.currentTimeMillis();
        //TODO 过期怎么处理?
    }


    @Override
    public void cacelByTag(Object tag) {
        for (Call call : mClient.dispatcher().queuedCalls()) {
            RequestObjectTag realTag = ((ReqBody) call.request().tag()).requestTag;
            if (tag.equals(realTag.requestCancelKey)) {
                call.cancel();
            }
        }
        for (Call call : mClient.dispatcher().runningCalls()) {
            RequestObjectTag realTag = ((ReqBody) call.request().tag()).requestTag;
            if (tag.equals(realTag.requestCancelKey)) {
                call.cancel();
            }
        }
    }

    @Override
    public PersistentCookieStore getCookieStore() {
        CookieJar cookieJar = mClient.cookieJar();
        if (cookieJar == null || !(cookieJar instanceof CookieJarImpl)) {
            return null;
        }
        CookieJarImpl cookie = (CookieJarImpl) cookieJar;
        return cookie.getCookieStore();
    }

    @Override
    public Object getPostBody(final ReqBody reqBody) {
        if (reqBody == null) {
            return null;
        }
        //有postbody直接取posybody，忽略NetParams
        if (reqBody.postBody != null) {
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return MediaType.parse(reqBody.postBody.getMediaType());
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    Source source = null;
                    try {
                        source = Okio.source(reqBody.postBody.getIputStream());
                        sink.writeAll(source);
                    } finally {
                        Util.closeQuietly(source);
                    }
                }
            };
        }
        //取NetParams
        if (reqBody.params == null || !reqBody.params.isHasParams()) {
            //未设置的话返回一个空的
            FormBody.Builder builder = new FormBody.Builder();
            return builder.build();
        }
        if (!reqBody.params.isHasFileParams()) {
            //没有文件参数
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder, reqBody.params.urlParams);
            return builder.build();
        } else {
            //先添加表单参数
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.MIXED);
            //TODO 这个TYPE不知道设什么
            addParams(builder, reqBody.params.urlParams);
            // Add stream params
            for (ConcurrentHashMap.Entry<String, NetParams.MyStreamWrapper> entry : reqBody.params.streamParams.entrySet()) {
                NetParams.MyStreamWrapper stream = entry.getValue();
                byte[] data = input2byte(stream.inputStream);
                if (data != null) {
                    okhttp3.RequestBody byteBody = okhttp3.RequestBody.create(MediaType.parse(stream.contentType), data);
                    String customName = stream.name == null ? System.currentTimeMillis() + "" : stream.name;
                    builder.addFormDataPart(entry.getKey(), customName, byteBody);
                } else {
                    LL.w("process stream params error!!!!!!");
                }
            }
            // Add byte[] params
            for (ConcurrentHashMap.Entry<String, NetParams.MyBytesWrapper> entry : reqBody.params.bytesParams.entrySet()) {
                NetParams.MyBytesWrapper stream = entry.getValue();
                if (stream.bytes != null) {
                    okhttp3.RequestBody byteBody = okhttp3.RequestBody.create(MediaType.parse(stream.contentType), stream.bytes);
                    String customName = stream.customName == null ? System.currentTimeMillis() + "" : stream.customName;
                    builder.addFormDataPart(entry.getKey(), customName, byteBody);
                } else {
                    LL.w("process byte[] params error!!!!!!");
                }
            }
            // Add file params
            for (ConcurrentHashMap.Entry<String, NetParams.MyFileWrapper> entry : reqBody.params.fileParams.entrySet()) {
                NetParams.MyFileWrapper fileWrapper = entry.getValue();
                String key = entry.getKey();

                okhttp3.RequestBody fileBody = okhttp3.RequestBody.create(MediaType.parse(fileWrapper.contentType), fileWrapper.file);
                String customFileName = fileWrapper.customFileName == null ? System.currentTimeMillis() + fileWrapper.file.getName() : fileWrapper.customFileName;
                builder.addFormDataPart(key, customFileName, fileBody);
                //httpclient的写法：addPart(entry.getKey(), fileWrapper.file, fileWrapper.contentType, fileWrapper.customFileName);
            }
            return builder.build();
        }
    }

    @Override
    public NetworkResponse transformResponse(Object or) {
        if (or == null || !(or instanceof Response)) {
            return new NetworkResponse();
        }
        Response okHttpResponse = (Response) or;
        //状态码
        int statusCode = okHttpResponse.code();
        //内容
        IReponseData responseData = null;
        if (okHttpResponse.body() != null) {
            responseData = new OkResponseData(okHttpResponse.body());
        }
        //Head信息
        Map<String, String> heads = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        Headers responseHeaders = okHttpResponse.headers();
        if (responseHeaders != null && responseHeaders.size() > 0) {
            for (int i = 0; i < responseHeaders.size(); i++) {
                String name = responseHeaders.name(i), value = responseHeaders.value(i);
                heads.put(name, value);
            }
        }
        //响应时间
        long costTime = okHttpResponse.receivedResponseAtMillis() - okHttpResponse.sentRequestAtMillis();
        return new NetworkResponse(statusCode, responseData, heads, costTime);
    }


    private void addParams(MultipartBody.Builder builder, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
        }
    }

    private void addParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                try {
                    builder.add(key, params.get(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static final byte[] input2byte(InputStream inStream) {
        ByteArrayOutputStream swapStream = null;
        try {
            swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int rc = 0;
            while ((rc = inStream.read(buff)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            NetUtils.silentCloseStream(inStream);
            NetUtils.silentCloseStream(swapStream);
        }
    }

    public OkHttpClient getOkHttpClient() {
        if(mClient==null){
            throw new RuntimeException("not inited");
        }
        return mClient;
    }
}
