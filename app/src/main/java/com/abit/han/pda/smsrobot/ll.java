package com.abit.han.pda.smsrobot;

import android.util.Log;

/**
 * Created by Han on 2016/10/15.
 */

public class ll {
    public static void e(String... s){
        int size = s==null? 0:s.length;
        if(size<1){
            return;
        }
        StringBuilder sb = new StringBuilder(s[0]);
        for (int i = 1; i < size; i++) {
            sb.append(s[i]);
        }
        Log.i("hh",sb.toString());
    }
}
