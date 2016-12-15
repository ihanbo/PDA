package com.abit.han.pda;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ihanb on 2016/12/15.
 */

public class App extends Application {
    public static App mApp;
    private EventBus appEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        appEventBus = EventBus.builder().eventInheritance(false).build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (Throwable e) {
             e.printStackTrace();
        }
    }

    public static EventBus getAppEventBus(){
        return mApp.appEventBus;
    }
}
