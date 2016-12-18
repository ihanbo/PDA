package com.abit.han.pda.util;

import android.util.Log;

import com.abit.han.pda.BuildConfig;

/**
 * Log日志
 */

public class ll {
    public static void throwError(String msg){
        if(BuildConfig.DEBUG){
            throw new RuntimeException(msg);
        }else{
            Log.e("hh",msg);
        }
    }

    public static void logWarn(String msg){
        Log.w("hh",msg);
    }

    public static void i(String... s){
        int size = s==null? 0:s.length;
        if(size<1){
            return;
        }
        StringBuilder sb = new StringBuilder(s[0]+"<-->");
        for (int i = 1; i < size; i++) {
            sb.append(s[i]);
            sb.append("<-->");
        }
        Log.i("hh",sb.toString());
    }
}
