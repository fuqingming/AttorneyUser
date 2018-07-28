package com.xinfu.attorneyuser;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.base.BaseApplication;
import com.xinfu.attorneyuser.bean.base.LoginBean;
import com.xinfu.attorneyuser.bean.base.YzmBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseResultBean;
import com.xinfu.attorneyuser.huanxin.DemoHelper;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RegexUtil;
import com.xinfu.attorneyuser.utils.SmsSendCounter;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class BindPhoneActivity extends BaseAppCompatActivity
{

    private static final int RESEND_VERIFY_CODE_SECOND = 60;
    private SmsSendCounter m_myCount = null;

    @BindView(R.id.et_register_tel)
    EditText m_etTel;
    @BindView(R.id.et_register_code)
    EditText m_etCode;
    @BindView(R.id.tv_register_getCode)
    TextView m_tvCode;
    @BindView(R.id.et_register_pwd)
    EditText m_etPwd;
    @BindView(R.id.et_register_cPwd)
    EditText m_etConfirmPwd;
    @BindView(R.id.cb_register_agree)
    CheckBox m_cbGgree;
    @BindView(R.id.tv_register_confirm)
    TextView m_tvBtn;

    private String m_strPhone;
    private String m_strPhoneSend;
    private String m_strVerifyNumber;
    private String m_strPassword;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_register;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"绑定手机",true);
        m_tvBtn.setText("绑定");
    }

    @OnClick({R.id.tv_register_getCode, R.id.tv_register_agreement, R.id.tv_register_confirm})
    public void onViewClick(View view)
    {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_register_getCode://获取验证码
                if(isPhoneValid())
                {
                    callHttpForSendYzm();
                }
                break;
            case R.id.tv_register_agreement://用户协议详情
                intent = new Intent(BindPhoneActivity.this, AgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register_confirm://注册
                if(isInputValid())
                {
                    if(!m_strPhoneSend.equals(m_strPhone))
                    {
                        Utils.showToast(BindPhoneActivity.this,"手机号已改变，请重新获取验证码");
                        return;
                    }
                    callHttpForRegister();
                }
                break;
        }
    }

    private void callHttpForSendYzm()
    {
        ApiStores.smsSend(m_strPhone, Const.YzmCode.yzm_code_type_register, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    m_tvCode.setEnabled(false);
                    m_tvCode.setText(String.valueOf(RESEND_VERIFY_CODE_SECOND));
                    m_myCount = new SmsSendCounter(BindPhoneActivity.this,m_tvCode, RESEND_VERIFY_CODE_SECOND * 1000, 1000);
                    m_myCount.start();

                    m_strPhoneSend = m_strPhone;

                    YzmBean yzmBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), YzmBean.class);
                    String code = yzmBean.getCode();
                }
                else
                {
                    Utils.showToast(BindPhoneActivity.this,response.getResult());
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(BindPhoneActivity.this, "错误", message);
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

    private void callHttpForRegister()
    {
        ApiStores.oauthBindPhone(getIntent().getStringExtra("id"),m_strPhone, m_strPassword,m_strVerifyNumber, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    LoginBean loginBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), LoginBean.class);
                    GlobalVariables.setUserId(loginBean.getUser_id());
                    GlobalVariables.setHXName(loginBean.getHx_user());
                    GlobalVariables.setHXPwd(loginBean.getHx_pass());
                    GlobalVariables.setUserPhone(loginBean.getPhone());
                    GlobalVariables.setUserWallect((Double.valueOf(loginBean.getWallect())).intValue());
                    GlobalVariables.setUserHead(loginBean.getHead());
                    GlobalVariables.setToken(loginBean.getToken());
                    GlobalVariables.setUserName(loginBean.getUser().getName());

                    if (!DemoHelper.getInstance().isLoggedIn())
                    {
                        loginHX();
                    }else{
                        login();
                    }
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(BindPhoneActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart()
            {
                waitDialog.show();
            }

            @Override
            public void OnRequestFinish(){}

        });
    }

    private void loginHX()
    {
        EMClient.getInstance().login(GlobalVariables.getHXName(), GlobalVariables.getHXPwd(),new EMCallBack() {

            @Override
            public void onSuccess() {

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        BaseApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                if(waitDialog.isShowing())
                {
                    waitDialog.dismiss();
                }

                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                login();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        waitDialog.dismiss();
                        Utils.showToast(BindPhoneActivity.this,getString(R.string.Login_failed) + message);
                    }
                });
            }
        });
    }

    private void login()
    {
        if(waitDialog.isShowing())
        {
            waitDialog.dismiss();
        }

        Intent intent = new Intent(BindPhoneActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private boolean isPhoneValid()
    {
        m_strPhone = m_etTel.getText().toString().trim();
        if(m_strPhone.isEmpty())
        {
            Utils.showToast(this, "请输入手机号码");
            m_etTel.requestFocus();
            return false;
        }
        else if(m_strPhone.length() < 11)
        {
            Utils.showToast(this, "手机号码需要11位长度");
            m_etTel.requestFocus();
            return false;
        }
        else if(!RegexUtil.checkMobile(m_strPhone))
        {
            Utils.showToast(this, "请输入正确的手机号码");
            m_etTel.requestFocus();
            return false;
        }
        return true;
    }

    // 检查输入项是否输入正确
    private boolean isInputValid()
    {
        m_strPhone = m_etTel.getText().toString().trim();
        if(m_strPhone.isEmpty())
        {
            Utils.showToast(this, "请输入手机号码");
            m_etTel.requestFocus();
            return false;
        }
        else if(m_strPhone.length() < 11)
        {
            Utils.showToast(this, "手机号码需要11位长度");
            m_etTel.requestFocus();
            return false;
        }
        else if(!RegexUtil.checkMobile(m_strPhone))
        {
            Utils.showToast(this, "请输入正确的手机号码");
            m_etTel.requestFocus();
            return false;
        }

        // 验证码
        m_strVerifyNumber = m_etCode.getText().toString().trim();
        if(m_strVerifyNumber.isEmpty())
        {
            Utils.showToast(this, "请输入验证码");
            m_etCode.requestFocus();
            return false;
        }
        else if(m_strVerifyNumber.length() < 6)
        {
            Utils.showToast(this, "验证码为6位");
            m_etCode.requestFocus();
            return false;
        }

        m_strPassword = m_etPwd.getText().toString().trim();
        if(m_strPassword.isEmpty())
        {
            Utils.showToast(this, "请输入密码");
            m_etPwd.requestFocus();
            return false;
        }
        else if(m_strPassword.length() < Const.FieldRange.PASSWORD_MIN_LEN)
        {
            Utils.showToast(this,"密码不能少于6位");
            m_etPwd.requestFocus();
            return false;
        }
//        else if(!RegexUtil.checkPassword(m_strPassword))
//        {
//            Utils.showToast(this,"输入6～18位数字字母组合密码");
//            m_etPwd.requestFocus();
//            return false;
//        }

        String strPassword = m_etConfirmPwd.getText().toString().trim();
        if(strPassword.isEmpty())
        {
            Utils.showToast(this, "请输入确认密码");
            m_etConfirmPwd.requestFocus();
            return false;
        }
        else if(strPassword.length() < Const.FieldRange.PASSWORD_MIN_LEN)
        {
            Utils.showToast(this,"确认密码不能少于6位");
            m_etConfirmPwd.requestFocus();
            return false;
        }
//        else if(!RegexUtil.checkPassword(strPassword))
//        {
//            Utils.showToast(this,"输入6～18位数字字母组合确认密码");
//            m_etConfirmPwd.requestFocus();
//            return false;
//        }

        if(!m_strPassword.equals(strPassword))
        {
            Utils.showToast(this,"两次密码不一致");
            return false;
        }

        if(!m_cbGgree.isChecked())
        {
            Utils.showToast(this,"请同意用户协议");
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent it = new Intent (this,LoginActivity.class);
        startActivity(it);
        finish();
    }
}
