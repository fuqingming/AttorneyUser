package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.WXRechargeBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.base.BaseHttpCompatActivity;
import com.xinfu.attorneyuser.bean.base.AskBean;
import com.xinfu.attorneyuser.bean.base.AskLawyerBean;
import com.xinfu.attorneyuser.bean.base.PriceBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.MessageFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问律师页
 * author 付庆明
 */

public class AskLawyerActivity extends BaseHttpCompatActivity{

    public static final int ADD_CLASSIFICATION_RESULT = 1;

    @BindView(R.id.tv_lawyer_number)
    TextView m_tvLawyerNum;
    @BindView(R.id.tv_hyjs)
    CheckBox m_tvHyjs;
    @BindView(R.id.tv_mjjd)
    CheckBox m_tvMjjd;
    @BindView(R.id.tv_fcjf)
    CheckBox m_tvFcjf;
    @BindView(R.id.tv_jtsg)
    CheckBox m_tvJtsg;
    @BindView(R.id.tv_ldzc)
    CheckBox m_tvLdzc;
    @BindView(R.id.tv_htjf)
    CheckBox m_tvHtjf;
    @BindView(R.id.ll_select)
    LinearLayout m_llSelect;

    @BindView(R.id.one_layout)
    LinearLayout oneStarLlayout;
    @BindView(R.id.two_layout)
    LinearLayout twoStarLlayout;
    @BindView(R.id.three_layout)
    LinearLayout threeStarLlayout;

    private ArrayList<PriceBean> m_arrSelectList;
    private int m_iSelectPrice = 0;
    private int m_iGrade = 1;
    private int m_iAmount = 0;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_ask_lawyer;
    }

    @Override
    protected void init() {
        setEventBus();
        m_arrSelectList = new ArrayList<>();
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"律师咨询",true);
    }

    @OnClick({R.id.tv_hyjs, R.id.tv_mjjd, R.id.tv_fcjf, R.id.tv_jtsg, R.id.tv_ldzc, R.id.tv_htjf, R.id.tv_more,
            R.id.one_layout, R.id.two_layout, R.id.three_layout, R.id.btn_ok, R.id.img_help})
    public void onViewClick(View v)
    {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_hyjs:
                selectMod(m_tvHyjs);
                break;
            case R.id.tv_mjjd:
                selectMod(m_tvMjjd);
                break;
            case R.id.tv_fcjf:
                selectMod(m_tvFcjf);
                break;
            case R.id.tv_jtsg:
                selectMod(m_tvJtsg);
                break;
            case R.id.tv_ldzc:
                selectMod(m_tvLdzc);
                break;
            case R.id.tv_htjf:
                selectMod(m_tvHtjf);
                break;

            case R.id.one_layout:
                initSelectView(oneStarLlayout);
                break;
            case R.id.two_layout:
                initSelectView(twoStarLlayout);
                break;
            case R.id.three_layout:
                initSelectView(threeStarLlayout);
                break;

            case R.id.tv_more:
                intent = new Intent(AskLawyerActivity.this, ClassificationActivity.class);
                intent.putExtra("list",  m_arrSelectList);
                /*intent = new Intent(mContext, LawyerDetailsActivity.class);*/
                startActivityForResult(intent, ADD_CLASSIFICATION_RESULT);
                break;
            case R.id.img_help:
                intent = new Intent(AskLawyerActivity.this, StarServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ok:
                btnCommit();
                break;
        }
    }

    private void btnCommit()
    {
        if(isInputValid())
        {
            if (m_iSelectPrice > GlobalVariables.getWallect()) {
                Utils.showDialog(this, R.layout.center_dialog_layout, obj -> {
                    Intent it = new Intent(this,MyWalletActivity.class);
                    it.putExtra("forResult",MyWalletActivity.RECHARGE_FOR_RESULT);
                    startActivity(it);
                });
            }  else {
                callHttpForCommit();
            }
        }
    }

    private boolean isInputValid()
    {
        if(m_arrSelectList.size() == 0)
        {
            Utils.showToast(this,"请选择案件类型");
            return false;
        }

        if (m_iSelectPrice == 0) {
            Utils.showToast(this,"请选择律师的星级");
            return  false;
        }

        return true;
    }


    private void initSelectView(LinearLayout linearLayout)
    {
        oneStarLlayout.setBackgroundResource(R.color.white);
        twoStarLlayout.setBackgroundResource(R.color.white);
        threeStarLlayout.setBackgroundResource(R.color.white);
        linearLayout.setBackgroundResource(R.color.gray_pressed2);
        if(linearLayout.getId() == R.id.one_layout)
        {
            m_iGrade = 1;
            m_iSelectPrice = 30;
        }
        else if (linearLayout.getId() == R.id.two_layout)
        {
            m_iGrade = 2;
            m_iSelectPrice = 80;
        }
        else if (linearLayout.getId() == R.id.three_layout)
        {
            m_iGrade = 3;
            m_iSelectPrice = 200;
        }
    }

    //选择取消选择方法
    public void selectMod(CheckBox view)
    {
        if(!view.isChecked())
        {
            for(int i = 0 ; i < m_arrSelectList.size() ; i ++)
            {
                if(m_arrSelectList.get(i).getName().equals(view.getText().toString().trim()))
                {
                    m_arrSelectList.remove(i);
                    break;
                }
            }
        }
        else
        {
            if(m_arrSelectList.size() >= 4)
            {
                view.setChecked(false);
                Utils.showToast(this, "最多只能选4个");
                return;
            }
            m_arrSelectList.add(new PriceBean(view.getTag().toString().trim(),view.getText().toString().trim()));
        }

        addViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CLASSIFICATION_RESULT && resultCode == RESULT_OK)
        {
            ArrayList<PriceBean> priceBeans = (ArrayList<PriceBean>) data.getSerializableExtra("list");
            m_arrSelectList.clear();
            m_arrSelectList.addAll(priceBeans);
            addViews();
        }
    }

    private void addViews()
    {
        m_llSelect.removeAllViews();
        for (int i = 0; i < m_arrSelectList.size(); i++)
        {
            View inflate = LayoutInflater.from(this).inflate(R.layout.ask_class_item, null);
            TextView tv_item = inflate.findViewById(R.id.tv_item);
            tv_item.setText(m_arrSelectList.get(i).getName().trim());
            m_llSelect.addView(inflate);
        }
    }

    private void callHttpForCommit()
    {
        ApiStores.askGetLawList(m_iGrade, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    AskLawyerBean askLawyerBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), AskLawyerBean.class);
                    if(askLawyerBean.getWzid() != null)
                    {
                        Intent it = new Intent(AskLawyerActivity.this,NotifyLawyerActivity.class);
                        it.putExtra("iGrade",m_iGrade);
                        it.putExtra("wzid",askLawyerBean.getWzid());
                        startActivity(it);
                    }
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(AskLawyerActivity.this, "错误", message);
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
    protected void getData() {
        ApiStores.getOnlineNum(1, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    executeOnLoadDataSuccess(true);

                    AskBean askBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), AskBean.class);

                    m_tvLawyerNum.setText(MessageFormat.format("当前有{0}位律师在线为您解答", askBean.getCount()));
                }
                else
                {
                    executeOnLoadDataSuccess(false);
                    FailureDataUtils.showServerReturnErrorMessageEx(AskLawyerActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataSuccess(false);
                AlertUtils.MessageAlertShow(AskLawyerActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Object obj)
    {

    }
}
