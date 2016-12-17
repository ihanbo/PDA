package com.abit.han.pda.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.abit.han.pda.App;
import com.abit.han.pda.util.ll;

/**
 * 基础服务
 */

public class BaseService extends Service implements IserviceProxy{

    private final static int GRAY_SERVICE_ID = 1109;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ll.i("PDA基础服务已启动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ll.i("PDA基础服务已关闭");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setGrayForeground();
        ServiceDispatch.dispatch(this,intent,flags,startId);
        return START_STICKY;
    }



    /** 设置灰色前台 保活*/
    private void setGrayForeground() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
    }


    /** 给 API >= 18 的平台上用的灰色保活手段 */
    public static class GrayInnerService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }



    public static void startService( IserviceData data){
        try {
            Intent i = new Intent(App.mApp,BaseService.class);
            Bundle bundle = new Bundle();
            data.savaToBundle(bundle);
            i.putExtra("data",bundle);
            App.mApp.startService(i);
        } catch (Exception e) {
            e.printStackTrace();
            ll.throwError(e.getMessage());
        }
    }

}
