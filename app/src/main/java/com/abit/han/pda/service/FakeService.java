package com.abit.han.pda.service;

import com.abit.han.pda.App;
import com.abit.han.pda.event.NewPushEvent;
import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.smsrobot.SmsProcesser;
import com.abit.han.pda.util.ll;
import com.abit.han.pda.util.tt;

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
    //新消息
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventNewSMS(NewSmsEvent event){
        SmsProcesser.onGetNewMsg(event);
    }

    //新消息
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventNewPushMsg(NewPushEvent event){
        ll.i(tt.getSimpleName(this),tt.getCurProcessName(App.mApp),event.title);
    }

}
