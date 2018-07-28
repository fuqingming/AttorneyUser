package com.xinfu.attorneyuser.utils;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;

/**
 * Created by Administrator on 2017/3/9.
 */

public class IMHelper {

    public static void EMLogin(final String userName, final String password, OnTaskSuccessComplete onTaskSuccess) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                Boolean conn= EMClient.getInstance().isConnected();

                onTaskSuccess.onSuccess(true);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                switch (code) {
                    case EMError.USER_NOT_FOUND:
                        EMReg(userName, password);
                        break;
                    default:

                        break;
                }
                onTaskSuccess.onSuccess(false);
            }
        });
    }

    public static void EMLogin(final String userName, final String password) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                Boolean conn= EMClient.getInstance().isConnected();

                Log.i("123","123");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                switch (code) {
                    case EMError.USER_NOT_FOUND:
                        EMReg(userName, password);
                        break;
                    default:

                        break;
                }
            }
        });
    }

    public static void EMReg(String userName, String password) {
        try {
            EMClient.getInstance().createAccount(userName, password);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }



}
