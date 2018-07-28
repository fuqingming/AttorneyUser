package com.xinfu.attorneyuser;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.adapter.EnterpriseMsgAdapter;
import com.xinfu.attorneyuser.adapter.EnterpriseMsgHeadAdapter;
import com.xinfu.attorneyuser.base.BaseListActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseEnterpriseMsgBean;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;

/**
 * author 付庆明
 */

public class RefereeActivity extends BaseListActivity{


    private EditText m_etSearch;
    private RecyclerView m_lrvHeadList;

    private EnterpriseMsgHeadAdapter m_enterpriseMsgHeadAdapter = new EnterpriseMsgHeadAdapter();
    private EnterpriseMsgAdapter m_enterpriseMsgAdapter = new EnterpriseMsgAdapter();

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"裁判文书",true);
    }

    @Override
    protected void initLayoutManager() {
        View header = LayoutInflater.from(this).inflate(R.layout.common_head_enterprise_msg,mRecyclerView, false);
        m_etSearch = header.findViewById(R.id.et_search);
        m_lrvHeadList = header.findViewById(R.id.recycler_view);
        mRecyclerViewAdapter.addHeaderView(header);
        mRecyclerView.setLoadMoreEnabled(false);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                intentWebView(m_enterpriseMsgAdapter.getListData().get(position).getLink()+"&channel=ios");
            }
        });

        initHeadRecyclerView();

        //搜索
        header.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String strSearch = m_etSearch.getText().toString().trim();
                if(strSearch.isEmpty())
                {
                    Utils.showToast(RefereeActivity.this,"请输入要搜索的内容！");
                    m_etSearch.requestFocus();
                    return;
                }
//                intentWebView("http://wx.qixin007.com/search/"+ strSearch +".html");
            }
        });
    }

    private void intentWebView(String strUrl)
    {
        Intent it = new Intent(RefereeActivity.this,WebViewActivity.class);
        it.putExtra("webViewUrl",strUrl);
        startActivity(it);
    }

    private void initHeadRecyclerView()
    {
        LinearLayoutManager m_linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        m_lrvHeadList.setLayoutManager(m_linearLayoutManager);

        m_lrvHeadList.setAdapter(m_enterpriseMsgHeadAdapter);
        m_lrvHeadList.setHasFixedSize(true);

        m_enterpriseMsgHeadAdapter.onDoClickListener(new BaseRecyclerAdapter.DoClickListener() {
            @Override
            public void DoClick(Object obj) {
                String strSearch = (String) obj;
//                intentWebView("http://wx.qixin007.com/search/"+ strSearch +".html");
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter() {
        return m_enterpriseMsgAdapter;
    }

    @Override
    protected void requestData() {
        ApiStores.helpListTag("instrument", new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseEnterpriseMsgBean responseEnterpriseMsgBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseEnterpriseMsgBean.class);

                    executeOnLoadDataSuccess(responseEnterpriseMsgBean.getArticles(),true);

                    m_enterpriseMsgHeadAdapter.setDataList(responseEnterpriseMsgBean.getTags());
                }
                else
                {
                    executeOnLoadDataError(null);
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(RefereeActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataError(null);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish()
            {
                executeOnLoadFinish();
            }

        });
    }


}
