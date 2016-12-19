package com.abit.han.pda.sendpush.umeng;

import android.app.Application;
import android.content.Intent;

import com.abit.han.pda.recievepush.IRecievePush;
import com.abit.han.pda.sendpush.ISendPush;
import com.abit.han.pda.sendpush.SendPushData;
import com.abit.han.pda.sendpush.SendPushSendListener;
import com.abit.han.pda.util.ll;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

/**
 * 推送中心
 */

public class UMPushCenter implements ISendPush,IRecievePush {

    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    /** 标记用户可以接受短信的tag */
    public static final String TAG_RECIEVE_SMS = "tagrecievesms";
    /** 接收消息的Alias */
    public static final String MESSAGE_ALIAS = "message_need";


    private static final String MI5_DEVICE_TOKEN = "Au_Vge514bCYJhoLGW6GjitXOL58hx9oH6wGDKRRX2hj";
    private static final String MIMAX_DEVICE_TOKEN = "Ai1obrPs_MgiPFMhS3_YV37YaC-y-aIc14MPHXPrGMTd";


    private static  UMPushCenter instance;

    public static UMPushCenter getInstance(){
        if(instance==null){
            instance = new UMPushCenter();
        }
        return instance;
    }

    private UMPushCenter() {

    }

    @Override
    public void initRecievePush(final  Application application) {
        PushAgent mPushAgent = PushAgent.getInstance(application);
        mPushAgent.setDebugMode(true);

        mPushAgent.setMessageChannel("general");

        //sdk开启通知声音 声光电
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        //设置消息处理类
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        //设置用户TAG
        mPushAgent.getTagManager().update(new TagCallBack(),TAG_RECIEVE_SMS);

        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                ll.i("注册成功，deviceToken: " + deviceToken);
                application.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                ll.i("注册失败: " + s + " " + s1);
                application.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });
    }

    @Override
    public void initSendPush(Application application) {

    }

    @Override
    public void sendPush(SendPushData data, SendPushSendListener listener) {
        Demo.sendAndroidUnicast(data.title, data.title, data.content, listener,MI5_DEVICE_TOKEN);
    }


    /** 设置tag回调 */
    public static final class TagCallBack implements TagManager.TCallBack{
        @Override
        public void onMessage(boolean b, ITagManager.Result result) {
            if(b){
                ll.i("友盟推送设置用户tag成功");
            }else{
                ll.i("友盟推送设置用户tag失败:  "+(result==null? "no result!":result.toString()));
            }
        }
    }
}