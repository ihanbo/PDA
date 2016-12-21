package com.abit.han.pda;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.abit.han.pda.bmob.BmobCenter;
import com.abit.han.pda.service.BaseService;
import com.abit.han.pda.service.FakeService;
import com.abit.han.pda.service.IserviceData;
import com.abit.han.pda.service.ServiceDispatch;
import com.facebook.stetho.Stetho;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.xiaomi.mipushdemo.MiPushRecieverCenter;
import com.xiaomi.mipushdemo.RecieveMiPushHelper;
import com.yiche.library.ylog.ErrorListener;
import com.yiche.library.ylog.Printer;
import com.yiche.library.ylog.YLog;
import com.yiche.net.NetCenter;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        //初始化log
        initYLog();
        //启动FakeService
        FakeService.start(appEventBus);
        //初始化Stetho
        initStetho();
        //初始化网络
        NetCenter.init(this);
        //启动服务
        BaseService.startService(IserviceData.START_SERVICE);
        //注册推送
        MiPushRecieverCenter.getInstance().register(this, miPushReciever);
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


    private void initYLog() {
        YLog.init("pda")
                .debug(BuildConfig.DEBUG)
                .showProcessName(this)
                .showThreadName(true)
                .showStackTrace(true)
                .showPriority(Printer.ERROR)
                .setErrorListener(new ErrorListener() {
                    @Override
                    public void onThrowable(int priority, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    private PushMessageReceiver miPushReciever = new PushMessageReceiver() {
        @Override
        public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
            super.onReceivePassThroughMessage(context, miPushMessage);
        }

        @Override
        public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
            super.onNotificationMessageClicked(context, miPushMessage);
        }

        @Override
        public void onNotificationMessageArrived(Context context, MiPushMessage message) {
            String log = context.getString(com.xiaomi.mipushdemo.R.string.arrive_notification_message, new Object[]{message.getContent()});
            RecieveMiPushHelper.logList.add(0, getSimpleDate() + " " + log);
            String type = "no type!";
            if(!TextUtils.isEmpty(message.getTopic())) {
                type = "topic:"+message.getTopic();
            } else if(!TextUtils.isEmpty(message.getAlias())) {
                type = "alias:"+message.getAlias();
            }
            YLog.i(type+"  "+log);
        }

        @Override
        public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
            super.onReceiveMessage(context, miPushMessage);
        }

        @Override
        public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
            String command = message.getCommand();
            List arguments = message.getCommandArguments();
            String cmdArg1 = arguments != null && arguments.size() > 0?(String)arguments.get(0):null;
            String log;
            if("register".equals(command)) {
                if(message.getResultCode() == 0L) {
                    String mRegId = cmdArg1;
                    log = "mRegId:"+mRegId+"  "+context.getString(com.xiaomi.mipushdemo.R.string.register_success);
                } else {
                    log = context.getString(com.xiaomi.mipushdemo.R.string.register_fail);
                }
            } else {
                log = message.getReason();
            }
            YLog.i(log);
        }

        @Override
        public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
            super.onCommandResult(context, miPushCommandMessage);
        }

        @SuppressLint({"SimpleDateFormat"})
        private  String getSimpleDate() {
            return (new SimpleDateFormat("MM-dd hh:mm:ss")).format(new Date());
        }
    };

}
