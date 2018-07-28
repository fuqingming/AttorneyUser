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
import com.xinfu.attorneyuser.utils.Utils;

public class PopupwindowRechargeEdit extends PopupWindow
{
    public PopupwindowRechargeEdit(final Activity context, final OnTaskSuccessComplete onTaskSuccessComplete)
    {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View m_pwMenuView = inflater.inflate(R.layout.ppw_recharge_edit_service, null);
        EditText m_etCount = m_pwMenuView.findViewById(R.id.et_count);
        TextView m_tvCommit = m_pwMenuView.findViewById(R.id.tv_commit);

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

        m_tvCommit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String strAcount = m_etCount.getText().toString().trim();
                if(strAcount.isEmpty() || "0".equals(strAcount))
                {
                    Utils.showToast(context, "请输入金额");
                    m_etCount.requestFocus();
                    return;
                }
                dismiss();
                onTaskSuccessComplete.onSuccess(Integer.parseInt(strAcount));
            }
        });
    }
}
