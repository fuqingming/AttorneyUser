package com.hyphenate.easeui.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.easeui.R;

public class Utils {

    public static Dialog showCancleOrderDialog(final Context context, final OnTaskSuccessComplete onTaskSuccess)
    {
        View.OnClickListener onLeftButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTaskSuccess != null)
                {
                    onTaskSuccess.onSuccess(false);
                }
            }
        };

        View.OnClickListener onRightButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTaskSuccess != null)
                {
                    onTaskSuccess.onSuccess(true);
                }
            }
        };

        return showCommonDialog(context,
                "提示",
                "是否取消通知律师。",
                "取消",
                onLeftButtonClickListener,
                "确认",
                onRightButtonClickListener);
    }

    public static Dialog showCommonDialog(final Context context,
                                          final String strTitle,
                                          final String strMsg,
                                          final String strLeftButtonText,
                                          final View.OnClickListener onLeftButtonClickListener,
                                          final String strRightButtonText,
                                          final View.OnClickListener onRightButtonClickListener)
    {
        View vContent = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        final Dialog dlg = new Dialog(context, R.style.common_dialog);
        dlg.setContentView(vContent);
        dlg.setCanceledOnTouchOutside(false); // 点击窗口外区域不消失
        dlg.setCancelable(false); // 返回键不消失

        // 必须用代码调整dialog的大小
        android.view.WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        //lp.width = (int) (MyApplication.s_nScreenWidth * 0.95);
        //lp.height = (int) (MyApplication.m_nScreenHeight * 0.5);
        lp.width = (int) context.getResources().getDimension(R.dimen.dialog_width);
        dlg.getWindow().setAttributes(lp);

        // title
        TextView tvTitle = vContent.findViewById(R.id.tv_title);
        if (strTitle != null && !strTitle.isEmpty())
        {
            tvTitle.setText(strTitle);
        }
        else
        {
            tvTitle.setVisibility(View.GONE);
        }

        // msg
        TextView tvMsg =  vContent.findViewById(R.id.tv_msg);
        tvMsg.setText(strMsg);

        // left button
        Button btnLeft =  vContent.findViewById(R.id.btn_left);
        btnLeft.setText(strLeftButtonText);
        btnLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onLeftButtonClickListener != null)
                {
                    onLeftButtonClickListener.onClick(v);
                }
            }
        });

        // right button
        Button btnRight = vContent.findViewById(R.id.btn_right);
        btnRight.setText(strRightButtonText);
        btnRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onRightButtonClickListener != null)
                {
                    onRightButtonClickListener.onClick(v);
                }
            }
        });

        dlg.show();

        return dlg;
    }
}
