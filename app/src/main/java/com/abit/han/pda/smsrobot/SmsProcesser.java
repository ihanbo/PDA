package com.abit.han.pda.smsrobot;

import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.sendpush.SendPushData;
import com.abit.han.pda.util.ll;
import com.abit.han.pda.sendpush.SendPushCenter;
import com.abit.han.pda.sendpush.SendPushSendListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 短信处理中心
 */

public class SmsProcesser {

    /** 来信短信了*/
    public static void onGetNewMsg(NewSmsEvent event){

        SendPushCenter.mInstance.sendPush(new SendPushData()
                .setTitle("13511097504短信")
                .setContent(event.getPushString()), new SmsSendListener());
        event.save(new BmobSaveListener());
    }


    public static class BmobSaveListener extends SaveListener<String>{

        @Override
        public void done(String objectId, BmobException e) {
            if(e==null){
                ll.i("添加数据成功，返回objectId为："+objectId);
            }else{
                ll.i("添加数据失败：" + e.getMessage());
            }
        }
    }


    /** 推送回调 */
    public static class SmsSendListener implements SendPushSendListener {

        @Override
        public void onFail(Throwable e) {
            ll.i(getClass().getSimpleName(),"推送失败-->"+e);
        }

        @Override
        public void onSuccess(int status, String result) {
            ll.i("推送成功-->"+result);
        }
    }


}
