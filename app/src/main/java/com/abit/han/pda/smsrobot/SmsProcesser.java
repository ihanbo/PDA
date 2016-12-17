package com.abit.han.pda.smsrobot;

import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.ll;
import com.abit.han.pda.push.PushCenter;
import com.abit.han.pda.push.PushSendListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Han on 2016/12/17.
 */

public class SmsProcesser {

    public static void onGetNewMsg(NewSmsEvent event){
        PushCenter.send(event, new SmsSendListener());
        event.save(new BmobSaveListener());
    }






    public static class BmobSaveListener extends SaveListener<String>{

        @Override
        public void done(String objectId, BmobException e) {
            if(e==null){
                ll.i("添加数据成功，返回objectId为："+objectId);
            }else{
                ll.i("创建数据失败：" + e.getMessage());
            }
        }
    }


    /** 推送回调 */
    public static class SmsSendListener implements PushSendListener{

        @Override
        public void onFail(Throwable e) {

        }

        @Override
        public void onSuccess(int status, String result) {

        }
    }


}
