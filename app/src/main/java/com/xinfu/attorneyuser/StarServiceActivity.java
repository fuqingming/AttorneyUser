package com.xinfu.attorneyuser;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xinfu.attorneyuser.base.BaseAppCompatActivity;

import butterknife.BindView;

/**
 * Created by LoveSnow on 2017/7/31.
 */

public class StarServiceActivity extends BaseAppCompatActivity{

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_right)
    ImageView tv_right;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_star_service;
    }

    @Override
    protected void initView() {
        iv_left.setBackgroundResource(R.mipmap.back_03);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_right.setVisibility(View.INVISIBLE);
    }
}
