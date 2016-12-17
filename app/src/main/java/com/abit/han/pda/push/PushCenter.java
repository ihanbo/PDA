package com.abit.han.pda.push;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.util.ll;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;

/**
 * 推送中心
 */

public class PushCenter {

    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    /** 接收消息的Alias */
    public static final String MESSAGE_ALIAS = "message_need";
    /** 标记用户可以接受短信的tag */
    public static final String TAG_RECIEVE_SMS = "tag_recieve_sms";

    /** 发送推送 */
    public static void send(NewSmsEvent event,PushSendListener listener){
        Demo.sendAndroidCustomizedcast(MESSAGE_ALIAS, "13511097504新消息", event.toString(), listener);
    }

    /** 注册接受广播 */
    public static void registerRecievePush(final Application application) {
        PushAgent mPushAgent = PushAgent.getInstance(application);
        mPushAgent.setDebugMode(true);

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        //设置用户TAG
        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean b, com.umeng.common.inter.ITagManager.Result result) {
                if(b){
                    ll.i("友盟推送设置用户tag成功");
                }else{
                    ll.i("友盟推送设置用户tag失败");
                }
            }
        }, TAG_RECIEVE_SMS);

        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                ll.logWarn("device token: " + deviceToken);
                application.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                ll.logWarn("register failed: " + s + " " + s1);
                application.sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });

        /*// sdk关闭通知声音
		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);*/


       /* UmengMessageHandler messageHandler = new UmengMessageHandler() {
            *//**
         * 自定义消息的回调方法
         * *//*
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(application).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(application).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            *//**
         * 自定义通知栏样式的回调方法
         * *//*
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);*/


    }
}
