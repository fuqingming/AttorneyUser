package com.xinfu.attorneyuser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.ui.ChatActivity;
import com.xinfu.attorneyuser.settings.BroadcastConstant;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.NotifyLawyerCounter;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class NotifyLawyerActivity extends BaseAppCompatActivity {

    private static final int RESEND_VERIFY_CODE_SECOND = 60;
    private NotifyLawyerCounter m_myCount = null;

    @BindView(R.id.tv_notify)
    TextView m_tvNotify;
    @BindView(R.id.tv_title_back)
    TextView m_tvLeft;
    @BindView(R.id.iv_pic)
    ImageView m_ivPic;

    private String m_strWzid;
    private String m_strLawyerIcon;

    private MsgReceiver m_msgReceiver;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_notifylaywer;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"通知律师");
        m_tvLeft.setText("取消通知");
    }

    @Override
    protected void initData() {
        initNofifyLawyerCounter();
        registerReciever();

        m_strWzid = getIntent().getStringExtra("wzid");
        m_strLawyerIcon = getIntent().getStringExtra("strPic");
        if(m_strLawyerIcon != null)
        {
            ImageLoader.getInstace().loadCircleImg(NotifyLawyerActivity.this, m_ivPic, m_strLawyerIcon);
        }
    }

    private void initNofifyLawyerCounter()
    {
        m_tvNotify.setEnabled(false);
        m_tvNotify.setText(String.valueOf(RESEND_VERIFY_CODE_SECOND));
        m_myCount = new NotifyLawyerCounter(NotifyLawyerActivity.this, m_tvNotify, RESEND_VERIFY_CODE_SECOND * 1000, 1000,null);
        m_myCount.start();
    }

    @OnClick({R.id.tv_notify,R.id.tv_title_back})
    public void onViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_notify:
                callHttpForGetMsg();
                break;
            case R.id.tv_title_back:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Utils.showCancleOrderDialog(this, new OnTaskSuccessComplete() {
            @Override
            public void onSuccess(Object obj) {
                callHttpForCancle();
            }
        });
    }

    private void stopMyCount()
    {
        if (m_myCount != null)
        {
            m_myCount.cancel();
            m_myCount = null;
        }

        m_tvNotify.setEnabled(true);
        m_tvNotify.setText("获取手机验证码");
    }

    private void callHttpForGetMsg()
    {
        ApiStores.askGetLawList(getIntent().getIntExtra("iGrade",0), new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    m_tvNotify.setEnabled(false);
                    m_tvNotify.setText(String.valueOf(RESEND_VERIFY_CODE_SECOND));
                    m_myCount = new NotifyLawyerCounter(NotifyLawyerActivity.this,m_tvNotify, RESEND_VERIFY_CODE_SECOND * 1000, 1000,null);
                    m_myCount.start();
                    initNofifyLawyerCounter();
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(NotifyLawyerActivity.this, "错误", message);
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

    private void callHttpForCancle()
    {
        ApiStores.cancelOrder(m_strWzid, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    stopMyCount();
                    finish();
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(NotifyLawyerActivity.this, "错误", message);
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


    private void callHttpForConfirm(String strWzId, String strHXUserMsg)
    {
        ApiStores.askStartConsult(strWzId, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    stopMyCount();
                    Intent intent = new Intent(NotifyLawyerActivity.this, ChatActivity.class);
                    intent.putExtra(Constant.EXTRA_USER_ID, strHXUserMsg);
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    intent.putExtra(Constant.CHATTYPE_CHAT_TIME, "900");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(NotifyLawyerActivity.this, "错误", message);
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
        filter1.addAction(BroadcastConstant.BROADCAST_RECEIVEMESSAGE_CHAT);
        registerReceiver(m_msgReceiver, filter1);
    }

    class MsgReceiver extends BroadcastReceiver
    {
        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public void onReceive(Context arg0, Intent intent)
        {
            String strWzid = intent.getStringExtra("strWzid");
            String strHXUserMsg = intent.getStringExtra("strHXUserMsg");

            if(strWzid.isEmpty())
            {
                return;
            }

            Utils.showDialog(NotifyLawyerActivity.this, R.layout.center_dialog_chat_layout, new OnTaskSuccessComplete()
            {
                @Override
                public void onSuccess(Object obj)
                {
                    callHttpForConfirm(strWzid,strHXUserMsg);
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(m_msgReceiver);
    }
}
