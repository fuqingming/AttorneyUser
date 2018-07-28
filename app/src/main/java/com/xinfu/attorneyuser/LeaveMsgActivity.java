package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.base.FindLawyerBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LeaveMsgActivity extends BaseAppCompatActivity {

    @BindView(R.id.et_leave)
    EditText m_etLeave;

    private String m_strId;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_leave_msg;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"留言",true);

        Utils.setOnTouchEditTextOutSideHideIM(this);
    }

    @Override
    protected void initData()
    {
        m_strId = getIntent().getStringExtra("strLawyerId");
    }

    @OnClick(R.id.btn_commit)
    public void onViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_commit:
                String strLeaveMsg = m_etLeave.getText().toString().trim();
                if(strLeaveMsg.isEmpty())
                {
                    Utils.showToast(LeaveMsgActivity.this,"请输入留言内容");
                    m_etLeave.requestFocus();
                    return;
                }
                callHttpForCommit(strLeaveMsg);
                break;
        }
    }

    private void callHttpForCommit(String strContent)
    {
        ApiStores.message(m_strId,strContent, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    Utils.showToast(LeaveMsgActivity.this,"留言成功");
                    finish();
                }
                else
                {
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(LeaveMsgActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP);
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(LeaveMsgActivity.this, "错误", message);
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
}
