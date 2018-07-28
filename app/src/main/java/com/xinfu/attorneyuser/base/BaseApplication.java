package com.xinfu.attorneyuser.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xinfu.attorneyuser.https.HttpSetUrl;
import com.xinfu.attorneyuser.huanxin.DemoHelper;
import com.xinfu.attorneyuser.huanxin.HMSPushHelper;

import cn.finalteam.rxgalleryfinal.utils.ModelUtils;

public class BaseApplication extends MyApplication
{

    public static Context applicationContext;//华为 HMS 推送服务
    private static BaseApplication instance;//华为 HMS 推送服务
    public static String currentUserNick = "";//华为 HMS 推送服务

    @Override
    public void onCreate()
    {
        super.onCreate();
        Utils.init(this);//shardPrefrences
        HttpSetUrl.setAppUrl("http://p1.51zhenpin.cn");
        //图片选择
        ModelUtils.setDebugModel(true);
        Fresco.initialize(this);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config.build());

        MobSDK.init(this);//shareSDK

        //华为 HMS 推送服务
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;
        //init demo helper
        DemoHelper.getInstance().init(applicationContext);
        // 初始化华为 HMS 推送服务
        HMSPushHelper.getInstance().initHMSAgent(instance);
        //华为 HMS 推送服务 end
    }

    //华为 HMS 推送服务
    public static BaseApplication getInstance()
    {
        return instance;
    }

    //华为 HMS 推送服务
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}