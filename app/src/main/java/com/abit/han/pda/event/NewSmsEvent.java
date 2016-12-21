package com.abit.han.pda.event;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.abit.han.pda.service.IserviceData;
import com.abit.han.pda.service.ServiceDispatch;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.helper.GsonUtil;

/**
 * Created by ihanb on 2016/12/15.
 * 新消息事件
 */

public class NewSmsEvent extends BmobObject implements IserviceData, Parcelable {
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

    @Override
    public void savaToBundle(Bundle bundle) {
        bundle.putParcelable("NewSmsEvent",this);
    }

    @Override
    public String getServiceName() {
        return ServiceDispatch.SERVICE_RECIEVE_SMS;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.sender);
        dest.writeString(this.time);
    }

    protected NewSmsEvent(Parcel in) {
        this.content = in.readString();
        this.sender = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<NewSmsEvent> CREATOR = new Parcelable.Creator<NewSmsEvent>() {
        @Override
        public NewSmsEvent createFromParcel(Parcel source) {
            return new NewSmsEvent(source);
        }

        @Override
        public NewSmsEvent[] newArray(int size) {
            return new NewSmsEvent[size];
        }
    };

    public String getPushString() {
        return content+"||"+sender+"||"+time;
    }
}
