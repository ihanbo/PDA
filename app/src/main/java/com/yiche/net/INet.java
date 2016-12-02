package com.yiche.net;

import android.content.Context;
import com.yiche.net.adapter2.PersistentCookieStore;

/**
 * 网络引擎
 */
public interface INet {
    long DEFAULT_MILLISECONDS = 10000;
    /*   api方法 */
    /** 初始化 */
    void init(Context context, Delivery delivery);
    /** 异步请求 */
    void newRequest(ReqBody rb, YCallback callback);
    /** 同步请求 */
    NetworkResponse newRequest(ReqBody rb) throws Exception;
    /** 取消请求 */
    void cacelByTag(Object tag);
    /** 清除cookie */
    void clearCookie();
    /** 验证下cookie */
    void authCookie();

    PersistentCookieStore getCookieStore();

    /****************************************工具方法*****************/


    /** 根据请求生成网络架构的请求体，通过{@link NetParams}或{@link PostBody}*/
    Object getPostBody(ReqBody reqBody);
    /** 转化结果*/
    NetworkResponse transformResponse(Object otherResponse);

}
