package com.abit.han.pda.push;

import android.app.Application;

/**
 * Created by Han on 2016/12/18.
 */

public interface IPush {
    /** 注册接受推送 */
    void registerToRecievePush(Application application);
    /** 初始化发送推送 */
    void initSendPush(Application application);
    void sendPush(PushData data,PushSendListener listener);
}
