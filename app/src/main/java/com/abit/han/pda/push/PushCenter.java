package com.abit.han.pda.push;

import com.abit.han.pda.event.NewSmsEvent;

/**
 * 推送中心
 */

public class PushCenter {

    /** 接收消息的Alias */
    public static final String MESSAGE_ALIAS = "message_need";

    public static void send(NewSmsEvent event,PushSendListener listener){
        Demo.sendAndroidCustomizedcast(MESSAGE_ALIAS, "13511097504新消息", event.toString(), listener);
    }
}
