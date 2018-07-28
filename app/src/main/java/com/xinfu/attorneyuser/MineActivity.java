package com.xinfu.attorneyuser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseUserInfoBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.BitmapUtils;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.RegexUtil;
import com.xinfu.attorneyuser.utils.RoundImageUtil;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowSex;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

public class MineActivity extends BaseAppCompatActivity {

    @BindView(R.id.iv_icon)
    ImageView m_ivIcon;
    @BindView(R.id.et_name)
    EditText m_etName;
    @BindView(R.id.tv_sex)
    TextView m_tvSex;
    @BindView(R.id.tv_phone)
    TextView m_tvPhone;
    @BindView(R.id.et_mail)
    TextView m_etMail;

    private String m_strIcon;
    private String m_strName;
    private String m_strSex;
    private String m_strPhone;
    private String m_strMail;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"个人信息",true,"保存");
    }

    @Override
    protected void initData()
    {
        m_strIcon = getIntent().getStringExtra("strIcon");
        m_strName = getIntent().getStringExtra("strName");
        m_strSex = getIntent().getStringExtra("strSex");
        m_strPhone = getIntent().getStringExtra("strPhone");
        m_strMail = getIntent().getStringExtra("strMail");

        RoundImageUtil.setRoundImage(MineActivity.this,m_strIcon,m_ivIcon);
        m_etName.setText(m_strName);
        m_tvSex.setText(m_strSex);
        m_tvPhone.setText(m_strPhone);
        m_etMail.setText(m_strMail);
    }

    @OnClick({R.id.ll_icon,R.id.ll_sex,R.id.tv_title_right})
    public void onViewClick(View view)
    {
        switch (view.getId()){
            case R.id.ll_icon:
                openRadio();
                break;

            case R.id.ll_sex:
                PopupWindowSex m_pwMenu = new PopupWindowSex(this, new OnTaskSuccessComplete() {
                    @Override
                    public void onSuccess(Object obj) {
                        m_strSex = (String) obj;
                        m_tvSex.setText(m_strSex);
                    }
                });
                m_pwMenu.showAtLocation(findViewById(R.id.iv_icon), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_title_right:
                if(isInputValid())
                {
                    callHttpCommit();
                }
                break;
        }
    }

    /**
     * 自定义单选
     */
    private void openRadio() {
        RxGalleryFinal
                .with(this)
                .image()
                .radio()
                .imageLoader(ImageLoaderType.FRESCO)
                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @SuppressLint("NewApi")
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        m_strIcon = imageRadioResultEvent.getResult().getThumbnailSmallPath();
                        File file = new File(m_strIcon);
                        RoundImageUtil.setRoundImage(MineActivity.this,file,m_ivIcon);
                        m_strIcon = BitmapUtils.bitmapsToBase64Array(m_strIcon);
                    }
                })
                .openGallery();
    }

    // 检查输入项是否输入正确
    private boolean isInputValid()
    {
        m_strName = m_etName.getText().toString().trim();
        if(m_strName.isEmpty())
        {
            Utils.showToast(this, "请输入昵称");
            m_etName.requestFocus();
            return false;
        }

        m_strMail = m_etMail.getText().toString().trim();
        if(!m_strMail.isEmpty())
        {
            if(!RegexUtil.checkEmail(m_strMail))
            {
                Utils.showToast(this, "邮箱格式错误");
                m_etMail.requestFocus();
                return false;
            }
        }

        m_strSex = m_tvSex.getText().toString().trim();

        return true;
    }

    private void callHttpCommit()
    {
        ApiStores.userPpdateInfo(m_strIcon,m_strSex,m_strName,m_strMail,new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(Decrypt.getInstance().decrypt(response.getData()));
                        if("1".equals(jsonObject.getString("success")))
                        {
                            Utils.showToast(MineActivity.this,"操作成功");
                            setResult(Activity.RESULT_OK);
                            finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    FailureDataUtils.showServerReturnErrorMessageEx(MineActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(MineActivity.this, "错误", message);
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
