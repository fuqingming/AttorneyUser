package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinfu.attorneyuser.base.BaseHttpCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseMyCardBean;
import com.xinfu.attorneyuser.bean.response.ResponseUserInfoBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.RoundImageUtil;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的卡券
 * author 付庆明
 */

public class MyCardActivity extends BaseHttpCompatActivity{

    @BindView(R.id.iv_pic)
    ImageView m_ivPic;
    @BindView(R.id.iv_icon)
    ImageView m_ivIcon;
    @BindView(R.id.tv_name)
    TextView m_tvName;
    @BindView(R.id.tv_time)
    TextView m_tvTime;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_my_card;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"我的卡券",true);
    }

    @Override
    protected void initData()
    {

    }

    @OnClick({R.id.btn_commit})
    public void onViewClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_commit:
                Intent it = new Intent(MyCardActivity.this,VipActivity.class);
                startActivity(it);
                break;

        }
    }

    @Override
    protected void getData() {
        ApiStores.vipCardInfo( new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    executeOnLoadDataSuccess(true);
                    ResponseMyCardBean data = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseMyCardBean.class);

                    ImageLoader.getInstace().loadImg(MyCardActivity.this, m_ivPic,data.getVip().getBgimg());
                    RoundImageUtil.setRoundImage(MyCardActivity.this,data.getCommon().getHead(),m_ivIcon);
                    m_tvName.setText(data.getCommon().getNickname());
                    m_tvTime.setText(MessageFormat.format("{0}到期", data.getVip().getExpireAt()));
                }
                else
                {
                    executeOnLoadDataSuccess(false);
                    FailureDataUtils.showServerReturnErrorMessageEx(MyCardActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataSuccess(false);
                AlertUtils.MessageAlertShow(MyCardActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }
}
