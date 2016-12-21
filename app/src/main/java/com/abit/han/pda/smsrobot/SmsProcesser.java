package com.abit.han.pda.smsrobot;

import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.util.ll;
import com.xiaomi.mipushdemo.send.MiBroadCast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 短信处理中心
 */

public class SmsProcesser {

    /** 来信短信了*/
    public static void onGetNewMsg(NewSmsEvent event){
        MiBroadCast.getInstance().obtain()
                .setTitle("13511097504短信")
                .setContent(event.getPushString())
                .sendToTarget();
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
}
