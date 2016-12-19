package com.abit.han.pda.recievepush;

import android.app.Application;

/**
 *  接受推送的超类
 */

public interface IRecievePush {
    /** 注册接受推送 */
    void initRecievePush(Application application);
}
