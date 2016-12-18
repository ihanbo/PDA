package com.abit.han.pda.push;

/**
 * 推送消息实体类
 */

public class PushData {
    public String title;
    public String content;
    public String tag;



    public PushData() {
    }

    public PushData setTag(String... tag) {
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

    public PushData setTitle(String title) {
        this.title = title;
        return this;
    }

    public PushData setContent(String content) {
        this.content = content;
        return this;
    }
}
