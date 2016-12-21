package com.xiaomi.mipushdemo.send;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

/**
 * 给指定用户
 */

public class MiSendToSome extends MiPostHandler implements MiPushSendMethod {

    private static MiSendToSome mInstance = new MiSendToSome();

    public static MiSendToSome getInstance() {
        return mInstance;
    }

    private MiSendToSome() {
    }


    @Override
    public void newPushMessage(MiPushData data, MiPushSendListener listener) {
        AsyncSender.getInstance().sendAsync(data, listener, this);
    }

    @Override
    public SomeMiPushData obtain() {
        return new SomeMiPushData(this);
    }

    @Override
    public void sendMethod(Sender sender, MiPushData data, MiPushSendListener listener) {
        try {
            SomeMiPushData sd = (SomeMiPushData) data;
            Result result = sender.send(data.build(), sd.regIds, 2);
            if (result.getErrorCode().getValue() == ErrorCode.Success.getValue()) {
                listener.onSuccess(result.getMessageId());
            } else {
                listener.onFailed(result.getErrorCode(), result.getReason(), null);
            }
        } catch (Exception e) {
            listener.onFailed(null, null, e);
        }
    }
}
