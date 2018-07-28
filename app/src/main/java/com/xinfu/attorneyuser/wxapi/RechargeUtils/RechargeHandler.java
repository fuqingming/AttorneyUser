package com.xinfu.attorneyuser.wxapi.RechargeUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;

import java.util.Map;

/**
 * Created by vip on 2018/5/23.
 */

public class RechargeHandler extends Handler
{
    private OnTaskSuccessComplete onTaskSuccessComplete;
    private Context mContext;

    public RechargeHandler(Context context, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        this.onTaskSuccessComplete = onTaskSuccessComplete;
        this.mContext = context;
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.what) {
            case RechargeThread.SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultStatus = payResult.getResultStatus();
                if (TextUtils.equals(resultStatus, "9000")) {
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    onTaskSuccessComplete.onSuccess(true);
                } else {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    onTaskSuccessComplete.onSuccess(false);
                }
                break;
            }
            case RechargeThread.SDK_AUTH_FLAG: {
                @SuppressWarnings("unchecked")
                AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                String resultStatus = authResult.getResultStatus();
                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    Toast.makeText(mContext, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                } else {
                    // 其他状态值则为授权失败
                    Toast.makeText(mContext, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                }
                break;
            }
            default:
                break;
        }
    }
}
