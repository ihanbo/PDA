package com.abit.han.pda;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.abit.han.pda.bmob.BmobCenter;
import com.abit.han.pda.service.FakeService;
import com.facebook.stetho.Stetho;
import com.yiche.net.NetCenter;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.Bmob;

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
        //初始化EventBus
        appEventBus = EventBus.builder().eventInheritance(false).build();
        //初始化Bmob
        BmobCenter.init(this);
        //启动FakeService
        FakeService.start(appEventBus);
        //初始化Stetho
        initStetho();
        //初始化网络
        NetCenter.init(this);
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


    /** Chrome调试*/
    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

}
