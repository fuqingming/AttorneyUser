package com.xinfu.attorneyuser.utils.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.VipCardActivationBean;
import com.xinfu.attorneyuser.utils.Utils;

/**
 * Created by vip on 2018/5/18.
 */

public class PopupWindowVipCardActivation extends PopupWindow
{
    public PopupWindowVipCardActivation(final Activity context, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View m_pwMenuView = inflater.inflate(R.layout.ppw_card_activation, null);
        EditText etNum = m_pwMenuView.findViewById(R.id.et_num);
        EditText etPwd = m_pwMenuView.findViewById(R.id.et_pwd);
        TextView tvBtn = m_pwMenuView.findViewById(R.id.tv_btn);
        this.setContentView(m_pwMenuView);						//设置SelectPicPopupWindow的View
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);	//设置SelectPicPopupWindow弹出窗体的宽
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);	//设置SelectPicPopupWindow弹出窗体的高
        this.setFocusable(true);								//设置SelectPicPopupWindow弹出窗体可点击
        this.setAnimationStyle(R.style.AnimBottom);				//设置SelectPicPopupWindow弹出窗体动画效果
        ColorDrawable dw = new ColorDrawable(0xb0000000);		//实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(dw);							//设置SelectPicPopupWindow弹出窗体的背景

        m_pwMenuView.setOnTouchListener(new View.OnTouchListener() //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
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

        tvBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String strNum = etNum.getText().toString().trim();
                if(strNum.isEmpty())
                {
                    etNum.requestFocus();
                    Utils.showToast(context,"请输入券号");
                    return;
                }
                String strPwd = etPwd.getText().toString().trim();
                if(strPwd.isEmpty())
                {
                    etPwd.requestFocus();
                    Utils.showToast(context,"请输入密码");
                    return;
                }

                dismiss();
                onTaskSuccessComplete.onSuccess(new VipCardActivationBean(strNum,strPwd));
            }
        });
    }
}