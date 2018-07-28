package com.xinfu.attorneyuser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mob.tools.utils.UIHandler;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.base.BaseApplication;
import com.xinfu.attorneyuser.bean.base.LoginBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseResultBean;
import com.xinfu.attorneyuser.huanxin.DemoApplication;
import com.xinfu.attorneyuser.huanxin.DemoHelper;
import com.xinfu.attorneyuser.huanxin.db.DemoDBManager;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RegexUtil;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseAppCompatActivity implements PlatformActionListener, Handler.Callback
{
    private static final int MSG_ACTION_CCALLBACK = 0;

    private ProgressDialog progressDialog;

    @BindView(R.id.et_login_account)
    EditText m_etAccount;
    @BindView(R.id.et_login_pwd)
    EditText m_etPwd;

    private String m_strPhone;
    private String m_strPassword;

    private int m_iLoginType;
    private String m_iOauthLoginType;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        Utils.setOnTouchEditTextOutSideHideIM(this);
    }

    @Override
    protected void initData() {
        m_iLoginType = getIntent().getIntExtra("iLoginType",Const.ForResultData.LOGIN_FIRST);
    }

    // 检查输入项是否输入正确
    private boolean isInputValid()
    {
        m_strPhone = m_etAccount.getText().toString().trim();
        if(m_strPhone.isEmpty())
        {
            Utils.showToast(this, "请输入手机号码");
            m_etAccount.requestFocus();
            return false;
        }
        else if(m_strPhone.length() < 11)
        {
            Utils.showToast(this, "手机号码需要11位长度");
            m_etAccount.requestFocus();
            return false;
        }
        else if(!RegexUtil.checkMobile(m_strPhone))
        {
            Utils.showToast(this, "请输入正确的手机号码");
            m_etAccount.requestFocus();
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
        DemoDBManager.getInstance().closeDB();
        DemoHelper.getInstance().setCurrentUserName(m_strPhone);
        return true;
    }

    @OnClick({R.id.tv_login_forgetPwd, R.id.tv_login_register, R.id.tv_login, R.id.ll_login_qq, R.id.ll_login_wx, R.id.ll_login_sina})
    public void onViewClick(View view)
    {
        Intent it = null;
        switch (view.getId())
        {
            case R.id.tv_login_forgetPwd://忘记密码
                it = new Intent(LoginActivity.this,ForgetPwdActivity.class);
                startActivity(it);
                break;
            case R.id.tv_login_register://注册账号
                it = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(it);
                break;
            case R.id.tv_login://登录
                if(isInputValid())
                {
                    callHttpForLogin();
                }
                break;
            case R.id.ll_login_qq://qq登录
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(this);
                qq.SSOSetting(false);
                authorize(qq);
                m_iOauthLoginType = "3";
                break;
            case R.id.ll_login_wx://微信登录
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.SSOSetting(false);
                authorize(wechat);
                m_iOauthLoginType = "2";
                break;
            case R.id.ll_login_sina://新浪登录
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                //关闭sso授权
                sina.SSOSetting(true);
                sina.setPlatformActionListener(this);
                sina.SSOSetting(false);
                authorize(sina);
                m_iOauthLoginType = "4";
                break;
        }
    }

    private void authorize(Platform plat) {
        waitDialog.show();
        if (plat.isAuthValid()) {//如果授权，就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    //登录授权成功的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);

    }

    //登录授权错误的回调
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
    }

    //登录授权取消的回调
    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    //登陆发送的handle消息在这里处理
    @Override
    public boolean handleMessage(Message message)
    {
        switch (message.arg1)
        {
            case 1:
                {
                //获取用户资料
                Platform platform = (Platform) message.obj;
                String userId = platform.getDb().getUserId();//获取用户账号
                String userName = platform.getDb().getUserName();//获取用户名字
                String userIcon = platform.getDb().getUserIcon();//获取用户头像
                String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                String token = platform.getDb().getToken();
                if("m".equals(userGender))
                {
                    userGender = "男";
                }
                else if("f".equals(userGender))
                {
                    userGender = "女";
                }
                else
                {
                    userGender = "";
                }

                callHttpForOauthLogin(m_iOauthLoginType,userId,userIcon,userName,token,userGender);
            }
            break;
            case 2: {
                waitDialog.dismiss();
                Toast.makeText(this, "授权登录失败", Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: {
                waitDialog.dismiss();
                Toast.makeText(this, "授权登录取消", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

    private void callHttpForLogin()
    {
        ApiStores.login(m_strPhone, m_strPassword, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                            BaseApplication.currentUserNick.trim());
                    if (!updatenick) {
                        Log.e("LoginActivity", "update current user nick fail");
                    }

                    // get user's info (this should be get from App's server or 3rd party service)
                    DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
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
                else
                {
                    Utils.showToast(LoginActivity.this,response.getResult());
                    waitDialog.dismiss();
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                waitDialog.dismiss();
                AlertUtils.MessageAlertShow(LoginActivity.this, "错误", message);
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

    private void callHttpForOauthLogin(String type,
                                       String openId,
                                       String image,
                                       String nickname,
                                       String accessToken,
                                       String sex)
    {
        ApiStores.oauthLogin(   type,
                                openId,
                                image,
                                nickname,
                                accessToken,
                                sex,
                                new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    LoginBean loginBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), LoginBean.class);
                    if("no".equals(loginBean.getIsphone()))
                    {
                        waitDialog.dismiss();
                        Intent it = new Intent(LoginActivity.this,BindPhoneActivity.class);
                        it.putExtra("id",loginBean.getUser_id());
                        startActivity(it);
                        finish();
                        return;
                    }
                    GlobalVariables.setUserId(loginBean.getUser_id());
                    GlobalVariables.setHXName(loginBean.getUser().getHx_user());
                    GlobalVariables.setHXPwd(loginBean.getUser().getHx_pass());
                    GlobalVariables.setUserPhone(loginBean.getUser().getPhone());
                    GlobalVariables.setUserWallect((Double.valueOf(loginBean.getUser().getWallect())).intValue());
                    GlobalVariables.setUserHead(loginBean.getUser().getHead());
                    GlobalVariables.setToken(loginBean.getToken());
                    GlobalVariables.setUserName(loginBean.getUser().getName());

                    if (!DemoHelper.getInstance().isLoggedIn())
                    {
                        loginHX();
                    }else{
                        login();
                    }
                }
                else
                {
                    Utils.showToast(LoginActivity.this,response.getResult());
                    waitDialog.dismiss();
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(LoginActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart(){}

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
                        Utils.showToast(LoginActivity.this,getString(R.string.Login_failed) + message);
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

        if(m_iLoginType == Const.ForResultData.LOGIN_ACTIVITY_GET_DATA)
        {
            setResult(RESULT_OK);
            finish();
        }
        else if(m_iLoginType == Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP)
        {
            finish();
        }
        else if(m_iLoginType == Const.ForResultData.LOGIN_FRAGMENT_GET_DATA)
        {
            EventBus.getDefault().post(new ResponseResultBean(Const.ForResultData.LOGIN_FRAGMENT_GET_DATA));
            finish();
        }
        else if(m_iLoginType == Const.ForResultData.LOGIN_FRAGMENT_CALL_HTTP)
        {
            EventBus.getDefault().post(new ResponseResultBean(Const.ForResultData.LOGIN_FRAGMENT_CALL_HTTP));
            finish();
        }
        else
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    // 按两次返回键退出程序
    private static final int WAIT_NEXT_KEY_BACK_DURATION = 1000 * 2;
    private Boolean m_bFistKeyBackPressed = false;
    private Boolean m_bIsWaitingNextKeyBack = false;
    private Timer m_timerWaitingNextKeyBack = new Timer();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!m_bFistKeyBackPressed)
            {
                m_bFistKeyBackPressed = true;

                Utils.showToast(this, "再按一次退出程序");

                if (!m_bIsWaitingNextKeyBack)
                {
                    m_bIsWaitingNextKeyBack = true;

                    m_timerWaitingNextKeyBack.schedule(new TimerTask()
                    {
                        public void run()
                        {
                            m_bFistKeyBackPressed = false;
                            m_bIsWaitingNextKeyBack = false;
                        }
                    }, WAIT_NEXT_KEY_BACK_DURATION);
                }
                return true;
            }
            else
            {
                finish();
            }
        }
        return false;
    }
}
