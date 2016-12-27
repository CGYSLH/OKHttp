package com.mi.okhttpuse;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

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
                        //联网失败的方法  运行在子线程
                        Log.i("tmd","联网失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //联网成功的方法  运行在子线程
                        Log.i("tmd",Thread.currentThread().getName());
                        Log.i("tmd",response.body().string());
//                        在解析操作完成后如果想要更新页面除了使用handler之外 也可以使用以下的方式
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {//运行在主线程
                                Log.i("tmd","当前的方法运行在"+Thread.currentThread().getName());
                            }
                        });
                    }
                });
//注意的是 enqueue是异步执行的方法 既是默认启动子线程联网的方法 因此主线程的方法要优先于onResponse的方法执行


                /**
                 * 通过Call对象执行网络请求的方式二：
                 * 通过execute方法
                 *  注意：此execute方法时同步执行的方法，即该方法中执行的连网操作默认是在主线程中执行，
                 *  因此如果需要通过此方法进行网络请求，那么需要先创建子线程，然后在子线程中调用此方法
                 */

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            //execute方法的返回值Response对象就是本次连网操作的请求结果
//                            Response response = getCall.execute();
//
//                            Log.i(TAG, "run: execute  get result :" + response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();

                break;
            case R.id.post://实现post请求
                RequestBody boby=new FormBody.Builder()
                        .add("platform","2")
                        .add("gifttype","1")
                        .add("compare","60841c5b7c69a1bbb3f06536ed685a48")
                        .build();

                Request post=new Request.Builder()
                        .post(boby)
                        .url("http://zhushou.72g.com/app/gift/gift_list/")
                        .build();
                Call postCall=client.newCall(post);
                postCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("tmd", "onResponse: post result 111 " + response.body().string());
                        //注意： 当通过response对象调用body方法后，再次获取数据时，是没有数据的
                        // 相当于第一次获取数据完成后，body方法找找那个就不再所有数据了
//                        Log.i("tmd", "onResponse: post result 222 " + response.body().string());//如果第二次获取的话为空
                    }
                });
            break;
            case R.id.upload_file:

                RequestBody fileBody = new MultipartBody.Builder()
                        .addFormDataPart(
                                //用于传递键值对条件，键值对中值的作用为：设置服务端接收此文件后，再服务端存储时存储的文件名称
                                "filename","aypost"+ System.currentTimeMillis()+".png",//服务器能够接受的数据的键
                                //封装要上传的File对象
                                //注意：此上传操作时将手机中的某文件上传到服务器中，因此注意File的路径不要有d盘等路径
                                RequestBody.create(MediaType.parse("application/octet-stream"),new File("/mnt/sdcard/ce/aydown.png"))//参数1固定的写法 参数2 为要上传的文件的手机的地址
                        )
                        .build();

                Request filePost = new Request.Builder()
                        .post(fileBody)
                        .url("http://192.168.9.168:8080/upload/UploadServlet")//服务器的地址
                        .build();

                /**
                 * 当在进行文件的上传或者下载的过程中
                 * enqueue方法的参数除了传递Callback类型之外
                 */
                client.newCall(filePost).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("tmd", "文件上传失败@@@！！！！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("tmd", "onResponse: 文件上传成功@@@！！！！");
                    }
                });
                break;
            case R.id.but_file_download:
                //文件下载的实现：
                //实现连网下载图片，并将图片存入sd卡中

                Request fileDown = new Request.Builder()
                        .url("http://i3.72g.com/upload/201506/201506041344181170.jpg")
                        .build();

                client.newCall(fileDown).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获取图片对应呢的流数据
                        InputStream is = response.body().byteStream();

                        //一般读取数据，一边将数据写入本地
                        FileOutputStream fos = new FileOutputStream("/mnt/sdcard/ce/aydown.png");
                        byte[] b = new byte[1024];
                        int num = -1;
                        while((num = is.read(b)) != -1) {
                            fos.write(b,0,num);
                            fos.flush();
                        }

                        fos.close();
                        is.close();
                        Log.i("tmd", "onResponse: 图片下载存储成功！！！！");
                    }
                });

                break;


        }
    }
}
