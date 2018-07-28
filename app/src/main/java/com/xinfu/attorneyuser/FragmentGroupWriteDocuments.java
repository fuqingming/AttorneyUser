package com.xinfu.attorneyuser;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问律师页，地址参数传文字
 * Created by LoveSnow on 2017/7/10.
 */

public class FragmentGroupWriteDocuments extends BaseFragment {

    @BindView(R.id.text1)
    TextView m_tvBtn1;
    @BindView(R.id.text2)
    TextView m_tvBtn2;
    @BindView(R.id.text3)
    TextView m_tvBtn3;
    @BindView(R.id.text4)
    TextView m_tvBtn4;
    @BindView(R.id.text5)
    TextView m_tvBtn5;
    @BindView(R.id.text6)
    TextView m_tvBtn6;
    @BindView(R.id.text7)
    TextView m_tvBtn7;

    private int m_iType = -1;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.fragment_review_instruments;
    }

    private void changeState(TextView tv)
    {
        m_tvBtn1.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn1.setTextColor(Color.parseColor("#8B9EEB"));
        m_tvBtn2.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn2.setTextColor(Color.parseColor("#8B9EEB"));
        m_tvBtn3.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn3.setTextColor(Color.parseColor("#8B9EEB"));
        m_tvBtn4.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn4.setTextColor(Color.parseColor("#8B9EEB"));
        m_tvBtn5.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn5.setTextColor(Color.parseColor("#8B9EEB"));
        m_tvBtn6.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn6.setTextColor(Color.parseColor("#8B9EEB"));
        m_tvBtn7.setBackgroundResource(R.drawable.text_shape3);
        m_tvBtn7.setTextColor(Color.parseColor("#8B9EEB"));


        tv.setBackgroundResource(R.drawable.text_shape4);
        tv.setTextColor(Color.parseColor("#ffffff"));
    }

    @OnClick({R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7,R.id.tv_next})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                m_iType = 1;
                changeState((TextView) v);
                break;
            case R.id.text2:
                changeState((TextView) v);
                m_iType = 2;
                break;
            case R.id.text3:
                changeState((TextView) v);
                m_iType = 3;
                break;
            case R.id.text4:
                changeState((TextView) v);
                m_iType = 4;
                break;
            case R.id.text5:
                changeState((TextView) v);
                m_iType = 5;
                break;
            case R.id.text6:
                changeState((TextView) v);
                m_iType = 6;
                break;
            case R.id.text7:
                changeState((TextView) v);
                m_iType = 7;
                break;
            case R.id.tv_next:
                if (m_iType != -1) {
                    Intent it = new Intent(getMContext(),WriteDocumentsActivity.class);
                    it.putExtra("iCategory",m_iType);
                    getMContext().startActivity(it);
                } else {
                    Toast.makeText(getContext(), "请选择文书类型", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
