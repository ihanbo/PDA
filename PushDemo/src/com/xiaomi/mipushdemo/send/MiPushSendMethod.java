package com.xiaomi.mipushdemo.send;

import com.xiaomi.xmpush.server.Sender;

/**
 * 不同的发送方法，如：广播、单播、订阅等
 */

public interface MiPushSendMethod {
    void sendMethod(Sender sender, MiPushData data , MiPushSendListener listener);
}
