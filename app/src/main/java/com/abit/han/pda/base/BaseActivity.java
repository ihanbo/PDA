package com.abit.han.pda.base;

import android.support.v7.app.AppCompatActivity;

import com.abit.han.pda.App;

/**
 * Created by ihanb on 2016/12/15.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(App.getAppEventBus().isRegistered(this)){
            App.getAppEventBus().unregister(this);
        }
    }
}
