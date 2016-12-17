package com.abit.han.pda.service;

import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.push.PushCenter;
import com.abit.han.pda.push.PushSendListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Han on 2016/12/17.
 */

public class FakeService {
    private static FakeService mInstance = new FakeService();

    public static void start(EventBus eventBus){
        if(!eventBus.isRegistered(mInstance)){
            eventBus.register(mInstance);
        }
    }

    public static void stop(EventBus eventBus){
        if(eventBus.isRegistered(mInstance)){
            eventBus.unregister(mInstance);
        }
    }

    @Subscribe
    public void onEvent(NewSmsEvent event){
        PushCenter.send(event, new PushSendListener() {
            @Override
            public void onFail(Throwable e) {

            }

            @Override
            public void onSuccess(int status, String result) {

            }
        });
    }

}
