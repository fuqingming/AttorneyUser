package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.ContractDetailsAdapter;
import com.xinfu.attorneyuser.adapter.FragmentGroupFindLawyerAdapter;
import com.xinfu.attorneyuser.adapter.MyCollectionAdapter;
import com.xinfu.attorneyuser.base.BaseListActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseContractDetailsBean;
import com.xinfu.attorneyuser.bean.response.ResponseFindLawBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseDataUtils;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;

/**
 * author 付庆明
 */

public class MyCollectionActivity extends BaseListActivity {

    private MyCollectionAdapter m_adapterCollection = new MyCollectionAdapter();


    @Override
    protected int setLayoutResourceId()
     {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"收藏中心",true);
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter() {
        return m_adapterCollection;
    }

    @Override
    protected void initLayoutManager()
    {
        mRecyclerView.setLoadMoreEnabled(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.one)
                .setColorResource(R.color.spliter_line_color)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyCollectionActivity.this, LawyerDetailsActivity.class);
                intent.putExtra("law_id", m_adapterCollection.getListData().get(position).getId());
                startActivity(intent);
            }
        });
    }
    
    @Override
    protected void requestData()
    {
        ApiStores.userFavorList( new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseFindLawBean>();
                    ResponseFindLawBean responseClassificationBean = (ResponseFindLawBean) responseDataUtils.getListData(response.getData(),ResponseFindLawBean.class);
                    executeOnLoadDataSuccess(responseClassificationBean.getData(),false);
                }
                else
                {
                    executeOnLoadDataError(null);
                    FailureDataUtils.showServerReturnErrorMessageFragment(MyCollectionActivity.this,response,Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
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
