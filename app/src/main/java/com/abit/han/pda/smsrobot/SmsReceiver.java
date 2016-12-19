package com.abit.han.pda.smsrobot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.abit.han.pda.App;
import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.service.BaseService;
import com.abit.han.pda.util.ll;

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "接收短信执行了.....", Toast.LENGTH_LONG).show();
        ll.i("SMSReceiver, isOrderedBroadcast()=", isOrderedBroadcast() + "");
        ll.i("SmsReceiver onReceive...", "接收短信执行了......");
        String action = intent.getAction();
        if (SMS_RECEIVED_ACTION.equals(action) || SMS_DELIVER_ACTION.equals(action)) {
            Toast.makeText(context, "开始接收短信.....", Toast.LENGTH_LONG).show();
            ll.i("SmsReceiver onReceive...", "开始接收短信.....");

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null && pdus.length > 0) {
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        byte[] pdu = (byte[]) pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                    }
                    for (SmsMessage message : messages) {
                        String content = message.getMessageBody();// 得到短信内容
                        String sender = message.getOriginatingAddress();// 得到发信息的号码
                        Date date = new Date(message.getTimestampMillis());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                        String sendContent = format.format(date) + ":" + sender + "--" + content;
                        Log.e("SmsReceicer onReceive ", sendContent + " ");
                        BaseService.startService(new NewSmsEvent(content,sender, format.format(date)));
                    }
                }
            }
        }
    }
}