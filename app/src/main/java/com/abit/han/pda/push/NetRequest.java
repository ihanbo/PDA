package com.abit.han.pda.push;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 一个请求的请求体
 */

public class NetRequest {
    public static final int HTTP_GET = 1;
    public static final int HTTP_POST = 2;
    public int method = HTTP_GET;
    public String url;
    public String UserAgent;
    public Map<String, String> headers;
    public Map<String, String> params;


    public static NetRequest get() {
        NetRequest rb = new NetRequest();
        rb.method = HTTP_GET;
        return rb;
    }

    public static NetRequest post() {
        NetRequest rb = new NetRequest();
        rb.method = HTTP_POST;
        return rb;
    }

    public NetRequest url(String url) {
        this.url = url;
        return this;
    }
    public NetRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public NetRequest addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap();
        }
        params.put(key, val);
        return this;
    }

    public NetRequest addParams(String key, int value) {
        return addParams(key, String.valueOf(value));
    }

    public NetRequest addParams(String key, float value) {
        return addParams(key, String.valueOf(value));
    }

    public NetRequest addParams(String key, double value) {
        return addParams(key, String.valueOf(value));
    }

    public NetRequest addParams(String key, long value) {
        return addParams(key, String.valueOf(value));
    }

    public NetRequest headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public NetRequest addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}
