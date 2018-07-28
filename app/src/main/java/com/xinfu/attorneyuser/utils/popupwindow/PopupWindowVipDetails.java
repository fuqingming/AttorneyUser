package com.xinfu.attorneyuser.utils.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.VipBean;

/**
 * Created by vip on 2018/5/18.
 */

public class PopupWindowVipDetails extends PopupWindow
{
    public static final int ONLINE_PURCHASE = 1;    //线上购买
    public static final int CARD_ACTIVATION = 2;    //卡券激活

    public PopupWindowVipDetails(final Activity context, final VipBean vipBean, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View m_pwMenuView = inflater.inflate(R.layout.ppw_vip_details, null);
        TextView tvTitle = m_pwMenuView.findViewById(R.id.tv_title);
        TextView tvText1 = m_pwMenuView.findViewById(R.id.tv_text1);
        TextView tvText2 = m_pwMenuView.findViewById(R.id.tv_text2);
        TextView tvBtn1 = m_pwMenuView.findViewById(R.id.tv_btn1);
        TextView tvBtn2 = m_pwMenuView.findViewById(R.id.tv_btn2);
        this.setContentView(m_pwMenuView);						//设置SelectPicPopupWindow的View
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);	//设置SelectPicPopupWindow弹出窗体的宽
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);	//设置SelectPicPopupWindow弹出窗体的高
        this.setFocusable(true);								//设置SelectPicPopupWindow弹出窗体可点击
        this.setAnimationStyle(R.style.AnimBottom);				//设置SelectPicPopupWindow弹出窗体动画效果
        ColorDrawable dw = new ColorDrawable(0xb0000000);		//实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(dw);							//设置SelectPicPopupWindow弹出窗体的背景

        tvTitle.setText(vipBean.getTitle());
        tvText1.setText(vipBean.getSummary());
        tvText2.setText(vipBean.getDescription());

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

        tvBtn1.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
                onTaskSuccessComplete.onSuccess(ONLINE_PURCHASE);
            }
        });

        tvBtn2.setOnClickListener(new View.OnClickListener()//取消
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
                onTaskSuccessComplete.onSuccess(CARD_ACTIVATION);
            }
        });
    }
}