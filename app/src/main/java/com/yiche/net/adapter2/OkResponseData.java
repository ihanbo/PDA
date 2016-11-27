package com.yiche.net.adapter2;

import com.yiche.net.IReponseData;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by ihanb on 2016/11/10.
 */

public class OkResponseData implements IReponseData {
    ResponseBody  okBody;

    public OkResponseData(ResponseBody okBody) {
        this.okBody = okBody;
    }

    @Override
    public byte[] bytes() throws IOException {
        return okBody.bytes();
    }

    @Override
    public InputStream byteStream() {
        return okBody.byteStream();
    }

    @Override
    public String string() throws IOException {
        return okBody.string();
    }
}
