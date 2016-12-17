package com.abit.han.pda.bmob;

import android.app.Application;

import com.abit.han.pda.util.Costant;

import cn.bmob.v3.Bmob;

/**
 * Bmob配置
 */

public class BmobCenter {
    public static final String BMOB_APP_KEY = Costant.Bmob.BMOB_APP_KEY;
    public static final String BMOB_API_KEY = Costant.Bmob.BMOB_API_KEY;


    public static void init(Application app) {
        Bmob.initialize(app, BmobCenter.BMOB_APP_KEY);
    }
}
