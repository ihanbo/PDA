package com.abit.han.pda.recievepush;

import android.app.Application;

import com.abit.han.pda.sendpush.umeng.UMPushCenter;

/**
 * Created by ihanb on 2016/12/19.
 */

public class RecievePushCenter implements IRecievePush {
    public static final RecievePushCenter mInstance = new RecievePushCenter();


    private IRecievePush realRecievePush;

    public RecievePushCenter() {
        this.realRecievePush = UMPushCenter.getInstance();
    }

    @Override
    public void initRecievePush(Application application) {
        realRecievePush.initRecievePush(application);
    }
}
