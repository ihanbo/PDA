package com.abit.han.pda.event;

/**
 * Created by ihanb on 2016/12/15.
 * 新消息事件
 */

public class NewSmsEvent {
    public String content;
    public String sender;
    public String time;

    public NewSmsEvent(String content, String sender, String time) {
        this.content = content;
        this.sender = sender;
        this.time = time;
    }

    @Override
    public String toString() {
        return "消息{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
