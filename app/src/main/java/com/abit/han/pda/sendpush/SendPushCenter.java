package com.abit.han.pda.sendpush;

import android.app.Application;

import com.abit.han.pda.sendpush.umeng.UMPushCenter;

/**
 * 发送推送中心
 */

public class SendPushCenter implements ISendPush {
    public static final SendPushCenter mInstance = new SendPushCenter();


    private ISendPush realPush;
    public SendPushCenter() {
        this.realPush = UMPushCenter.getInstance();
    }

    @Override
    public void initSendPush(Application application) {
        //do nothing
    }

    @Override
    public void sendPush(SendPushData data, SendPushSendListener listener) {
        realPush.sendPush(data,listener);
    }
}
