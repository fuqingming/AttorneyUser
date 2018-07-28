package com.xinfu.attorneyuser.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.xinfu.attorneyuser.R;

/**
 * Created by Ruby on 2017/4/20.
 */
       //进度条对话框
public class ProgressBarDialog extends Dialog {

    public ProgressBarDialog(Context context) {
        super(context);
    }

    public ProgressBarDialog(Context context, int themeResId) {
        super(context, themeResId);
    }




    @Override   //点击取消时的操作
    public void cancel() {

    }

    @Override   //需在后台执行的任务
    public void onBackPressed() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pro);
    }

}
