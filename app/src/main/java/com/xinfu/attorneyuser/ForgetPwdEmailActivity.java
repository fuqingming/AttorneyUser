package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.base.YzmBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RegexUtil;
import com.xinfu.attorneyuser.utils.SmsSendCounter;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class ForgetPwdEmailActivity extends BaseAppCompatActivity
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

    private String m_strPhone;
    private String m_strPhoneSend;
    private String m_strVerifyNumber;
    private String m_strPassword;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_forget_pwd_email;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"忘记密码",true);
    }

    @OnClick({R.id.tv_register_getCode,R.id.tv_register_confirm})
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
            case R.id.tv_register_confirm://注册
                if(isInputValid())
                {
                    if(!m_strPhoneSend.equals(m_strPhone))
                    {
                        Utils.showToast(ForgetPwdEmailActivity.this,"手机号已改变，请重新获取验证码");
                        return;
                    }
                    callHttpForRegister();
                }
                break;
        }
    }

    private void callHttpForSendYzm()
    {
        ApiStores.emailBack(m_strPhone, Const.YzmCode.yzm_code_type_forget_pwd, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    m_tvCode.setEnabled(false);
                    m_tvCode.setText(String.valueOf(RESEND_VERIFY_CODE_SECOND));
                    m_myCount = new SmsSendCounter(ForgetPwdEmailActivity.this,m_tvCode, RESEND_VERIFY_CODE_SECOND * 1000, 1000);
                    m_myCount.start();

                    m_strPhoneSend = m_strPhone;

                    YzmBean yzmBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), YzmBean.class);
                }
                else
                {
                    Utils.showToast(ForgetPwdEmailActivity.this,response.getResult());
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(ForgetPwdEmailActivity.this, "错误", message);
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
        ApiStores.emailSetPass(m_strPhone, m_strPassword,m_strVerifyNumber, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    finish();
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(ForgetPwdEmailActivity.this, "错误", message);
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

    private boolean isPhoneValid()
    {
        m_strPhone = m_etTel.getText().toString().trim();
        if(m_strPhone.isEmpty())
        {
            Utils.showToast(this, "请输入邮箱账户");
            m_etTel.requestFocus();
            return false;
        }
        else if(!RegexUtil.checkEmail(m_strPhone))
        {
            Utils.showToast(this, "您输入的邮箱格式错误");
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
            Utils.showToast(this, "请输入邮箱账户");
            m_etTel.requestFocus();
            return false;
        }
        else if(!RegexUtil.checkEmail(m_strPhone))
        {
            Utils.showToast(this, "您输入的邮箱格式错误");
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
        else if(!RegexUtil.checkPassword(m_strPassword))
        {
            Utils.showToast(this,"输入6～18位数字字母组合密码");
            m_etPwd.requestFocus();
            return false;
        }

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
        else if(!RegexUtil.checkPassword(strPassword))
        {
            Utils.showToast(this,"输入6～18位数字字母组合确认密码");
            m_etConfirmPwd.requestFocus();
            return false;
        }

        if(!m_strPassword.equals(strPassword))
        {
            Utils.showToast(this,"两次密码不一致");
            return false;
        }

        return true;
    }
}
