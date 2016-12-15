package com.abit.han.pda.push;

/**
 * Created by ihanb on 2016/12/15.
 */

public interface PushSendListener {
    void onFail(Throwable e);
    void onSuccess(int status, String result);
}
