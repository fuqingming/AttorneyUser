package com.xinfu.attorneyuser.wxapi.RechargeUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * @author 付庆明
 * */  
public class RechargeThread extends Thread
{
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;

    private Handler mHandler;
    private Context mContext;
    private String mOrderInfo;

    public RechargeThread(Context context , Handler mHandler, String orderInfo)
    {
        this.mHandler = mHandler;
        this.mContext = context;
        this.mOrderInfo = orderInfo;
    }  
  
    @Override
    public void run()
    {
        PayTask alipay = new PayTask((Activity) mContext);
        Map<String, String> result = alipay.payV2(mOrderInfo, true);

        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }
}