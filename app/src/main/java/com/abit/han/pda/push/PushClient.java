package com.abit.han.pda.push;


import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yiche.net.NetCenter;
import com.yiche.net.NetResultPac;
import com.yiche.net.NetworkResponse;
import com.yiche.net.PostBody;
import com.yiche.net.ReqBody;
import com.yiche.net.callback.YCStringRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Han on 2016/10/15.
 */

public class PushClient {

    // The user agent
    protected final String USER_AGENT = "Mozilla/5.0";

    // This object is used for sending the post request to Umeng
    // The host
    protected static final String host = "http://msg.umeng.com";

    // The upload path
    protected static final String uploadPath = "/upload";

    // The post path
    protected static final String postPath = "/api/send";

    public boolean send(UmengNotification msg) throws Exception {
        String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
        msg.setPredefinedKeyValue("timestamp", timestamp);
        String url = host + postPath;
        String postBody = msg.getPostBody();
        String sign = PushHelper.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
        url = url + "?sign=" + sign;
        ReqBody rb = ReqBody.post().url(url);
        rb.addHeader("User-Agent", USER_AGENT);
        rb.setPostBody(PostBody.create("application/json; charset=utf-8",postBody));
        // Send the post request and get the response
//        post(url,postBody);

        NetCenter.newRequest(rb, new YCStringRequest() {
            @Override
            public void onResponse(NetResultPac<String> netResPonse) {
                NetworkResponse response = netResPonse.response;
                int status = response.statusCode;
                Log.i("hh","status: "+status);
                Log.i("hh","内容: "+netResPonse.result);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(String result) {

            }
        });
        return true;
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client ;
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        client = okHttpClientBuilder.build();
    }

    void post(String url, String json)  {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", USER_AGENT)
                .post(body)
                .build();
         client.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 e.printStackTrace();
             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {
                 int status = response.code();
                 Log.i("hh","status: "+status);
                 Log.i("hh","内容: "+response.body().string());
             }
         });

    }


    // Upload file with device_tokens to Umeng
    public String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
        // Construct the json string
        JSONObject uploadJson = new JSONObject();
        uploadJson.put("appkey", appkey);
        String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
        uploadJson.put("timestamp", timestamp);
        uploadJson.put("content", contents);
        // Construct the request
        String url = host + uploadPath;
        String postBody = uploadJson.toString();
        String sign = PushHelper.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
        url = url + "?sign=" + sign;
        ReqBody rb = ReqBody.post().url(url);
        rb.addHeader("User-Agent", USER_AGENT);
        rb.setPostBody(PostBody.create("application/json; charset=utf-8",postBody));
        NetCenter.newRequest(rb, new YCStringRequest() {
            @Override
            public void onResponse(NetResultPac<String> netResPonse) {
                NetworkResponse response = netResPonse.response;
                int status = response.statusCode;
                Log.i("hh","status: "+status);
                Log.i("hh","内容: "+netResPonse.result);
                String fileId;
                try {
                    JSONObject respJson = new JSONObject(netResPonse.result);
                    String ret = respJson.getString("ret");
                    if (!ret.equals("SUCCESS")) {
                        Log.i("hh","上传文件失败");
                    }else{
                        JSONObject data = respJson.getJSONObject("data");
                        fileId = data.getString("file_id");
                        Log.i("hh","File Id: "+fileId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(String result) {

            }
        });
        return null;
    }

}
