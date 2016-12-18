package com.abit.han.pda.event;

/**
 * 收到新的推送事件
 */

public class NewPushEvent {
    public String msg;
    public String custom;
    public String title;
    public String text;

    public NewPushEvent(String msg, String custom, String title, String text) {
        this.msg = msg;
        this.custom = custom;
        this.title = title;
        this.text = text;
    }


    @Override
    public String toString() {
        return "新推送 {" +
                "msg='" + msg + '\'' +
                ", custom='" + custom + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
