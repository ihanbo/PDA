package com.abit.han.pda.service;

import android.content.Intent;
import android.os.Bundle;

import com.abit.han.pda.util.ll;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务分发管理类
 */

public class ServiceDispatch {
    private static final ConcurrentHashMap<String,IDOService> services = new ConcurrentHashMap<>();

    public static void dispatch(IserviceProxy service,Bundle bundle, Intent intent,int flags, int startId){
        String flag = bundle.getString(IserviceData.FLAG);
        if(flag==null){
            ll.throwError("flag is null");
            return;
        }
        IDOService idoService = services.get(flag);
        if(idoService==null){
            ll.throwError("idoService is null");
            return;
        }
        idoService.doService(bundle);
    }

    public static void registerServices(IDOService realService){
        if(services.containsKey(realService.getFlag())){
            throw new RuntimeException("cock!! has register!!!!!");
        }
        IDOService pri = services.put(realService.getFlag(),realService);
        if(pri!=null){
            throw new RuntimeException("cock!! has register!!!!!");
        }
    }
}
