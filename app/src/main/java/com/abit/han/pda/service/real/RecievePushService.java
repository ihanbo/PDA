package com.abit.han.pda.service.real;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.abit.han.pda.R;
import com.abit.han.pda.event.NewPushEvent;
import com.abit.han.pda.service.IDOService;
import com.abit.han.pda.service.IserviceProxy;
import com.abit.han.pda.service.ServiceDispatch;
import com.abit.han.pda.util.ll;
import com.abit.han.pda.util.tt;

import java.util.Random;

/**
 * Created by Han on 2016/12/18.
 * 推送消息处理服务
 */

public class RecievePushService implements IDOService {


    @Override
    public String getFlag() {
        return ServiceDispatch.SERVICE_RECIEVE_PUSH;
    }

    @Override
    public void doService(Bundle bundle,IserviceProxy service) {
        NewPushEvent npe = bundle.getParcelable(NewPushEvent.KEY);
        if(npe==null){
            ll.throwError("NewPushEvent is null!!!");
        }
        ll.i(tt.getSimpleName(this),"展示通知");
        showNotification(npe,service);

    }

    private void showNotification( NewPushEvent msg,IserviceProxy service) {
        int id = new Random(System.nanoTime()).nextInt();
        NotificationManager manager = (NotificationManager) service.getServiceContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        Notification.Builder mBuilder = new Notification.Builder(service.getServiceContext());
        mBuilder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.title)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_pda)
                .setAutoCancel(true);
        Notification notification = mBuilder.getNotification();
//        PendingIntent clickPendingIntent = getClickPendingIntent(service.getServiceContext(), msg);
//        PendingIntent dismissPendingIntent = getDismissPendingIntent(service.getServiceContext(), msg);
//        notification.deleteIntent = dismissPendingIntent;
//        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

//    public PendingIntent getClickPendingIntent(Context context, NewPushEvent  msg) {
//        Intent clickIntent = new Intent();
//        clickIntent.setClass(context, NotificationBroadcast.class);
//        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
//                msg.getRaw().toString());
//        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION,
//                NotificationBroadcast.ACTION_CLICK);
//        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
//                (int) (System.currentTimeMillis()),
//                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        return clickPendingIntent;
//    }
//
//    public PendingIntent getDismissPendingIntent(Context context, NewPushEvent msg) {
//        Intent deleteIntent = new Intent();
//        deleteIntent.setClass(context, NotificationBroadcast.class);
//        deleteIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
//                msg.getRaw().toString());
//        deleteIntent.putExtra(
//                NotificationBroadcast.EXTRA_KEY_ACTION,
//                NotificationBroadcast.ACTION_DISMISS);
//        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context,
//                (int) (System.currentTimeMillis() + 1),
//                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        return deletePendingIntent;
//    }
}
