package com.mi.okhttp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mi.okhttp.bean.ZGCBean;
import com.mi.okhttp.utils.OKHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.get_execute)
    Button getExecute;
    @BindView(R.id.lv)
    ListView lv;
    private WebView webview;
    private List<ZGCBean.ListBean> list=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                adapter.notifyDataSetChanged();
                Log.i("tmd","走了");
            }
        }
    };
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                下面的代码应该在第二个Activity中书写
//                webview.loadUrl(list.get(position).getUrl());
//
//                webview.setWebViewClient(new WebViewClient(){
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        view.loadUrl(url);
//                        return true;
//                    }
//                });
//                setContentView(webview);
                Intent intent =new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra("url",list.get(position).getUrl());
                startActivity(intent);
            }

        });
    }

    private void initAdapter() {
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }



    public void click(View view) {
        switch (view.getId()) {
            case R.id.get_execute:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ZGCBean zgcBean = OKHttpUtils.getInstance().get("http://lib.wap.zol.com.cn/ipj/docList/?v=6.0&class_id=0&page=4&vs=and412&retina=1");
                        Log.i("tmd", zgcBean.getList().toString());
                        list.addAll(zgcBean.getList());
                        handler.sendEmptyMessage(0);
                    }
                }).start();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.i("tmd",""+list.size());
            return list.size();
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
            MyViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_lv, parent, false);
                holder = new MyViewHolder();
                holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
                holder.iv = (ImageView) convertView.findViewById(R.id.item_iv);
                convertView.setTag(holder);
            } else {
                holder= (MyViewHolder) convertView.getTag();
            }
            holder.tv.setText(list.get(position).getStitle());
            Picasso.with(MainActivity.this).load(list.get(position).getImgsrc2()).into(holder.iv
            );
            return convertView;
        }
        class MyViewHolder
        {
            TextView tv;
            ImageView iv;
        }
    }
}
