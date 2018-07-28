package com.xinfu.attorneyuser;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.VipAdapter;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseListActivity;
import com.xinfu.attorneyuser.bean.base.VipCardActivationBean;
import com.xinfu.attorneyuser.bean.base.WXRechargeBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseVipBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RechargeDataUtils;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowRecharge;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowVipCardActivation;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowVipDetails;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeHandler;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeThread;
import com.xinfu.attorneyuser.wxapi.WXPayUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author 付庆明
 */

public class VipActivity extends BaseListActivity{

    private VipAdapter m_vipAdapter = new VipAdapter();

    private PopupWindowVipDetails m_pwMenu;

    private String m_strCardId;

    @Override
    protected int setLayoutResourceId()
     {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"VIP会员卡",true);
    }

    @Override
    protected void initLayoutManager() {
        View header = LayoutInflater.from(this).inflate(R.layout.common_head_vip,mRecyclerView, false);
        mRecyclerViewAdapter.addHeaderView(header);
        mRecyclerView.setLoadMoreEnabled(false);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                m_strCardId = m_vipAdapter.getListData().get(position).getId();
                m_pwMenu = new PopupWindowVipDetails(VipActivity.this, m_vipAdapter.getListData().get(position), m_onTaskSuccessDetails);
                m_pwMenu.showAtLocation(findViewById(R.id.recycler_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private OnTaskSuccessComplete m_onTaskSuccessDetails = new OnTaskSuccessComplete(){
        @Override
        public void onSuccess(Object obj) {
            int iResult = (int)obj;
            switch (iResult)
            {
                case PopupWindowVipDetails.ONLINE_PURCHASE:
                    PopupWindowRecharge m_pwMenuRecharge = new PopupWindowRecharge(VipActivity.this,onTaskSuccessCompleted);
                    m_pwMenuRecharge.showAtLocation(findViewById(R.id.recycler_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                case PopupWindowVipDetails.CARD_ACTIVATION:
                    PopupWindowVipCardActivation m_pwMenuActivation = new PopupWindowVipCardActivation(VipActivity.this,onTaskSuccessCompletedActivation);
                    m_pwMenuActivation.showAtLocation(findViewById(R.id.recycler_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
            }
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

    private OnTaskSuccessComplete onTaskSuccessCompletedActivation = new OnTaskSuccessComplete()
    {
        @Override
        public void onSuccess(Object obj)
        {
            VipCardActivationBean data = (VipCardActivationBean)obj;
            callHttpCompletedActivation(data.getCardNum(),data.getCardPwd());
        }
    };

    private void callHttpCompletedActivation(String carNum,String pwd)
    {
        ApiStores.vipCardValidate( carNum,pwd,new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    Utils.showToast(VipActivity.this,"激活成功");
                }
                else
                {
                    FailureDataUtils.showServerReturnErrorMessageFragment(VipActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP);
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataError(null);
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

    private void callHttpForRecharge(boolean isWX)
    {
        ApiStores.vipCardBuy(m_strCardId,isWX, new HttpCallback<ResponseBaseBean>()
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
                                .build().toWXPayNotSign(VipActivity.this);
                    }
                    else
                    {
                        RechargeHandler rechargeHandler = new RechargeHandler(VipActivity.this, new OnTaskSuccessComplete() {
                            @Override
                            public void onSuccess(Object obj) {

                            }
                        });
                        RechargeThread rechargeThread = new RechargeThread(VipActivity.this,rechargeHandler,RechargeDataUtils.getZHFRechargeData(data));
                        rechargeThread.start();
                    }
                }
                else
                {
                    FailureDataUtils.showServerReturnErrorMessageEx(VipActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(VipActivity.this, "错误", message);
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

    @Override
    protected BaseRecyclerAdapter getListAdapter() {
        return m_vipAdapter;
    }

    @Override
    protected void requestData() {
        ApiStores.vipCardList( new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseVipBean responseVipBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseVipBean.class);

                    executeOnLoadDataSuccess(responseVipBean.getLists(),true);
                }
                else
                {
                    executeOnLoadDataError(null);
                    FailureDataUtils.showServerReturnErrorMessageFragment(VipActivity.this,response,Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataError(null);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish()
            {
                executeOnLoadFinish();
            }

        });
    }
}
