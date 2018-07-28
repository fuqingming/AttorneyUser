package com.xinfu.attorneyuser;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xinfu.attorneyuser.adapter.PictureSelectionAdapter;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.utils.BitmapUtils;
import com.xinfu.attorneyuser.utils.RadiosUtils;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.view.switchbutton.FSwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * 问律师页
 * author 付庆明
 */

public class WriteDocumentsActivity extends BaseAppCompatActivity {

    private PictureSelectionAdapter m_pictureSelectionAdapter;

    @BindView(R.id.gridview_functions)
    GridView m_gridView;
    @BindView(R.id.sb_switch)
    FSwitchButton m_sbSwitch;

    private List<MediaBean> m_arrMediaBean = null;
    private List<MediaBean> m_arrDatas;
    private int m_iCategory;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_write_documents;
    }

    @Override
    protected void initView() {
        Utils.initCommonTitle(this,"写文书",true);
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

        m_sbSwitch.setChecked(false,false,false);
        m_sbSwitch.setOnCheckedChangedCallback(new FSwitchButton.OnCheckedChangedCallback()
        {
            @Override
            public void onCheckedChanged(boolean checked, FSwitchButton view)
            {

            }
        });
    }

    @OnClick({R.id.tv_commit})
    public void onViewClick(View v)
    {
        switch (v.getId()) {
            case R.id.tv_commit:
                callHttpForCommit(BitmapUtils.bitmapsToBase64Array(m_arrMediaBean));
                break;
        }
    }

    private void callHttpForCommit(ArrayList<String> arrImages)
    {

        ApiStores.userInstrument(m_iCategory,"write",arrImages, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    Utils.showToast(WriteDocumentsActivity.this,"操作成功");
                    finish();
                }
            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(WriteDocumentsActivity.this, "错误", message);
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
            }
        });
    }
}
