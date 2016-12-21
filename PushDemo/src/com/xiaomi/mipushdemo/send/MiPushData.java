package com.xiaomi.mipushdemo.send;

import com.xiaomi.xmpush.server.Message;

/**
 * 由MiPostHandler方法obtain()生产
 */

public class MiPushData {
    private Message msg;

    public String content = "test test";
    public String title = "test";
    MiPostHandler postHandler;

    MiPushData(MiPostHandler postHandler) {
        this.postHandler = postHandler;
    }

    public void sendToTarget(){
        if(postHandler==null){
            throw new RuntimeException("postHandler not set");
        }
        postHandler.newPushMessage(this);
    }

    public MiPushData setContent(String content) {
        this.content = content;
        return this;
    }

    public MiPushData setTitle(String title) {
        this.title = title;
        return this;
    }

    /*MiPushData setMiPostHandler(MiPostHandler handler){
        this.postHandler = handler;
        return this;
    }*/


    public Message build(){
        this.msg = new Message.Builder()
                .title(title)
                .description("小米推送").payload(content)
                .restrictedPackageName("com.abit.han.pda")
                .passThrough(1)     //消息使用透传方式
                .notifyType(1)      // 使用默认提示音提示
                .extra("flow_control", "4000")     // 设置平滑推送, 推送速度4000每秒(qps=4000)
                .build();
        return msg;
    }
}
