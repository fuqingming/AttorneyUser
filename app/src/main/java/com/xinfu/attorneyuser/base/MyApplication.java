package com.xinfu.attorneyuser.base;

import android.util.Log;

public class MyApplication extends MyTransApplication
{
	private static final String LOG_TAG = "MyApplication";
	
	private static MyApplication s_instance = null;
	
	/*****************************************************************************/

	// Activity被回收时，保存全局变量的版本号（每保存一次，自增1）
	public int m_nSaveInstanceStateVersion = 0;
	/*****************************************************************************/

	//登录用户Id
//	public String userId = "";
//	public String userPhone = "";
//	public String userWallect = "";//用户余额（律币）
//	public String userIcon = "";//用户头像
//	public String userHXName = "";
//	public String userHXPwd = "";
//	public String token = "";
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		Log.d(LOG_TAG, "onCreate()");
		
//		if (TEST_ENVIRONMENT) 
//		{
//			CrashHandler crashHandler = CrashHandler.getInstance();
//			crashHandler.init(getApplicationContext());
//		}
		s_instance = this;
	}
	
	public static MyApplication getInstance()
	{
		return s_instance;
	}
	
	public void init()
	{
		/*****************************************************************************/
		// Activity被回收时，保存全局变量的版本号（每保存一次，自增1）
		m_nSaveInstanceStateVersion = 0;
		/*****************************************************************************/

//		userId = "";
//		userPhone = "";
//		userWallect = "";//用户余额（律币）
//		userIcon = "";//用户头像
//		userHXName = "";
//		userHXPwd = "";
//		token = "";
	}
}
