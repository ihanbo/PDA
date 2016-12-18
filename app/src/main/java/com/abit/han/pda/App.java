package com.abit.han.pda;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.abit.han.pda.bmob.BmobCenter;
import com.abit.han.pda.push.PushCenter;
import com.abit.han.pda.service.BaseService;
import com.abit.han.pda.service.FakeService;
import com.abit.han.pda.service.IserviceData;
import com.abit.han.pda.service.ServiceDispatch;
import com.facebook.stetho.Stetho;
import com.umeng.message.PushAgent;
import com.yiche.net.NetCenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ihanb on 2016/12/15.
 */

public class App extends Application {
    public static App mApp;
    private EventBus appEventBus;
    private PushAgent pushAgent;

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
        //启动服务
        BaseService.startService(IserviceData.START_SERVICE);
        //注册推送
        PushCenter.instance.registerToRecievePush(this);
        //ServiceDispatch注册服务
        ServiceDispatch.registeAllServices();
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
