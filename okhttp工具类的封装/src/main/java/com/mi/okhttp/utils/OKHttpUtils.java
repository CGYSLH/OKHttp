package com.mi.okhttp.utils;

import com.google.gson.Gson;
import com.mi.okhttp.bean.ZGCBean;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 暗示语 on 2016/12/28.
 */

public class OKHttpUtils {
   private OkHttpClient client;

    private OKHttpUtils() {
        client=new OkHttpClient();
    }
    private static OKHttpUtils utils;
    public static OKHttpUtils getInstance()//单列防止创建多个对象
    {
        if (utils==null)
        {
            synchronized (OKHttpUtils.class) {
                if (utils == null) {
                    utils=new OKHttpUtils();
                }
            }
        }
        return utils;
    }
    //    用于实现任意网址对应的get请求
    public ZGCBean get(String url){
        Request request=new Request.Builder().url(url).build();
        try {
            Response response= client.newCall(request).execute();
            ZGCBean zb=new Gson().fromJson(response.body().string(),ZGCBean.class);

            return zb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
