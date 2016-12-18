package com.abit.han.pda.service;

import android.os.Bundle;

/**
 * Baseservice服务需要的数据
 */

public interface IserviceData {
    String FLAG = "flag";
    void savaToBundle(Bundle bundle);

    IserviceData START_SERVICE = new IserviceData() {

        @Override
        public void savaToBundle(Bundle bundle) {
            bundle.putString(FLAG,"start service");
        }
    };
}
