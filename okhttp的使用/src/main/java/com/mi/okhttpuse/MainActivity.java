package com.mi.okhttpuse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp的使用
 * 作用 用于实现网络数据的加载的框架 同时通过此框架可以实现文件的下载和上传
 * <p>
 * 导包的话 除了要导入okhttp包还要导入okio的包
 * 为什么要导入okio的包因为okhttp中使用了okio的方法
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.get)
    Button get;
    @BindView(R.id.post)
    Button post;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //创建okhttpclient的对象
        client = new OkHttpClient();

    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.get://实现get请求

                //封装request对象
                Request get=new Request.Builder()
                        .url("http://lib.wap.zol.com.cn/ipj/docList/?v=6.0&class_id=0&page=4&vs=and412&retina=1")//获取网址的对象
                        .get()//用于设置当前的请求的方式为get的请求
                        .build();
                //获取call对象
                Call getCall=client.newCall(get);
                getCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //联网失败的方法
                        Log.i("tmd","联网失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //联网成功的方法
                        Log.i("tmd",response.body().string());
                    }
                });

            break;
            case R.id.post://实现post请求
            break;
        }
    }
}
