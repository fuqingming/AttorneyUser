package com.xinfu.attorneyuser.settings;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created by HH
 * Date: 2017/11/9
 */

public class GlobalVariables {

    /************SharedPreferences******************/
    public final static String serverSp = "serverSp";

    public final static String userId = "userId";
    public final static String userPhone = "userPhone";
    public final static String userWallect = "userWallect";//用户余额（律币）
    public final static String userIcon = "userIcon";
    public final static String userHXName = "userHXName";
    public final static String userHXPwd = "userHXPwd";
    public final static String token = "token";
    public final static String userHead = "userHead";
    public final static String userName = "userName";

    public final static String isFirstRun = "isFirstRun";

    private GlobalVariables()
    {
        // 不允许声明AppSettings对象
    }

    // 用户id
    public static String getUserId()
    {
        return SPUtils.getInstance(serverSp).getString(userId,"");
    }
    public static void setUserId(String strUserId)
    {
        SPUtils.getInstance(serverSp).put(userId,strUserId);
    }

    public static String getUserPhone()
    {
        return SPUtils.getInstance(serverSp).getString(userPhone,"");
    }
    public static void setUserPhone(String strUserPhone)
    {
        SPUtils.getInstance(serverSp).put(userPhone,strUserPhone);
    }

    public static int getWallect()
    {
        return SPUtils.getInstance(serverSp).getInt(userWallect);
    }
    public static void setUserWallect(int iUserWallect)
    {
        SPUtils.getInstance(serverSp).put(userWallect,iUserWallect);
    }

    public static String getUserIcon()
    {
        return SPUtils.getInstance(serverSp).getString(userIcon,"");
    }
    public static void setUserIcon(String strUserIcon)
    {
        SPUtils.getInstance(serverSp).put(userIcon,strUserIcon);
    }

    public static String getHXName()
    {
        return SPUtils.getInstance(serverSp).getString(userHXName,"");
    }
    public static void setHXName(String strHXName)
    {
        SPUtils.getInstance(serverSp).put(userHXName,strHXName);
    }

    public static String getHXPwd()
    {
        return SPUtils.getInstance(serverSp).getString(userHXPwd,"");
    }
    public static void setHXPwd(String strHXPwd)
    {
        SPUtils.getInstance(serverSp).put(userHXPwd,strHXPwd);
    }

    public static String getToken()
    {
        return SPUtils.getInstance(serverSp).getString(token,"");
    }
    public static void setToken(String strToken)
    {
        SPUtils.getInstance(serverSp).put(token,strToken);
    }

    public static String getUserHead()
    {
        return SPUtils.getInstance(serverSp).getString(userHead,"");
    }
    public static void setUserHead(String strHead)
    {
        SPUtils.getInstance(serverSp).put(userHead,strHead);
    }

    public static boolean isFirstRun() {
        boolean isFirstRuns = SPUtils.getInstance(serverSp).getBoolean("isFirstRun", true);
        if (isFirstRuns) {
            SPUtils.getInstance(serverSp).put(isFirstRun,false);
        }
        return isFirstRuns;
    }

    public static String getUserName()
    {
        return SPUtils.getInstance(serverSp).getString(userName,"");
    }
    public static void setUserName(String strUserName)
    {
        SPUtils.getInstance(serverSp).put(userName,strUserName);
    }
}
