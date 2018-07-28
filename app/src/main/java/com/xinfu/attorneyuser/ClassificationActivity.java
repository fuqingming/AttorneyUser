package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseDataUtils;
import com.xinfu.attorneyuser.adapter.ClassificationAdapter;
import com.xinfu.attorneyuser.base.BaseGridViewActivity;
import com.xinfu.attorneyuser.bean.base.PriceBean;
import com.xinfu.attorneyuser.bean.response.ResponseBannerBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseClassificationBean;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;

import java.util.ArrayList;

import butterknife.OnClick;

public class ClassificationActivity extends BaseGridViewActivity {

    private ClassificationAdapter m_classificationAdapter;

    private ArrayList<PriceBean> m_arrSelectsList;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_common_list;
    }

    @Override
    protected void init()
    {
        super.init();
        m_iSpanCount = 4;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"全部分类","取消","确认");
    }

    @Override
    protected void initData()
    {
        m_arrSelectsList = (ArrayList<PriceBean>) getIntent().getSerializableExtra("list");
        m_classificationAdapter = new ClassificationAdapter(m_arrSelectsList);
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter()
    {
        return m_classificationAdapter;
    }

    @Override
    protected void initLayoutManager()
    {
        View header = LayoutInflater.from(this).inflate(R.layout.common_head_classification,mRecyclerView, false);
        mRecyclerViewAdapter.addHeaderView(header);
        mRecyclerView.setLoadMoreEnabled(false);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tvBox = view.findViewById(R.id.tv_select);
                boolean isHas = false;
                for(int i = 0 ; i < m_arrSelectsList.size(); i ++)
                {
                    if(m_arrSelectsList.get(i).getName().trim().equals(tvBox.getText()))
                    {
                        isHas = true;
                        break;
                    }
                }

                if(!isHas)
                {
                    if(m_arrSelectsList.size() >= 4)
                    {
                        Utils.showToast(ClassificationActivity.this, "最多只能选4个");
                        return;
                    }
                    tvBox.setBackgroundResource(R.drawable.shape_text_select2);
                    tvBox.setTextColor(getResources().getColor(R.color.white_color));
                    m_arrSelectsList.add(new PriceBean(tvBox.getTag().toString(), tvBox.getText().toString()));
                }
                else
                {
                    for(int i = 0 ; i < m_arrSelectsList.size() ; i ++)
                    {
                        if(m_arrSelectsList.get(i).getName().equals( tvBox.getText().toString()))
                        {
                            m_arrSelectsList.remove(i);
                            break;
                        }
                    }
                    tvBox.setBackgroundResource(R.drawable.shape_text_select1);
                    tvBox.setTextColor(getResources().getColor(R.color.mainColor));
                }
            }
        });
    }

    @OnClick({R.id.tv_title_back, R.id.tv_title_right})
    public void onViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_title_back:
                finish();
                break;
            case R.id.tv_title_right:
                Intent it = new Intent();
                it.putExtra("list",  m_arrSelectsList);
                setResult(RESULT_OK,it);
                finish();
                break;
        }
    }

    protected void requestData()
    {
        ApiStores.getProfessionList(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseBannerBean>();
                    ResponseClassificationBean responseClassificationBean = (ResponseClassificationBean) responseDataUtils.getListData(response.getData(),ResponseClassificationBean.class);
                    executeOnLoadDataSuccess(responseClassificationBean.getData().get(0).getChild(),true);
                }
            }

            @Override
            public void OnFailure(String message)
            {
                executeOnLoadDataError(null);
            }

            @Override
            public void OnRequestStart()
            {
                if(mCurrentPage > 1)
                {
                    waitDialog.show();
                }
            }

            @Override
            public void OnRequestFinish()
            {
                executeOnLoadFinish();
            }
        });

    }
}
