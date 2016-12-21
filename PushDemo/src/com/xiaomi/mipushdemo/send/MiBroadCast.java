package com.xiaomi.mipushdemo.send;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

/**
 *  广播
 */

public class MiBroadCast extends MiPostHandler implements MiPushSendMethod{

    private static MiBroadCast mInstance = new MiBroadCast();
    public static MiBroadCast getInstance(){
        return mInstance;
    }

    private MiBroadCast() {
    }

    @Override
    public void newPushMessage(MiPushData data, MiPushSendListener listener) {
        AsyncSender.getInstance().sendAsync(data,listener,this);
    }

    @Override
    public void sendMethod(Sender sender,MiPushData data,MiPushSendListener listener) {
        try {
            Result result = sender.broadcastAll(data.build(),2);
            if(result.getErrorCode().getValue()== ErrorCode.Success.getValue()){
                listener.onSuccess(result.getMessageId());
            }else{
                listener.onFailed(result.getErrorCode(),result.getReason(),null);
            }
        } catch (Exception e) {
            listener.onFailed(null,null,e);
        }
    }
}
