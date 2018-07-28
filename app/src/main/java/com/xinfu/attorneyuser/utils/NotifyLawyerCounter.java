package com.xinfu.attorneyuser.utils;

/**
 * Created by Administrator on 2018/6/27.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;

/**
 * @author 付庆明
 *
 */

/**通知律师倒计时*/
public class NotifyLawyerCounter extends CountDownTimer
{
    TextView m_btn = null;
    Context mContext;
    OnTaskSuccessComplete mOnTaskSuccessComplete;

    public NotifyLawyerCounter(Context context, TextView btn, long millisInFuture, long countDownInterval, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        super(millisInFuture, countDownInterval);
        m_btn = btn;
        mContext = context;
        this.mOnTaskSuccessComplete = onTaskSuccessComplete;
    }

    @Override
    public void onFinish()
    {
        m_btn.setEnabled(true);
        m_btn.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
        m_btn.setText("点击继续接通律师");
        if(mOnTaskSuccessComplete != null)
        {
            mOnTaskSuccessComplete.onSuccess(null);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished)
    {
        m_btn.setText(String.valueOf("正在接通律师" + millisUntilFinished / 1000));
        m_btn.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
    }
}