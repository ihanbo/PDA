package com.abit.han.pda.service;

import android.content.Intent;
import android.os.Bundle;

import com.abit.han.pda.service.real.RecievePushService;
import com.abit.han.pda.util.ll;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务分发管理类
 */

public class ServiceDispatch {
    //服务名称
    public static final String PUSH_RECIEVE_SERVICE = "push_recieve_service";

    private static final ConcurrentHashMap<String,IDOService> services = new ConcurrentHashMap<>();

    public static void dispatch(IserviceProxy service,Bundle bundle, Intent intent,int flags, int startId){
        String flag = bundle.getString(IserviceData.FLAG);
        if(flag==null){
            ll.throwError("flag is null");
            return;
        }
        if(flag.equals("start service")){
            return;
        }
        IDOService idoService = services.get(flag);
        if(idoService==null){
            ll.throwError("idoService is null");
            return;
        }
        idoService.doService(bundle,service);
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

    /**
     * 注册所有服务
     */
    public static void registeAllServices() {
        registerServices(new RecievePushService());
    }
}
