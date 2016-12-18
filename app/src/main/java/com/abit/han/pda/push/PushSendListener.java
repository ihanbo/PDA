package com.abit.han.pda.push;

/**
 * 推送监听
 */

public interface PushSendListener {
    void onFail(Throwable e);
    void onSuccess(int status, String result);
}
