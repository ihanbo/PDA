package com.yiche.net.adapter2;

import com.yiche.net.LL;
import com.yiche.net.NetCenter;
import com.yiche.net.NetParams;
import com.yiche.net.NetUtils;
import com.yiche.net.ReqBody;
import com.yiche.net.YCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Post请求
 */
public class OkPostRequest extends IOkRequest {
    protected OkPostRequest(ReqBody rb) {
        super(rb);
    }

    @Override
    protected okhttp3.RequestBody buildRequestBody() {
        return (RequestBody) OkNet.getInstance().getPostBody(rb);
    }

    @Override
    protected okhttp3.RequestBody wrapRequestBody(okhttp3.RequestBody requestBody, final YCallback callback) {
        if (callback == null||requestBody==null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {

                NetCenter.getDelivery().postResponse(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten, contentLength);
                    }
                });

            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(okhttp3.RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

}
