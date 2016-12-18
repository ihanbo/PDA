package com.abit.han.pda.service;

import android.os.Bundle;

/**
 * BaseService数据用
 */

public class RealServiceData implements IserviceData{

    public String flag;

    public RealServiceData(String flag) {
        this.flag = flag;
    }

    @Override
    public void savaToBundle(Bundle bundle) {
        bundle.putString(FLAG,flag);
    }

}
