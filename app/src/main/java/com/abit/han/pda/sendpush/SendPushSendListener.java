package com.abit.han.pda.sendpush;

/**
 * 推送监听
 */

public interface SendPushSendListener {
    void onFail(Throwable e);
    void onSuccess(int status, String result);
}
