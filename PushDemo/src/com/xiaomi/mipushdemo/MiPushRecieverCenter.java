package com.xiaomi.mipushdemo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 1、为了打开客户端的日志，便于在开发过程中调试，需要自定义一个 Application。
 * 并将自定义的 application 注册在 AndroidManifest.xml 文件中。<br/>
 * 2、为了提高 push 的注册率，您可以在 Application 的 onCreate 中初始化 push。你也可以根据需要，在其他地方初始化 push。
 *
 * @author wangkuiwei
 */
public class MiPushRecieverCenter {


    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    // com.xiaomi.mipushdemo
    public static final String TAG = "com.xiaomi.mipushdemo";


    private PushMessageReceiver msgReciever ;


    public void register(Application application,PushMessageReceiver msgReciever) {

        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit(application)) {
            MiPushClient.registerPush(application, MiPushKEY.APP_ID, MiPushKEY.APP_KEY);
        }
        //设置log
//        showDebugLog(application);
        this.msgReciever = msgReciever;
    }

    private void showDebugLog(Application application) {
        //调试日志
        //默认放在默认情况下，我们会将日志内容写入SDCard/Android/data/apppkgname/files/MiPushLog目录下的文件。
        // 如果app需要关闭写日志文件功能（不建议关闭），只需要调用Logger.disablePushFileLog(context)即可。
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(application, newLogger);
    }

    /**　主进程才初始化　*/
    private boolean shouldInit(Application application) {
        ActivityManager am = ((ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = application.getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static MiPushRecieverCenter getInstance() {
        return mInstance;
    }
    private static MiPushRecieverCenter mInstance = new MiPushRecieverCenter();

    private MiPushRecieverCenter() {
    }

     PushMessageReceiver getMsgReciever(){
        return msgReciever==null? DEFAULT:msgReciever;
    }



    private PushMessageReceiver DEFAULT = new PushMessageReceiver() {
        @Override
        public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
            super.onReceivePassThroughMessage(context, miPushMessage);
        }

        @Override
        public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
            super.onNotificationMessageClicked(context, miPushMessage);
        }

        @Override
        public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
            super.onNotificationMessageArrived(context, miPushMessage);
        }

        @Override
        public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
            super.onReceiveMessage(context, miPushMessage);
        }

        @Override
        public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
            super.onReceiveRegisterResult(context, miPushCommandMessage);
        }

        @Override
        public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
            super.onCommandResult(context, miPushCommandMessage);
        }
    };

    /** 收到新消息*/
    public void onLoged( String logDesc) {
            Log.i("mipush",logDesc);
    }
}