package com.xinfu.attorneyuser.https;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.download.DownLoadCallBack;
import com.xinfu.attorneyuser.base.MyApplication;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by HH
 * Date: 2017/11/22
 */

public class HttpClient
{

    @SuppressLint("StaticFieldLeak")
    private static Novate mNovate = null;

    public static void init(Context context, boolean reInit)
    {
        if (reInit)
        {
            mNovate = null;
        }
        if (mNovate == null)
        {
            Context appliactionContext;
            try {
                Activity activity = (Activity) context;
                appliactionContext = activity.getApplicationContext();
            } catch (Exception e) {
                appliactionContext = context;
            }

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(true);

            OkHttpClient okHttpClient = builder.build();

            MyApplication myApp = MyApplication.getInstance();

            String baseUrl = HttpSetUrl.getAppUrl();
            Map<String, String> headers = new HashMap<>();
//            headers.put("X-Requested-With", "XMLHttpRequest");
            headers.put("Content-Type", "application/json");
//            headers.put("X-Auth-City", HttpSetUrl.getHeaderAuthCity());
//            headers.put("X-Auth-App", "5006");
////            headers.put("X-Auth-uuid",  HttpSetUrl.getHeaderAuthUuid());
////            headers.put("X-Auth-Token", HttpSetUrl.getHeaderAuthToken());
//            headers.put("X-Auth-uuid",  myApp.m_strUuid);
//            headers.put("X-Auth-Token", myApp.m_strUserToken);

            mNovate = new Novate.Builder(appliactionContext)
                    .addHeader(headers)
                    .addCache(false)
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
//                    .addCookie(true)
                    .build();
        }
    }

    public static Novate novateClient()
    {
        if (mNovate == null)
        {
            Log.e("Novate", "Novate is null!");
        }
        return mNovate;
    }

    public static <T> void  get(String url, HttpCallback<T> httpCallback)
    {
        ApiService apiService = mNovate.create(ApiService.class);
        mNovate.call(apiService.executeGet(url),httpCallback);
    }

    public static <T> void get(String url, Map<String,Object> params, HttpCallback<T> httpCallback)
    {
        mNovate.rxGet(url,params,httpCallback);
    }

    public static <T> void post(String url, Map<String,Object> params, HttpCallback<T> httpCallback)
    {
        mNovate.rxBody(url,params,httpCallback);
    }

    public static <T> void rxPost(String url, Map<String,Object> params, HttpCallback<T> httpCallback)
    {
        mNovate.rxPost(url,params,httpCallback);
    }

    public static <T> void post(String url, String jsonString, HttpCallback<T> httpCallback)
    {
        mNovate.rxJson(url,jsonString,httpCallback);
    }

    public static <T> void uploadFile(String url, File file, HttpCallback<T> httpCallback)
    {
        mNovate.rxUploadWithPart(url,file,httpCallback);
    }

    public static <T> void uploadFile(String url, Map<String, File> maps, HttpCallback<T> httpCallback)
    {
        mNovate.rxUploadWithPartMapByFile(url,maps,httpCallback);
    }

    public static <T> void uploadFile(String url, List<File> list, HttpCallback<T> httpCallback)
    {
        mNovate.rxUploadWithPartListByFile(url,list,httpCallback);
    }

    public static <T> void uploadFile(String url, RequestBody requestBody , Subscriber<ResponseBody> subscriber)
    {
        mNovate.upload(url,requestBody,subscriber);
    }

    public static <T> void delete(String url, Map<String,Object> params, BaseSubscriber<ResponseBody> subscriber)
    {
        mNovate.delete(url, params,subscriber);
    }

    public static <T> void download(String url, DownLoadCallBack downLoadCallBack)
    {
        mNovate.download(url, downLoadCallBack);
    }
}
