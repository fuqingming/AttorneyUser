package com.xinfu.attorneyuser;


import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.LawuerCommonAdapter;
import com.xinfu.attorneyuser.base.BaseHttpCompatActivity;
import com.xinfu.attorneyuser.bean.base.FindLawyerBean;
import com.xinfu.attorneyuser.bean.base.FindLawyerContinueBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseFocusBean;
import com.xinfu.attorneyuser.bean.response.ResponseLawyerMainBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.ui.ChatActivity;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.recycler.RecycleViewDivider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 付庆明
 */

public class LawyerDetailsActivity extends BaseHttpCompatActivity {

    @BindView(R.id.iv_icon)
    ImageView m_ivIcon;
    @BindView(R.id.tv_name)
    TextView m_tvName;
    @BindView(R.id.tv_office)
    TextView m_tvOffice;
    @BindView(R.id.tv_profession1)
    TextView m_tvProfession1;
    @BindView(R.id.tv_profession2)
    TextView m_tvProfession2;
    @BindView(R.id.tv_profession3)
    TextView m_tvProfession3;
    @BindView(R.id.tv_profession4)
    TextView m_tvProfession4;
    @BindView(R.id.iv_star1)
    ImageView m_ivStar1;
    @BindView(R.id.iv_star2)
    ImageView m_ivStar2;
    @BindView(R.id.iv_star3)
    ImageView m_ivStar3;
    @BindView(R.id.tv_focus)
    TextView m_tvFocus;
    @BindView(R.id.tv_text)
    TextView m_tvText;
    @BindView(R.id.tv_advice)
    TextView m_tvAdvice;
    @BindView(R.id.tv_message)
    TextView m_tvMessage;
    @BindView(R.id.list)
    RecyclerView m_recyclerView;
    @BindView(R.id.ll_profession2)
    LinearLayout m_llProfession2;
    @BindView(R.id.ll_profession3)
    LinearLayout m_llProfession3;
    @BindView(R.id.ll_profession4)
    LinearLayout m_llProfession4;

    private LawuerCommonAdapter m_adapterCommon = new LawuerCommonAdapter();

    private int m_iGrade = 1;
    private String m_strLid;
    private String m_strLawyserIcon;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_lawyer_main;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"律师主页",true);
    }

    @Override
    protected void initData()
    {
        m_strLid = getIntent().getStringExtra("law_id");

        m_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        m_recyclerView.setItemAnimator(new DefaultItemAnimator());
        m_recyclerView.setAdapter(m_adapterCommon);
        m_recyclerView.setNestedScrollingEnabled(false);
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.app_backgrount_color));
        m_recyclerView.addItemDecoration(recycleViewDivider);
    }

    private void isFocus(int iFocusType)
    {
        if(iFocusType == 1)
        {
            m_tvFocus.setText("已关注");
        }
        else
        {
            m_tvFocus.setText("关注");
        }
    }

    @Override
    protected void getData() {
        ApiStores.homePage(m_strLid,new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    executeOnLoadDataSuccess(true);
                    ResponseLawyerMainBean data = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseLawyerMainBean.class);

                    m_tvName.setText(data.getRes().getUsername());
                    m_tvOffice.setText(data.getRes().getOffice());
                    ImageLoader.getInstace().loadCircleImg(LawyerDetailsActivity.this, m_ivIcon, data.getRes().getHeadimg());

                    m_strLawyserIcon = data.getRes().getHeadimg();

                    isFocus(data.getIs_follow());

                    if(data.getRes().getProfession().size() >= 4)
                    {
                        m_tvProfession1.setText(data.getRes().getProfession().get(0).getName());
                        m_tvProfession1.setVisibility(View.VISIBLE);
                        setTextVisible(data.getRes().getProfession().get(1).getName(),m_tvProfession2,m_llProfession2);
                        setTextVisible(data.getRes().getProfession().get(2).getName(),m_tvProfession3,m_llProfession3);
                        setTextVisible(data.getRes().getProfession().get(3).getName(),m_tvProfession4,m_llProfession4);
                    }
                    else if(data.getRes().getProfession().size() == 3)
                    {
                        m_tvProfession1.setText(data.getRes().getProfession().get(0).getName());
                        m_tvProfession1.setVisibility(View.VISIBLE);
                        setTextVisible(data.getRes().getProfession().get(1).getName(),m_tvProfession2,m_llProfession2);
                        setTextVisible(data.getRes().getProfession().get(2).getName(),m_tvProfession3,m_llProfession3);
                    }
                    else if(data.getRes().getProfession().size() == 2)
                    {
                        m_tvProfession1.setText(data.getRes().getProfession().get(0).getName());
                        m_tvProfession1.setVisibility(View.VISIBLE);
                        setTextVisible(data.getRes().getProfession().get(1).getName(),m_tvProfession2,m_llProfession2);
                    }
                    else if(data.getRes().getProfession().size() == 1)
                    {
                        m_tvProfession1.setText(data.getRes().getProfession().get(0).getName());
                        m_tvProfession1.setVisibility(View.VISIBLE);
                    }

                    if(data.getRes().getGrade() == 1)
                    {
                        m_ivStar1.setVisibility(View.VISIBLE);
                    }
                    else if(data.getRes().getGrade() == 2)
                    {
                        m_ivStar1.setVisibility(View.VISIBLE);
                        m_ivStar2.setVisibility(View.VISIBLE);
                    }
                    else if(data.getRes().getGrade() == 3)
                    {
                        m_ivStar1.setVisibility(View.VISIBLE);
                        m_ivStar2.setVisibility(View.VISIBLE);
                        m_ivStar3.setVisibility(View.VISIBLE);
                    }

                    m_iGrade = data.getRes().getGrade();

                    m_tvText.setText(data.getRes().getEvaluate());

                    if(data.getCommon().size() > 0)
                    {
                        m_adapterCommon.setDataList(data.getCommon());
                    }
                }
                else
                {
                    executeOnLoadDataSuccess(false);
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(LawyerDetailsActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataSuccess(false);
                AlertUtils.MessageAlertShow(LawyerDetailsActivity.this,"错误",message);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }

    private void callHttpForFocus()
    {
        ApiStores.focus(m_strLid, new HttpCallback<ResponseFocusBean>()
        {
            @Override
            public void OnSuccess(final ResponseFocusBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    isFocus(response.getFocus());
                }
                else
                {
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(LawyerDetailsActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP);
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(LawyerDetailsActivity.this, "错误", message);
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

    private void callHttpForCommit()
    {
        ApiStores.findConsult(m_iGrade,m_strLid, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    if("continue".equals(response.getResult()))
                    {
                        FindLawyerContinueBean data = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), FindLawyerContinueBean.class);
                        Intent intent = new Intent(LawyerDetailsActivity.this, ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, data.getHx_user());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        intent.putExtra(Constant.CHATTYPE_CHAT_TIME, data.getEnd_time());
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        FindLawyerBean askLawyerBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), FindLawyerBean.class);
                        if(askLawyerBean.getWzid() != null)
                        {
                            Intent it = new Intent(LawyerDetailsActivity.this,NotifyLawyerActivity.class);
                            it.putExtra("iGrade",m_iGrade);
                            it.putExtra("wzid",askLawyerBean.getWzid());
                            it.putExtra("strPic",m_strLawyserIcon);
                            it.putExtra(Constant.CHATTYPE_CHAT_TIME, "0");
                            startActivity(it);
                        }
                    }
                }
                else
                {
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(LawyerDetailsActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP);
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(LawyerDetailsActivity.this, "错误", message);
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

    private void setTextVisible(String strText,TextView text , LinearLayout lineVisible)
    {
        text.setText(strText);
        lineVisible.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_advice,R.id.tv_message,R.id.tv_focus})
    public void onViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_advice://咨询
                callHttpForCommit();
                break;
            case R.id.tv_message://留言
                Intent it = new Intent(LawyerDetailsActivity.this,LeaveMsgActivity.class);
                it.putExtra("strLawyerId","");
                startActivity(it);
                break;
            case R.id.tv_focus://关注
                callHttpForFocus();
                break;
        }
    }
}
