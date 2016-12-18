package com.abit.han.pda.service.real;

import android.os.Bundle;

import com.abit.han.pda.service.IDOService;

/**
 * Created by Han on 2016/12/18.
 */

public class RecievePushService implements IDOService {
    @Override
    public String getFlag() {
        return "recievepushservice";
    }

    @Override
    public void doService(Bundle bundle) {

    }
}
