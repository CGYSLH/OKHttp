package com.mi.okhttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 暗示语 on 2016/12/28.
 */

public class WebActivity extends AppCompatActivity {
    private WebView webview;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        webview=new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);

        url = getIntent().getStringExtra("url");
                        webview.loadUrl(url);

                webview.setWebViewClient(new WebViewClient(){

                    public boolean shouldOverrideUrlLoading(WebView view, String url) {//这个方法是保证webview的打开是在本程序的上面打开而不是利用
//                        外部的浏览器进行打开 这样的更利于我们进行操作 因为大部分的程序的操作也是这样的套路
                        view.loadUrl(url);
                        return true;
                    }
                });
                setContentView(webview);//设置当前的webview为当前的页面显示 因为我们没有利用xml文件进行webview的显示 所以这里我们采用的是直接new对象的方式

    }
//    有问题要点击两次才可以返回
//    //设置回退
//    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
//            webview.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return super.onKeyDown(keyCode,event);
//    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            Log.i("tmd","oo");
            Log.i("tmd",webview.getUrl());
            Log.i("tmd",url);
            /**
             * String[] urls=url.split("/");

             String lasturl=urls[urls.length-1];

             String[] lasturls=lasturl.split(".h");

             System.out.println(lasturls[0]);
             * */
        /*
        * 下面的代码是在手机点击返回键的时候我们如何保证他不会直接的在我们浏览webview的时候
        * 如果我们有点击了新的webview的时候不会因为点击返回键的原因就会退出程序
        * 这里我们进行网址的判断当我们所在的网页还可以继续返回的时候我们可以判读这个网址是不是我们第一次传入进来的网址
        * 如果是的话我们就执行系统的onbackpressed
        * 如果不是的话我们就执行webview的goback的方法
        * **/
//            if(webview.getUrl().equals(url)){//正常的代码应该是这样写但是现在的问题是这样写的话得到的网址的数据和我传进来的初始的网址有出入这个时候只能重新进行编写找到共同点
//                Log.i("tmd",url);
//                super.onBackPressed();
//            }else{
//                webview.goBack();
//                Log.i("tmd","goBack");
//            }
            String url_t=webview.getUrl();
            String[] urls=url_t.split("/");
            String lasturl=urls[urls.length-1];

            String[] lasturls=lasturl.split(".h");

            Log.i("tmd",lasturls[0]);
            if(url.contains(lasturls[0])){
                Log.i("tmd",url);
                super.onBackPressed();
            }else{
                webview.goBack();
                Log.i("tmd","goBack");
            }
        }else{
            super.onBackPressed();
        }
    }
}
