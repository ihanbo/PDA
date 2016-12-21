package com.xiaomi.mipushdemo.send;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

/**
 * 订阅用户推送
 */

public class MiSendTopic extends MiPostHandler implements MiPushSendMethod {

    private static MiSendTopic mInstance = new MiSendTopic();

    public static MiSendTopic getInstance() {
        return mInstance;
    }

    private MiSendTopic() {
    }


    @Override
    public void newPushMessage(MiPushData data, MiPushSendListener listener) {
        AsyncSender.getInstance().sendAsync(data, listener, this);
    }

    @Override
    public TopicMiPushData obtain() {
        return new TopicMiPushData(this);
    }

    @Override
    public void sendMethod(Sender sender, MiPushData data, MiPushSendListener listener) {
        try {
            TopicMiPushData sd = (TopicMiPushData) data;
            Result result = sender.broadcast(data.build(), sd.topic, 2);
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
