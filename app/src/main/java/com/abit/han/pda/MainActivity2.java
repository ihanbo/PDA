package com.abit.han.pda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.abit.han.pda.push.Demo;
import com.facebook.stetho.Stetho;
import com.yiche.net.NetCenter;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ls = (ListView) findViewById(R.id.list_msg);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ls.addHeaderView(ll);
        TextView tv = new TextView(this);
        tv.setText("我是head");
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
        ll.addView(tv);

        ls.setAdapter(adapter);
        ls.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    l("first:"+ls.getFirstVisiblePosition()+"   LAST:"+ls.getLastVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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


    void l(String s){
        Log.i("hh",s);
    }

}
