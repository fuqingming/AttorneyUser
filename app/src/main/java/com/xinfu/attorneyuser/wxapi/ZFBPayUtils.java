package com.xinfu.attorneyuser.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinfu.attorneyuser.backhandler.OnTaskComplete;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class ZFBPayUtils {

    private ZFBPayBuilder builder;

    private ZFBPayUtils(ZFBPayBuilder builder) {
        this.builder = builder;
    }

    /**
     * 调起微信支付的方法,不需要在客户端签名
     **/
    public void toWXPayNotSign(Context context) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static class ZFBPayBuilder {
        public String mOrderInfo;

        public ZFBPayUtils build() {
            return new ZFBPayUtils(this);
        }

        public ZFBPayBuilder setmOrderInfo(String mOrderInfo) {
            this.mOrderInfo = mOrderInfo;
            return this;
        }

        public String getmOrderInfo() {
            return mOrderInfo;
        }
    }
}
