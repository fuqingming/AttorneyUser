package com.xinfu.attorneyuser;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.base.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.xinfu.attorneyuser.R;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class AgreementActivity extends BaseAppCompatActivity {
    @BindView(R.id.ll_set_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_set_titleName)
    TextView tv_name;
    @BindView(R.id.wb_agreement_content)
    WebView wb_agreement;
    private static String AGREEMENT="http://112.124.115.81/lawyer/user/law_show/xieyi";//注册协议详情-(可转换为局部变量)----避免static String导致内存溢出/泄露

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgreementActivity.this.finish();
            }
        });
        tv_name.setText("注册协议");
        //wb_agreement = (WebView) findViewById(R.id.wb_agreement_content);
        //设置WebView自适应屏幕
        WebSettings settings = wb_agreement.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置WebView属性，能够执行Javascript脚本
        wb_agreement.getSettings().setJavaScriptEnabled(true);
        wb_agreement.loadUrl(AGREEMENT);
    }
}
