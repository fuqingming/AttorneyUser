package com.xinfu.attorneyuser.utils.alert;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;

/**
 * Created by HH
 * Date: 2017/11/24
 */

public class Alert extends Dialog implements View.OnClickListener
{
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName = "确定";
    private String negativeName;
    private String title;


    public Alert(Context context)
    {
        super(context);
        this.mContext = context;
    }
    public Alert(Context context, String content)
    {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public Alert(Context context, int themeResId, String content)
    {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public Alert(Context context, int themeResId, String content, OnCloseListener listener)
    {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected Alert(Context context, boolean cancelable, OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public Alert setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public Alert setPositiveButton(String name)
    {
        this.positiveName = name;
        return this;
    }

    public Alert setNegativeButton(String name)
    {
        this.negativeName = "×";
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView()
    {
        contentTxt = (TextView)findViewById(R.id.content);
        titleTxt = (TextView)findViewById(R.id.title);
        submitTxt = (TextView)findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView)findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if(!TextUtils.isEmpty(positiveName))
        {
            submitTxt.setText(positiveName);
        }
        else
        {
            this.submitTxt.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(negativeName))
        {
            cancelTxt.setText(negativeName);
        }

        if(!TextUtils.isEmpty(title))
        {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.cancel)
        {
            if (listener != null)
            {
                listener.onClick(this, false);
            }
            this.dismiss();

        }
        else if (i == R.id.submit)
        {
            if (listener != null)
            {
                listener.onClick(this, true);
            }

        }
    }

    public interface OnCloseListener
    {
        void onClick(Dialog dialog, boolean confirm);
    }
}
