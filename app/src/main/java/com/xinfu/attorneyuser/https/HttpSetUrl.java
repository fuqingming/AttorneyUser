package com.xinfu.attorneyuser.https;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created by vip on 2018/5/12.
 */

public class HttpSetUrl
{
    public final static String serverSp = "serverSp";

    private static final String APP_URL = "AppUrl";	// 访问url
//    private static final String X_AUTH_CITY = "X-Auth-City";    //Header
//    private static final String X_AUTH_UUID = "X-Auth-uuid";    //Header
//    private static final String X_AUTH_TOKEN = "X-Auth-Token";    //Header

    private HttpSetUrl()
    {
        // 不允许声明AppSettings对象
    }

    // 要访问的url
    public static String getAppUrl()
    {
        return SPUtils.getInstance(serverSp).getString(APP_URL,"");
    }

    public static void setAppUrl(String strAppUrl)
    {
        SPUtils.getInstance(serverSp).put(APP_URL,strAppUrl);
    }
//
//    // Header X-Auth-City
//    public static String getHeaderAuthCity()
//    {
//        return SPUtils.getInstance(serverSp).getString(X_AUTH_CITY,"0");
//    }
//    public static void setHeaderAuthCity(String strHeaderAuthCity)
//    {
//        SPUtils.getInstance(serverSp).put(X_AUTH_CITY,strHeaderAuthCity);
//    }
//
//    // Header X-Auth-uuid
//    public static String getHeaderAuthUuid()
//    {
//        return SPUtils.getInstance(serverSp).getString(X_AUTH_UUID,"0");
//    }
//    public static void setHeaderAuthUuid(String strHeaderAuthUuid)
//    {
//        SPUtils.getInstance(serverSp).put(X_AUTH_UUID,strHeaderAuthUuid);
//    }
//
//    // Header X-Auth-Token
//    public static String getHeaderAuthToken()
//    {
//        return SPUtils.getInstance(serverSp).getString(X_AUTH_TOKEN,"1");
//    }
//    public static void setHeaderAuthToken(String strHeaderAuthToken)
//    {
//        SPUtils.getInstance(serverSp).put(X_AUTH_TOKEN,strHeaderAuthToken);
//    }
}
