package com.abit.han.pda.service;

import android.os.Bundle;

/**
 * 服务数据
 */

public interface IserviceData {
    String FLAG = "flag";
    void savaToBundle(Bundle bundle);

    IserviceData DEFAULT = new IserviceData() {
        @Override
        public void savaToBundle(Bundle bundle) {
            bundle.putString(FLAG,"start service");
        }
    };
}
