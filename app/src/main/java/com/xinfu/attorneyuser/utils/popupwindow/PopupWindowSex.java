package com.xinfu.attorneyuser.utils.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.utils.Utils;

public class PopupWindowSex extends PopupWindow
{
    public static final int RECHARGE_WX = 1;
    public static final int RECHARGE_ZFB = 2;

    public PopupWindowSex(final Activity context, final OnTaskSuccessComplete onTaskSuccessComplete)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View m_pwMenuView = inflater.inflate(R.layout.ppw_sex_service, null);
        Button btnNan = m_pwMenuView.findViewById(R.id.btn_nan);
        Button btnNv = m_pwMenuView.findViewById(R.id.btn_nv);

        this.setContentView(m_pwMenuView);						//设置SelectPicPopupWindow的View
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);	//设置SelectPicPopupWindow弹出窗体的宽
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);	//设置SelectPicPopupWindow弹出窗体的高
        this.setFocusable(true);								//设置SelectPicPopupWindow弹出窗体可点击
        this.setAnimationStyle(R.style.AnimBottom);				//设置SelectPicPopupWindow弹出窗体动画效果
        ColorDrawable dw = new ColorDrawable(0xb0000000);		//实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(dw);							//设置SelectPicPopupWindow弹出窗体的背景

        m_pwMenuView.setOnTouchListener(new View.OnTouchListener()
        {

            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event)
            {

                int height = m_pwMenuView.findViewById(R.id.popu_layout).getTop();
                int y = (int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(y < height)
                    {
                        dismiss();
                    }
                }
                return true;
            }
        });

        btnNan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskSuccessComplete.onSuccess("男");
                dismiss();
            }
        });

        btnNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskSuccessComplete.onSuccess("女");
                dismiss();
            }
        });

    }
}
