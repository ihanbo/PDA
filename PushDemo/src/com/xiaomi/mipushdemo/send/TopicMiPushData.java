package com.xiaomi.mipushdemo.send;


import java.util.ArrayList;
import java.util.List;

/**
 *  订阅消息
 */

public class TopicMiPushData extends MiPushData {
    public String topic ;

    TopicMiPushData(MiPostHandler postHandler) {
        super(postHandler);
    }


    public TopicMiPushData setTopic(String topic){
        this.topic =topic;
        return this;
    }

}
