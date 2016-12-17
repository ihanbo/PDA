package com.abit.han.pda.bmob;

import android.app.Application;
import cn.bmob.v3.Bmob;

/**
 * Bmob配置
 */

public class BmobCenter {
    public static final String BMOB_APP_KEY = "5767c5911dd06e44a0e7b2ed1fd334c7";
    public static final String BMOB_API_KEY = "1db1ca1c134259300170f3b76e7e89fa";


    public static void init(Application app) {
        Bmob.initialize(app, BmobCenter.BMOB_APP_KEY);
    }
}
