package com.xinfu.attorneyuser;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.xinfu.attorneyuser.base.FragmentActivityBase;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.DemoHelper;
import com.xinfu.attorneyuser.huanxin.HMSPushHelper;
import com.xinfu.attorneyuser.huanxin.db.UserDao;
import com.xinfu.attorneyuser.huanxin.runtimepermissions.PermissionsManager;
import com.xinfu.attorneyuser.huanxin.runtimepermissions.PermissionsResultAction;

public class MainActivity extends FragmentActivityBase {

    private boolean isExceptionDialogShow =  false;
    private android.app.AlertDialog.Builder exceptionBuilder;

    // Tab文字及图标
    private int m_iconArray[] = {   R.drawable.main_tab_hall_selector,
                                    R.drawable.main_tab_actual_combat_selector,
                                    R.drawable.main_tab_tool_selector,
                                    R.drawable.main_tab_open_account_selector,
                                    R.drawable.main_tab_mine_selector};

    // 各tab页对应的fragment
    private Class<?> m_fragmentArray[] = {  FragmentGroup.class,
                                            FragmentConvenience.class,
                                            FragmentAssistantTool.class,
                                            FragmentInformation.class,
                                            FragmentVadio.class,};

    private String m_textArray[] = null;

    private FragmentTabHost m_tabHost = null;
    private LayoutInflater m_layoutInflater = null;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();

        m_textArray = getResources().getStringArray(R.array.lottery_main_tab_text);

        m_layoutInflater = LayoutInflater.from(this);

    }

    @Override
    protected void setUpView()
    {
        m_tabHost = findViewById(android.R.id.tabhost);
        m_tabHost.setup(this, getSupportFragmentManager(), R.id.fl_real_tabcontent);
        m_tabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
        for (int i = 0; i < m_fragmentArray.length; i++)
        {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = m_tabHost.newTabSpec(m_fragmentArray[i].getName()).setIndicator(getTabItemView(i));

            // 将Tab按钮添加进Tab选项卡中
            m_tabHost.addTab(tabSpec, m_fragmentArray[i], null);

            // 设置Tab按钮的背景
            //m_tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_item_bg_selector);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }
        UserDao userDao = new UserDao(this);
        requestPermissions();
        // 获取华为 HMS 推送 token
        HMSPushHelper.getInstance().getHMSToken(this);

    }

    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index)
    {
        View vTab = m_layoutInflater.inflate(R.layout.main_tab_item_view, null);

        ImageView ivIcon = vTab.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(m_iconArray[index]);

        TextView tvText = vTab.findViewById(R.id.tv_text);
        tvText.setText(m_textArray[index]);

        return vTab;
    }

    public void setCurrentTab(Class<?> fragmentClass)
    {
        m_tabHost.setCurrentTabByTag(fragmentClass.getName());
    }

    private long lastBackPress;
    @Override
    public void onBackPressed()//手机自带返回键所用
    {
        if (m_tabHost.getCurrentTab() != 0)
        {
            setCurrentTab(FragmentGroup.class);
            return;
        }

        if (System.currentTimeMillis() - lastBackPress < 1000) {
            super.onBackPressed();
        } else {
            lastBackPress = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次注销", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }



    private void showExceptionDialogFromIntent(Intent intent) {
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            this.finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false,null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
            } catch (Exception e) {
            }
        }
    }

    private int getExceptionMessageId(String exceptionType) {
        if(exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }
}
