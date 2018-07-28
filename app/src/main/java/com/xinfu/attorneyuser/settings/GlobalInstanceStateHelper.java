package com.xinfu.attorneyuser.settings;

import android.content.Context;
import android.os.Bundle;

import com.xinfu.attorneyuser.base.MyApplication;
import com.xinfu.attorneyuser.https.HttpClient;

// 用来在Activity被回收时保存和恢复公共数据
public class GlobalInstanceStateHelper
{
	// 保存数据
	public static void saveInstanceState(Bundle outState)
	{
		MyApplication myApp = MyApplication.getInstance();
		
		/** Activity被回收时，保存全局变量的版本号（每保存一次，自增1）  */
		//public int m_nSaveInstanceStateVersion = 0;
		outState.putInt("m_nSaveInstanceStateVersion", ++myApp.m_nSaveInstanceStateVersion);

//		outState.putString("USER_ID", myApp.userId);
//		outState.putString("USER_PHONE", myApp.userPhone);
//		outState.putString("USER_WALLECT", myApp.userWallect);
//		outState.putString("USER_ICON", myApp.userIcon);
//		outState.putString("USER_HX_NAME", myApp.userHXName);
//		outState.putString("USER_HX_PWD", myApp.userHXPwd);
//		outState.putString("TOKEN", myApp.token);
	}
	
	// 恢复数据
	public static void restoreInstanceState(Context context, Bundle savedInstanceState)
	{
		MyApplication myApp = MyApplication.getInstance();
		
		/** Activity被回收时，保存全局变量的版本号（每保存一次，自增1）  */
		//public int m_nSaveInstanceStateVersion = 0;
		int nVersion = savedInstanceState.getInt("m_nSaveInstanceStateVersion");
		if (nVersion > myApp.m_nSaveInstanceStateVersion)
		{
			myApp.m_nSaveInstanceStateVersion = nVersion;
		}
		else
		{
			return;	// 不需要恢复（当前数据更新，或与要恢复的数据相同）
		}

//		myApp.userId = savedInstanceState.getString("USER_ID");
//		myApp.userPhone = savedInstanceState.getString("USER_PHONE");
//		myApp.userWallect = savedInstanceState.getString("USER_WALLECT");
//		myApp.userIcon = savedInstanceState.getString("USER_ICON");
//		myApp.userHXName = savedInstanceState.getString("USER_HX_NAME");
//		myApp.userHXPwd = savedInstanceState.getString("USER_HX_PWD");
//		myApp.token = savedInstanceState.getString("TOKEN");

		HttpClient.init(context.getApplicationContext(),true);


	}
}
