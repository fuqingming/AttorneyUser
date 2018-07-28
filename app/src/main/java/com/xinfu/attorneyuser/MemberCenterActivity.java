package com.xinfu.attorneyuser;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.CallHttpUserBalanceUtil;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RoundImageUtil;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupwindowIntegralRecharge;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.MessageFormat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员中心
 * @Author 付庆明
 */

public class MemberCenterActivity extends BaseAppCompatActivity {

    @BindView(R.id.iv_icon)
    ImageView m_ivIcon;
    @BindView(R.id.tv_name)
    TextView m_tvName;
    @BindView(R.id.tv_integral)
    TextView m_tvIntegral;

    private String m_strScore;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_member_center;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"会员中心",true);
    }

    @Override
    protected void initData() {
        String m_strIcon = getIntent().getStringExtra("strIcon");
        String m_strName = getIntent().getStringExtra("strName");
        m_strScore = getIntent().getStringExtra("strScore");

        RoundImageUtil.setRoundImage(MemberCenterActivity.this, m_strIcon,m_ivIcon);
        m_tvName.setText(m_strName);
        if(m_strScore != null)
        {
            m_tvIntegral.setText(MessageFormat.format("积分 {0}", m_strScore));
        }
        else
        {
            m_tvIntegral.setText("积分 0");
        }
    }

    @OnClick({R.id.ll_history,R.id.ll_recharge,R.id.ll_details})
    public void onViewClick(View v)
    {
        switch (v.getId()) {
            case R.id.ll_history://我的足迹

                break;
            case R.id.ll_recharge://积分兑换
                if(m_strScore != null && Integer.parseInt(m_strScore) > 0)
                {
                    PopupwindowIntegralRecharge m_pwMenu = new PopupwindowIntegralRecharge(MemberCenterActivity.this,onTaskSuccessComplete,m_strScore);
                    m_pwMenu.showAtLocation(m_tvName, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                }
                else
                {
                    Utils.showToast(MemberCenterActivity.this,"积分不足");
                }

                break;
            case R.id.ll_details://积分攻略

                break;
        }
    }

    private OnTaskSuccessComplete onTaskSuccessComplete = new OnTaskSuccessComplete()
    {
        @Override
        public void onSuccess(Object obj)
        {
            callHttpCommit((int)obj);
        }
    };

    private void callHttpCommit(int iAmount)
    {
        ApiStores.userScoreReplace(iAmount,new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    try {
                        JSONObject json = new JSONObject(Decrypt.getInstance().decrypt(response.getData()));
                        m_tvIntegral.setText(json.getString("score"));
                        CallHttpUserBalanceUtil.callHttpBalance(MemberCenterActivity.this,waitDialog,null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    FailureDataUtils.showServerReturnErrorMessageEx(MemberCenterActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(MemberCenterActivity.this, "错误", message);
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
}
