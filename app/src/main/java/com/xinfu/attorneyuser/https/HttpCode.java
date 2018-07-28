package com.xinfu.attorneyuser.https;

/**
 * Created by Administrator on 2017/3/15.
 */
//以下为状态码
public interface HttpCode {
    int HTTPCODE_ERROR = 0;   //0为错误码
    int HTTPCODE_SUCCESS = 1;  //1为成功码
    int HTTPCODE_FAIL = 2;     //2为响应失败
    int HTTPCODE_ISLOGIN = 2000;  //Activity Http getData登录
}
