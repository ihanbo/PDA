package com.abit.han.pda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.abit.han.pda.base.BaseActivity;
import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.push.Demo;
import com.facebook.stetho.Stetho;
import com.yiche.net.NetCenter;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getAppEventBus().register(this);
        NetCenter.init(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        findViews();
    }

    private Button getMsg;
    private TextView textView;
    private ListView listMsg;

    private void findViews() {
        getMsg = (Button)findViewById( R.id.get_msg );
        textView = (TextView)findViewById( R.id.textView );
        listMsg = (ListView)findViewById( R.id.list_msg );

        getMsg.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if ( v == getMsg ) {
            try {
                Demo.sendAndroidBroadcast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Subscribe
    public void onEvent(NewSmsEvent event){
        textView.setText(event.toString());
    }

}
