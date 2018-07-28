package com.xinfu.attorneyuser.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.AskLawyerBean;
import com.xinfu.attorneyuser.bean.base.BalanceBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.dialog.WaitDialog;

public class CallHttpUserBalanceUtil {

    public static void callHttpBalance(Context context, WaitDialog waitDialog, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        ApiStores.userBalance(new HttpCallback<ResponseBaseBean>()
        {

            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    BalanceBean data = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), BalanceBean.class);
                    GlobalVariables.setUserWallect(data.getCommon().getBalance());
                    if(onTaskSuccessComplete!=null)
                    {
                        onTaskSuccessComplete.onSuccess(true);
                    }
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(context, "错误", message);
                if(onTaskSuccessComplete!=null)
                {
                    onTaskSuccessComplete.onSuccess(false);
                }
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
