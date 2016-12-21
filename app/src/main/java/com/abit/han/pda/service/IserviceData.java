package com.abit.han.pda.service;

import android.os.Bundle;

/**
 * Baseservice服务需要的数据
 */

public interface IserviceData {
    void savaToBundle(Bundle bundle);
    String getServiceName();

    IserviceData START_SERVICE = new IserviceData() {

        @Override
        public void savaToBundle(Bundle bundle) {
        }

        @Override
        public String getServiceName() {
            return ServiceDispatch.SERVICE_START;
        }
    };
}
