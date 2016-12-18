package com.abit.han.pda.service;

import android.os.Bundle;

/**
 * 注册服务
 */

public interface IDOService {
   String getFlag();
    void doService(Bundle bundle,IserviceProxy service);
}
