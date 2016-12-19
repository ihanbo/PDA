package com.abit.han.pda.sendpush;

import android.app.Application;

/**
 * Created by Han on 2016/12/18.
 */

public interface ISendPush {
    /** 初始化发送推送 */
    void initSendPush(Application application);
    void sendPush(SendPushData data, SendPushSendListener listener);
}
