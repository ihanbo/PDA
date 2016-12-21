package com.xiaomi.mipushdemo.send;

import com.xiaomi.push.sdk.ErrorCode;

/**
 * 类似Handler、Message的设计，PushData由MiPostHandler方法obtain()生产
 */

public abstract class MiPostHandler {

    abstract void newPushMessage(MiPushData data, MiPushSendListener listener);
    void newPushMessage(MiPushData data){
        newPushMessage(data,null);
    }
    public MiPushData obtain(){
        MiPushData miPushData= new MiPushData(this);
        return miPushData;
    }


}
