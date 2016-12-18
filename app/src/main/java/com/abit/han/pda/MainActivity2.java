package com.abit.han.pda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    ListView ls;

    int[] lsxy = new int[2];
    EventBus bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = EventBus.getDefault();
        setContentView(R.layout.activity_main2);
        ls = (ListView) findViewById(R.id.list_msg);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ls.addHeaderView(ll);
        TextView tv = new TextView(this){
            @Subscribe
            public void onEvent(A a){
                append(a.s);
            }
        };
        bus.register(tv);
        tv.setText("我是head");
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
        ll.addView(tv);

        ls.setAdapter(adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new A(position+"||"));
            }
        });
    }

    public static final class A {
        public String s;

        public A(String s) {
            this.s = s;
        }
    }


    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(parent.getContext());
            tv.setText("我是item:"+position);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            return tv;
        }
    };


    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void l(String s){
        Log.i("hh",s);
    }

}
