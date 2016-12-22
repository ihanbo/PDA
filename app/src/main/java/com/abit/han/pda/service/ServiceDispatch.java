package com.abit.han.pda.service;

import android.content.Intent;
import android.os.Bundle;

import com.abit.han.pda.App;
import com.abit.han.pda.service.real.RecievePushService;
import com.abit.han.pda.util.ll;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务分发管理类
 */

public class ServiceDispatch {
    //服务名称
    public static final String SERVICE_START = "service_start";
    public static final String SERVICE_RECIEVE_PUSH = "push_recieve_service";
    public static final String SERVICE_RECIEVE_SMS = "service_recieve_sms";


    private static final ConcurrentHashMap<String,IDOService> services = new ConcurrentHashMap<>();


    public static final String KEY_SERVICE_NAME = "service_name";
    private final static String KEY_BUNDLE_DATA = "bundle_data_key";

    public static void dispatch(IserviceProxy service,Intent intent,int flags, int startId){
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE_DATA);
        String serviceName = bundle.getString(KEY_SERVICE_NAME);
        if(serviceName==null){
            ll.throwError("flag is null");
            return;
        }
        if(serviceName.equals(SERVICE_START)){
            return;
        }
        IDOService idoService = services.get(serviceName);
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



    public static void startService( IserviceData data){
        try {
            Intent i = new Intent(App.mApp,BaseService.class);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_SERVICE_NAME,data.getServiceName());
            data.savaToBundle(bundle);
            i.putExtra(KEY_BUNDLE_DATA,bundle);
            App.mApp.startService(i);
        } catch (Exception e) {
            e.printStackTrace();
            ll.throwError(e.getMessage());
        }
    }
}
