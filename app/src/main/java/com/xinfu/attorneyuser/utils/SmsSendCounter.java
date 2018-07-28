package com.xinfu.attorneyuser.utils;

/**
 * Created by Administrator on 2018/6/27.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;

/**
 * @author 付庆明
 *
 */

/**倒计时*/
public class SmsSendCounter extends CountDownTimer
{
    TextView m_btn = null;
    Context mContext;

    public SmsSendCounter(Context context, TextView btn, long millisInFuture, long countDownInterval)
    {
        super(millisInFuture, countDownInterval);
        m_btn = btn;
        mContext = context;
    }

    @Override
    public void onFinish()
    {
        m_btn.setEnabled(true);
        m_btn.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
        m_btn.setText("获取验证码");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished)
    {
        m_btn.setText(String.valueOf(millisUntilFinished / 1000+"秒后重发"));
        m_btn.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
    }
}