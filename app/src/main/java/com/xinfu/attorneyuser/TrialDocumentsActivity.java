package com.xinfu.attorneyuser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.WXRechargeBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.adapter.PictureSelectionAdapter;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.utils.BitmapUtils;
import com.xinfu.attorneyuser.utils.CallHttpUserBalanceUtil;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.RadiosUtils;
import com.xinfu.attorneyuser.utils.RechargeDataUtils;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowRecharge;
import com.xinfu.attorneyuser.utils.popupwindow.PopupwindowRechargeEdit;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeHandler;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeThread;
import com.xinfu.attorneyuser.wxapi.WXPayUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * 审文书
 * author 付庆明
 */

public class TrialDocumentsActivity extends BaseAppCompatActivity{

    private PictureSelectionAdapter m_pictureSelectionAdapter;

    @BindView(R.id.gridview_functions)
    GridView m_gridView;
    @BindView(R.id.tv_amount)
    TextView m_tvAmount;

    private List<MediaBean> m_arrMediaBean = null;
    private List<MediaBean> m_arrDatas;
    private int m_iSelectPrice = 0;
    private int m_iAmount = 0;
    private int m_iCategory;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_trial_documents;
    }

    @Override
    protected void initView() {
        Utils.initCommonTitle(this,"审文书",true);
    }

    @Override
    protected void initData()
    {
        m_iCategory = getIntent().getIntExtra("iCategory",0);

        m_arrDatas = new ArrayList<>();
        m_arrDatas.add(null);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int nWidth = (dm.widthPixels - Utils.dp2px(this,40))/3;

        m_pictureSelectionAdapter = new PictureSelectionAdapter(this, m_arrDatas, nWidth);
        m_gridView.setAdapter(m_pictureSelectionAdapter);
        m_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        m_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                openRadios();
            }
        });
    }

    @OnClick({R.id.tv_commit})
    public void onViewClick(View v)
    {
        switch (v.getId()) {
            case R.id.tv_commit:
                if(m_iSelectPrice == 0)
                {
                    Utils.showToast(TrialDocumentsActivity.this,"请添加文书");
                }
                else if (m_iSelectPrice > GlobalVariables.getWallect())
                {
                    Utils.showDialog(this, R.layout.center_dialog_layout, obj ->
                    {
                        Intent it = new Intent(this,MyWalletActivity.class);
                        it.putExtra("forResult",MyWalletActivity.RECHARGE_FOR_RESULT);
                        startActivity(it);
                    });
                }  else {
                    callHttpForCommit(BitmapUtils.bitmapsToBase64Array(m_arrMediaBean));
                }
                break;
        }
    }

    private void callHttpForCommit(ArrayList<String> arrImages)
    {

        ApiStores.userInstrument(m_iCategory,"look",arrImages, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    Utils.showToast(TrialDocumentsActivity.this,"操作成功");
                    finish();
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(TrialDocumentsActivity.this, "错误", message);
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

    /**
     * 多选
     */
    private void openRadios()
    {
        RadiosUtils.openRadios(this, m_arrMediaBean, 12, new OnTaskSuccessComplete()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object obj)
            {
                List<MediaBean> list = (List<MediaBean>) obj;
                m_arrMediaBean = list;
                m_arrDatas.clear();

                m_arrDatas.addAll(m_arrMediaBean);
                if(m_arrMediaBean.size() < 12)
                {
                    m_arrDatas.add(null);
                }
                m_pictureSelectionAdapter.notifyDataSetChanged();

                if (list.size() <= 3)
                {
                    m_tvAmount.setText("100律币/1-3张");
                    m_iSelectPrice = 100;
                }
                else if (list.size() <= 6)
                {
                    m_tvAmount.setText("300律币/4-6张");
                    m_iSelectPrice = 300;
                }
                else if (list.size() <= 9){
                    m_tvAmount.setText("700律币/7-9张");
                    m_iSelectPrice = 700;
                }
                else if (list.size() <= 12)
                {
                    m_tvAmount.setText("1000律币/10-12张");
                    m_iSelectPrice = 1000;
                }
            }
        });
    }
}
