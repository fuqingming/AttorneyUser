package com.xinfu.attorneyuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.hyphenate.chat.EMClient;
import com.joker.pager.BannerPager;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseHttpFragment;
import com.xinfu.attorneyuser.bean.base.BannerBean;
import com.xinfu.attorneyuser.bean.response.ResponseBannerBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseDataUtils;
import com.xinfu.attorneyuser.https.ResponseResultBean;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.ui.ChatActivity;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.UtilsBanner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问律师页，地址参数传文字
 * Created by LoveSnow on 2017/7/10.
 */

public class FragmentGroupAsk extends BaseHttpFragment {

    @BindView(R.id.banner_pager)
    BannerPager m_bpBanner;
    @BindView(R.id.banner_pager_bottom)
    BannerPager m_bpBannerBottom;

    private ArrayList<BannerBean> m_arrBannerBean;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_group_ask;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            m_arrBannerBean = (ArrayList<BannerBean>) savedInstanceState.getSerializable("LIST_DATA");
        }
    }

    @Override
    protected void initData()
    {
        setEventBus();

        UtilsBanner.bannerFile(getMContext(), m_bpBannerBottom, new OnTaskSuccessComplete()
        {
            @Override
            public void onSuccess(Object obj)
            {
                int position = (int)obj;
                switch (position)
                {
                    case 0 :
                        Intent it = new Intent(getMContext(),ContractActivity.class);
                        startActivity(it);
                        break;
                }
            }
        });
    }

    @Override
    protected void getData() {
        if(m_arrBannerBean != null && m_arrBannerBean.size() > 0 )
        {
            executeOnLoadDataSuccess(true);
            initBanner();
            return;
        }
        ApiStores.getSlideAd(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    executeOnLoadDataSuccess(true);

                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseBannerBean>();
                    ResponseBannerBean bannerBeans = (ResponseBannerBean) responseDataUtils.getListData(response.getData(),ResponseBannerBean.class);
                    m_arrBannerBean = new ArrayList<>();
                    m_arrBannerBean.addAll(bannerBeans.getData());

                    initBanner();
                }
                else
                {
                    executeOnLoadDataSuccess(false);
                    FailureDataUtils.showServerReturnShowErrorMessageFragment(getMContext(),response,Const.ForResultData.LOGIN_FRAGMENT_GET_DATA);
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataSuccess(false);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }

    private void initBanner()
    {
        UtilsBanner.banner(getMContext(), m_bpBanner,m_arrBannerBean, new OnTaskSuccessComplete() {
            @Override
            public void onSuccess(Object obj) {
                Intent intent = new Intent(getMContext(), LawyerDetailsActivity.class);
                intent.putExtra("law_id", m_arrBannerBean.get((int)obj).getId());
                getMContext().startActivity(intent);
            }
        });
    }

    @OnClick({R.id.iv_ask,R.id.iv_customer,R.id.iv_vip})
    public void onViewClick(View v) {
        Intent it;
        switch (v.getId()) {
            case R.id.iv_ask:
                it = new Intent(getMContext(),AskLawyerActivity.class);
                getMContext().startActivity(it);
                break;
            case R.id.iv_customer:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.EXTRA_USER_ID, "kefu");
                intent.putExtra(Constant.CHATTYPE_KEFU, true);
                intent.putExtra(Constant.CHATTYPE_CHAT_TIME, "900");
                startActivity(intent);
                break;
            case R.id.iv_vip:
                it = new Intent(getMContext(),VipActivity.class);
                getMContext().startActivity(it);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(m_bpBanner!=null){
            m_bpBanner.stopTurning();
        }

        if(m_bpBannerBottom!=null){
            m_bpBannerBottom.stopTurning();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(m_bpBanner!=null){
            m_bpBanner.startTurning();
        }

        if(m_bpBannerBottom!=null){
            m_bpBannerBottom.startTurning();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Object obj)
    {
        ResponseResultBean responseResultBean = (ResponseResultBean) obj;
        if(responseResultBean.getCode() == Const.ForResultData.LOGIN_FRAGMENT_GET_DATA)
        {
            getData();
        }
        else if(responseResultBean.getCode() == Const.ForResultData.LOGOUT)
        {
            requestCallLogout();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LIST_DATA", m_arrBannerBean);
    }

    //退出登录
    private void requestCallLogout()
    {
        ApiStores.logout(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                EMClient.getInstance().logout(true);
                GlobalVariables.setUserId("");
                GlobalVariables.setHXName("");
                GlobalVariables.setHXPwd("");
                GlobalVariables.setUserPhone("");
                GlobalVariables.setUserWallect(0);
                GlobalVariables.setUserHead("");
                GlobalVariables.setToken("");
                GlobalVariables.setUserName("");
                Intent it = new Intent(getMContext(),LoginActivity.class);
                startActivity(it);
                getActivity().finish();

            }

            @Override
            public void OnFailure(final String message){}

            @Override
            public void OnRequestStart()
            {

            }

            @Override
            public void OnRequestFinish(){}

        });
    }
}
