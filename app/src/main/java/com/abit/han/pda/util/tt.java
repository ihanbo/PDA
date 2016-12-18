package com.abit.han.pda.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Han on 2016/12/18.
 */

public class tt {
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
}
