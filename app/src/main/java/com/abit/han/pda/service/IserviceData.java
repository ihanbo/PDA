package com.abit.han.pda.service;

import android.os.Bundle;

/**
 * Created by Han on 2016/12/17.
 */

public interface IserviceData {
    int FLAG_START = 1;
    void savaToBundle(Bundle bundle);

    IserviceData DEFAULT = new IserviceData() {
        @Override
        public void savaToBundle(Bundle bundle) {
            bundle.putInt("flag",FLAG_START);
        }
    };
}
