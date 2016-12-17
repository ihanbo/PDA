package com.abit.han.pda.service;

import android.content.Intent;

import com.abit.han.pda.util.ll;

/**
 * Created by Han on 2016/12/17.
 */

public class ServiceDispatch {

    public static void dispatch(IserviceProxy service, Intent intent,int flags, int startId){
        ll.i("PDA收到服务");
    }
}
