package com.abit.han.pda.push;

import android.app.Application;

import com.abit.han.pda.push.umeng.UMPushCenter;

/**
 * 推送中心
 */

public class PushCenter implements IPush {

    public static final PushCenter instance = new PushCenter();


    private IPush realPush;
    public PushCenter() {
        this.realPush = new UMPushCenter();
    }

    @Override
    public void registerToRecievePush(final Application application) {
        realPush.registerToRecievePush(application);
    }

    @Override
    public void initSendPush(Application application) {
        //do nothing
    }

    @Override
    public void sendPush(PushData data,PushSendListener listener) {
        realPush.sendPush(data,listener);
    }
}
