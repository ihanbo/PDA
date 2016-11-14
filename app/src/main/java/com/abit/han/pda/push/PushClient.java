package com.abit.han.pda.push;


import android.util.Log;

import com.yiche.net.NetCenter;
import com.yiche.net.NetParams;
import com.yiche.net.NetRes;
import com.yiche.net.NetworkResponse;
import com.yiche.net.ReqBody;
import com.yiche.net.callback.YCStringRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

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
        rb.addParams(NetParams.ExtraPostBody.create("application/json; charset=utf-8",postBody));
        // Send the post request and get the response
        NetCenter.newRequest(rb, new YCStringRequest() {
            @Override
            public void onResponse(NetRes<String> netResPonse) {
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
        rb.addParams(NetParams.ExtraPostBody.create("application/json; charset=utf-8",postBody));
        NetCenter.newRequest(rb, new YCStringRequest() {
            @Override
            public void onResponse(NetRes<String> netResPonse) {
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
