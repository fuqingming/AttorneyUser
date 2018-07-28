package com.xinfu.attorneyuser;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.xinfu.attorneyuser.base.BaseFragment;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.utils.RegexUtil;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 调查服务页
 * Created by LoveSnow on 2017/7/10.
 */

public class FragmentGroupInvestigation extends BaseFragment {

    @BindView(R.id.name_edt)
    EditText m_etName;
    @BindView(R.id.phone_edt)
    EditText m_etTel;
    @BindView(R.id.content_edt)
    EditText m_etContent;
    @BindView(R.id.text1)
    TextView m_tvBtn1;
    @BindView(R.id.text2)
    TextView m_tvBtn2;
    @BindView(R.id.text3)
    TextView m_tvBtn3;
    @BindView(R.id.text4)
    TextView m_tvBtn4;

    private int m_iType = -1;
    private String m_strPhone;
    private String m_strName;
    private String m_strContent;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.fragment_group_investigation;
    }

    private void getData()
    {
        ApiStores.userAddService("find",m_iType, m_strName,m_strPhone,m_strContent,"",new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    m_etName.setText("");
                    m_etTel.setText("");
                    m_etContent.setText("");
                    Utils.showToast(getMContext(),"操作成功");
                    changeState(null);
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(getMContext(), "错误", message);
            }

            @Override
            public void OnRequestStart()
            {
                waitDialog.show();
            }

            @Override
            public void OnRequestFinish()
            {
                waitDialog.dismiss();
            }

        });
    }

    @OnClick({R.id.btn_ok,R.id.text1,R.id.text2,R.id.text3,R.id.text4})
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_ok:
                if(isInputValid())
                {
                    getData();
                }
                break;
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
        }
    }

    // 检查输入项是否输入正确
    private boolean isInputValid()
    {
        if(m_iType == -1)
        {
            Utils.showToast(getMContext(), "请选择类型");
            return false;
        }

        m_strName = m_etName.getText().toString().trim();
        if(m_strName.isEmpty())
        {
            Utils.showToast(getMContext(), "请输入姓名");
            m_etName.requestFocus();
            return false;
        }

        m_strPhone = m_etTel.getText().toString().trim();
        if(m_strPhone.isEmpty())
        {
            Utils.showToast(getMContext(), "请输入手机号码");
            m_etTel.requestFocus();
            return false;
        }
        else if(m_strPhone.length() < 11)
        {
            Utils.showToast(getMContext(), "手机号码需要11位长度");
            m_etTel.requestFocus();
            return false;
        }
        else if(!RegexUtil.checkMobile(m_strPhone))
        {
            Utils.showToast(getMContext(), "请输入正确的手机号码");
            m_etTel.requestFocus();
            return false;
        }

        m_strContent = m_etContent.getText().toString().trim();

        return true;
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

        if(tv != null)
        {
            tv.setBackgroundResource(R.drawable.text_shape4);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
    }

}
