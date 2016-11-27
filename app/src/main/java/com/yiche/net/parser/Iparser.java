package com.yiche.net.parser;

import com.yiche.net.NetResultPac;
import com.yiche.net.NetworkResponse;

import java.io.IOException;

/**
 * CallBack的解析器
 * 解析数据的
 */
public interface Iparser<T> {
    NetResultPac<T> parse(NetworkResponse networkResponse);


    Iparser DEFAULT = new Iparser<byte[]>() {
         @Override
         public NetResultPac<byte[]> parse(NetworkResponse networkResponse) {
             try {
                 return NetResultPac.success(networkResponse.bytes(),networkResponse,"default parser no care result");
             } catch (IOException e) {
                 return NetResultPac.error(e,networkResponse);
             }
         }
     };
}
