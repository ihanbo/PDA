package com.abit.han.pda.event;

import android.os.Bundle;

import com.abit.han.pda.service.IserviceData;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.helper.GsonUtil;

/**
 * Created by ihanb on 2016/12/15.
 * 新消息事件
 */

public class NewSmsEvent extends BmobObject implements IserviceData{
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
        return "短信内容:" + content + ", 发送人:" + sender  +
                ", 时间:=" + time ;
    }

    public String getPushString(){
        return content+"||"+sender+"||"+time;
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

    @Override
    public void savaToBundle(Bundle bundle) {

    }
}
