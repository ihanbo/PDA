package com.abit.han.pda.event;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.abit.han.pda.service.IserviceData;

/**
 * 收到新的推送事件
 */

public class NewPushEvent implements Parcelable,IserviceData {

    public static final String KEY = NewPushEvent.class.getSimpleName();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeString(this.custom);
        dest.writeString(this.title);
        dest.writeString(this.text);
    }

    protected NewPushEvent(Parcel in) {
        this.msg = in.readString();
        this.custom = in.readString();
        this.title = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<NewPushEvent> CREATOR = new Parcelable.Creator<NewPushEvent>() {
        @Override
        public NewPushEvent createFromParcel(Parcel source) {
            return new NewPushEvent(source);
        }

        @Override
        public NewPushEvent[] newArray(int size) {
            return new NewPushEvent[size];
        }
    };


    @Override
    public void savaToBundle(Bundle bundle) {
        bundle.putString(FLAG,"");
    }
}
