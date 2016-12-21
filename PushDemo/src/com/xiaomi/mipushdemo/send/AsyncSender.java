package com.xiaomi.mipushdemo.send;

import com.xiaomi.mipushdemo.MiPushKEY;
import com.xiaomi.xmpush.server.Sender;

/**
 * Created by ihanb on 2016/12/21.
 */

public class AsyncSender {
    Sender mSender = new Sender(MiPushKEY.APP_SECRET_KEY);
    private static AsyncSender mAsyncSender = new AsyncSender();
    public static AsyncSender getInstance(){
        return mAsyncSender;
    }

    public void sendAsync(MiPushData data, MiPushSendListener listener, MiPushSendMethod miPushMethod) {
        new Thread(new SendAsync(data,listener,miPushMethod,mSender)).start();
    }

    private static class SendAsync implements Runnable{
        MiPushData data;
        MiPushSendListener listener = MiPushSendListener.DEFAULT;
        MiPushSendMethod miPushMethod;
        Sender mSender;

        public SendAsync(MiPushData data, MiPushSendListener listener, MiPushSendMethod miPushMethod, Sender sender) {
            this.data = data;
            if(listener!=null){
                this.listener = listener;
            }
            this.miPushMethod = miPushMethod;
            mSender = sender;
        }

        @Override
        public void run() {
            miPushMethod.sendMethod(mSender,data,listener);
        }
    }
}
