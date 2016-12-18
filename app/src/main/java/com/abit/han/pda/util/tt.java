package com.abit.han.pda.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Han on 2016/12/18.
 */

public class tt {


    public static String getSimpleName(Object object){
        return object.getClass().getSimpleName();
    }

    public static String getFullName(Object object){
        return object.getClass().getName();
    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return "not found!";
    }


    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if(serviceList == null || serviceList.isEmpty())
            return false;
        for(int i = 0; i < serviceList.size(); i++) {
            if(serviceList.get(i).service.getClassName().equals(className) && TextUtils.equals(
                    serviceList.get(i).service.getPackageName(), context.getPackageName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
