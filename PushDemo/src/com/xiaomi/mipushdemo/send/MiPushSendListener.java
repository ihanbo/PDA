package com.xiaomi.mipushdemo.send;

import android.util.Log;

import com.xiaomi.push.sdk.ErrorCode;

/**
 * 发推送回调
 */

public interface MiPushSendListener {
    void onSuccess(String messageId);

    void onFailed(ErrorCode errorCode,String reason,Exception e);


    MiPushSendListener DEFAULT = new MiPushSendListener() {
        @Override
        public void onSuccess(String messageId) {
            Log.i("mipush","send mi push succ,msgid: "+messageId);
        }
        @Override
        public void onFailed(ErrorCode errorCode, String reason, Exception e) {
            Log.i("mipush","send mi push failed,errorCode: "+errorCode.toString());
        }
    };
}
