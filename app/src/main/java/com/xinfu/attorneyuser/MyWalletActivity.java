package com.xinfu.attorneyuser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseHttpCompatActivity;
import com.xinfu.attorneyuser.bean.base.WXRechargeBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.BroadcastConstant;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.CallHttpUserBalanceUtil;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RechargeDataUtils;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowRecharge;
import com.xinfu.attorneyuser.utils.popupwindow.PopupwindowRechargeEdit;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeHandler;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeThread;
import com.xinfu.attorneyuser.wxapi.WXPayUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 * @Author 付庆明
 */

public class MyWalletActivity extends BaseHttpCompatActivity{

    public static final int RECHARGE_FOR_RESULT = 112;//其他页面充值成功后关闭此页面

    @BindView(R.id.tv_amount)
    TextView m_tvAmount;

    private int m_iAmount = 0;

    private MsgReceiver m_msgReceiver;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"我的钱包",true);
        registerReciever();
    }

    private OnTaskSuccessComplete onTaskSuccessComplete = new OnTaskSuccessComplete()
    {
        @Override
        public void onSuccess(Object obj)
        {
            m_iAmount = (int)obj;
            PopupWindowRecharge m_pwMenu = new PopupWindowRecharge(MyWalletActivity.this,onTaskSuccessCompleted);
            m_pwMenu.showAtLocation(m_tvAmount, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        }
    };

    private OnTaskSuccessComplete onTaskSuccessCompleted = new OnTaskSuccessComplete() {
        @Override
        public void onSuccess(Object obj) {
            if((int)obj == PopupWindowRecharge.RECHARGE_WX)
            {
                callHttpForRecharge(true);
            }
            else if((int)obj == PopupWindowRecharge.RECHARGE_ZFB)
            {
                callHttpForRecharge(false);
            }
        }
    };

    private OnTaskSuccessComplete onTaskSuccessRecharge = new OnTaskSuccessComplete() {
        @Override
        public void onSuccess(Object obj) {
            if((boolean)obj)
            {
                m_tvAmount.setText(String.valueOf(GlobalVariables.getWallect()));
                if(getIntent().getIntExtra("forResult",0) == RECHARGE_FOR_RESULT)
                {
                    finish();
                }
            }
        }
    };
    
    @OnClick({R.id.ll_recharge})
    public void onViewClick(View v)
    {
        switch (v.getId()) {
            case R.id.ll_recharge:
                PopupwindowRechargeEdit m_pwMenu = new PopupwindowRechargeEdit(MyWalletActivity.this,onTaskSuccessComplete);
                m_pwMenu.showAtLocation(m_tvAmount, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
        }
    }

    @Override
    protected void getData() {
        ApiStores.userBalance(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    executeOnLoadDataSuccess(true);
                    try {
                        JSONObject json = new JSONObject(Decrypt.getInstance().decrypt(response.getData()));
                        JSONObject jsonObject = new JSONObject(json.getString("common"));
                        m_tvAmount.setText(jsonObject.getString("balance"));
                        GlobalVariables.setUserWallect(jsonObject.getInt("balance"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    executeOnLoadDataSuccess(false);
                    FailureDataUtils.showServerReturnErrorMessageEx(MyWalletActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataSuccess(false);
                AlertUtils.MessageAlertShow(MyWalletActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }

    private void callHttpForRecharge(boolean isWX)
    {
        ApiStores.recharge(m_iAmount,isWX, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    String data = Decrypt.getInstance().decrypt(response.getData());
                    if(isWX)
                    {
                        WXRechargeBean beans = RechargeDataUtils.getWxRechargeData(data);

                        WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                        builder .setAppId(beans.getAppId())
                                .setPrepayId(beans.getPrepayId())
                                .setPackageValue(beans.getPackageName())
                                .setNonceStr(beans.getNonce_str())
                                .setTimeStamp(beans.getTimeStamp())
                                .setSign(beans.getSign())
                                .setPartnerId(beans.getPartnerid())
                                .build().toWXPayNotSign(MyWalletActivity.this);
                    }
                    else
                    {
                        RechargeHandler rechargeHandler = new RechargeHandler(MyWalletActivity.this, new OnTaskSuccessComplete() {
                            @Override
                            public void onSuccess(Object obj) {
                                CallHttpUserBalanceUtil.callHttpBalance(MyWalletActivity.this,waitDialog,onTaskSuccessRecharge);
                            }
                        });
                        RechargeThread rechargeThread = new RechargeThread(MyWalletActivity.this,rechargeHandler,RechargeDataUtils.getZHFRechargeData(data));
                        rechargeThread.start();
                    }
                }
                else
                {
                    FailureDataUtils.showServerReturnErrorMessageEx(MyWalletActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(MyWalletActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart()
            {
                waitDialog.show();
            }

            @Override
            public void OnRequestFinish()
            {
                waitDialog.dismiss();
            }

        });
    }

    private void registerReciever() {
        m_msgReceiver = new MsgReceiver();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(BroadcastConstant.BROADCAST_RECEIVE_RECHARGE_WX);
        registerReceiver(m_msgReceiver, filter1);
    }

    class MsgReceiver extends BroadcastReceiver
    {
        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public void onReceive(Context arg0, Intent intent)
        {
            int type = intent.getIntExtra("type",0);
            int errCode = intent.getIntExtra("errCode",0);
            if(type == ConstantsAPI.COMMAND_PAY_BY_WX)
            {
                if(errCode==0)
                {
                    CallHttpUserBalanceUtil.callHttpBalance(MyWalletActivity.this,waitDialog,onTaskSuccessRecharge);
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(m_msgReceiver);
    }


}
