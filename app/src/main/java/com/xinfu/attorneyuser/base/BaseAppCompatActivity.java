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
import com.xinfu.attorneyuser.backhandler.BackHandlerHelper;
import com.xinfu.attorneyuser.https.HttpClient;
import com.xinfu.attorneyuser.settings.GlobalInstanceStateHelper;
import com.xinfu.attorneyuser.utils.dialog.WaitDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by HH
 * Date: 2017/11/9
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity
{

    protected WaitDialog waitDialog;
    private boolean isHaveEventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
        setContentView(setLayoutResourceId());
        ButterKnife.bind(this);
        initView();
        initData();
        clickView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
    protected void onPause()
    {

        super.onPause();
    }

    protected abstract int setLayoutResourceId();

    protected void init()
    {
        HttpClient.init(getApplicationContext(),true);
        waitDialog = new WaitDialog(this);
    }

    protected void initData(){}

    protected void initView() {}

    protected void clickView() {}

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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(isHaveEventBus)
        {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void setEventBus()
    {
        isHaveEventBus = true;
        EventBus.getDefault().register(this);
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
}
