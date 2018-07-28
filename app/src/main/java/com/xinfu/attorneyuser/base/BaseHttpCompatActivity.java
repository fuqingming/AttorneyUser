package com.xinfu.attorneyuser.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.xinfu.attorneyuser.LoginActivity;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.BackHandlerHelper;
import com.xinfu.attorneyuser.https.HttpClient;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalInstanceStateHelper;
import com.xinfu.attorneyuser.utils.dialog.WaitDialog;
import com.xinfu.attorneyuser.view.ErrorLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by HH
 * Date: 2017/11/9
 */

public abstract class BaseHttpCompatActivity extends AppCompatActivity
{

    protected WaitDialog waitDialog;
    protected ErrorLayout mErrorLayout;
    private boolean isHaveEventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
        setContentView(setLayoutResourceId());
        ButterKnife.bind(this);
        mErrorLayout = (ErrorLayout) findViewById(R.id.error_layout);
        mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                getData();
            }
        });
        waitDialog = new WaitDialog(this);
        initView();
        initData();
        clickView();
        getData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    protected abstract void getData();

    protected void init()
    {
        HttpClient.init(getApplicationContext(),true);
    }

    protected void initView(){}

    protected void initData(){}

    protected void clickView(){}

    protected View.OnClickListener setIntent(int iLoginType)
    {
        View.OnClickListener onOkClickListener = v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("iLoginType", iLoginType);
            startActivityForResult(intent,iLoginType);
        };

        return onOkClickListener;
    }

    @Override
    protected void onResume()
    {
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }

    protected abstract int setLayoutResourceId();

    @Override
    public void onBackPressed()
    {
        if (!BackHandlerHelper.handleBackPress(this))
        {
            super.onBackPressed();
        }
    }

    @Override
    public Resources getResources()
    {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    protected void executeOnLoadDataSuccess(boolean isSuccess)
    {
        if(isSuccess)
        {
            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        }
        else
        {
            mErrorLayout.setErrorType(ErrorLayout.NETWORK_ERROR);
        }
    }

    protected void setEventBus()
    {
        isHaveEventBus = true;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(isHaveEventBus)
        {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // 保存MyApplication中保存的全局变量
        GlobalInstanceStateHelper.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        GlobalInstanceStateHelper.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Const.ForResultData.LOGIN_ACTIVITY_GET_DATA && resultCode == RESULT_OK)	{
            getData();
        }
    }
}
