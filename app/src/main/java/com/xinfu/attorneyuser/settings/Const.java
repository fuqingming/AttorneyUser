package com.xinfu.attorneyuser.settings;

/**
 * Created by asus on 2018/4/17.
 */

public class Const {

    // 默认密匙，在签到时使用
    public static final String DEFAULT_WORK_KEY = "a123456789012345678901234567890b";

    public class ErrorCode
    {
        public static final String SJT_SIGN_INVALID = "未授权的请求";		// 签名错误
    }

    public class YzmCode
    {
        public static final int yzm_code_type_register = 1;		    // 注册
        public static final int yzm_code_type_forget_pwd = 2;		// 忘记密码
    }


    public class FieldRange
    {
        public static final int PASSWORD_MIN_LEN = 6;		// 密码最小长度
        public static final int PASSWORD_MAX_LEN = 18;		// 密码最大长度
    }

    public class ForResultData
    {
        public static final int LOGIN_FIRST = 10;                    //正常登陆
        public static final int LOGIN_ACTIVITY_CALL_HTTP = 11;       //在Activity登陆 CallHttp登陆后直接finish
        public static final int LOGIN_ACTIVITY_GET_DATA = 12;        //在Activity登陆 getData启动回调
        public static final int LOGIN_FRAGMENT_GET_DATA = 13;        //在Fragment登陆启动EventBus  getData
        public static final int LOGIN_FRAGMENT_CALL_HTTP = 14;       //在Fragment登陆启动EventBus  callHttp

        public static final int LOGOUT = 201;                        //退出登录


        public static final int WX_RECHARGE_SUCCESS = 15;            //微信支付成功
        public static final int WX_RECHARGE_FAIL = 16;               //微信支付失败
    }
}
