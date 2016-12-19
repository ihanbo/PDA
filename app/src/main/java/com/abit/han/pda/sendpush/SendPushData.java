package com.abit.han.pda.sendpush;

/**
 * 推送消息实体类
 */

public class SendPushData {
    public String title;
    public String content;
    public String tag;



    public SendPushData() {
    }

    public SendPushData setTag(String... tag) {
        if(tag==null||tag.length==0){
            return this;
        }
        StringBuilder sb = new StringBuilder(tag[0]);
        for (int i = 1; i < tag.length; i++) {
            sb.append(",").append(tag[i]);
        }
        this.tag = sb.toString();
        return this;
    }

    public SendPushData setTitle(String title) {
        this.title = title;
        return this;
    }

    public SendPushData setContent(String content) {
        this.content = content;
        return this;
    }
}
