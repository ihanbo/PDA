package com.abit.han.pda.event;

import cn.bmob.v3.BmobObject;

/**
 * Created by ihanb on 2016/12/15.
 * 新消息事件
 */

public class NewSmsEvent extends BmobObject{
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
