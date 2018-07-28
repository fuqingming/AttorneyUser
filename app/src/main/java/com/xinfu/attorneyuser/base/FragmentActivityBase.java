package com.xinfu.attorneyuser.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xinfu.attorneyuser.settings.GlobalInstanceStateHelper;

public abstract class FragmentActivityBase extends FragmentActivity
{

	private static final String LOG_TAG = "FragmentActivityBase";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
		setContentView(setLayoutResourceId());
		setUpView();
		setUpData();
	}

	protected abstract int setLayoutResourceId();

	protected void init(){}


	protected abstract void setUpView();

	protected void setUpData(){}
	
    @Override
    public Resources getResources()
	{
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();    
        res.updateConfiguration(config,res.getDisplayMetrics() );  
        return res;  
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
	protected void onDestroy() {
		super.onDestroy();
	}
}
