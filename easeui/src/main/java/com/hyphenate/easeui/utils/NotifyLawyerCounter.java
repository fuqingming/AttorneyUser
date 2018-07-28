package com.hyphenate.easeui.utils;

/**
 * Created by Administrator on 2018/6/27.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.hyphenate.easeui.R;

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
        m_btn.setTextColor(mContext.getResources().getColor(R.color.btn_register_normal));
        m_btn.setText("0");
        if(mOnTaskSuccessComplete != null)
        {
            mOnTaskSuccessComplete.onSuccess(null);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished)
    {
        m_btn.setText(String.valueOf("" + timeParse(millisUntilFinished)));
        m_btn.setTextColor(mContext.getResources().getColor(R.color.btn_register_normal));
    }

    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;

    }
}