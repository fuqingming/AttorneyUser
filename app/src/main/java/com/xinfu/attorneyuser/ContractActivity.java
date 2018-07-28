package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.ContractTypeAdapter;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.ContractTypeBean;
import com.xinfu.attorneyuser.bean.response.ResponseClassificationBean;
import com.xinfu.attorneyuser.bean.response.ResponseContractTypeBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.adapter.ContractAdapter;
import com.xinfu.attorneyuser.base.BaseGridViewActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseContractBean;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseDataUtils;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.view.menu.FilterMenu;
import com.xinfu.attorneyuser.view.menu.FilterMenuList;
import com.xinfu.attorneyuser.view.menu.OnFilterMenuItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * author 付庆明
 */

public class ContractActivity extends BaseGridViewActivity {

    private ContractAdapter m_contractAdapter = new ContractAdapter();

    private FilterMenu m_filterBtn1;
    private FilterMenu m_filterBtn2;
    private FilterMenu m_filterBtn3;

    private LinearLayout m_llPop;

    private String m_strMenuTypeID = "";

    @Override
    protected int setLayoutResourceId()
     {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"合同文书",true);
    }

    @Override
    protected void initData()
    {
        requestMenuTypeData();
    }

    @Override
    protected void initLayoutManager()
    {
        View header = LayoutInflater.from(this).inflate(R.layout.common_head_contract,mRecyclerView, false);
        m_filterBtn1  = header.findViewById(R.id.btn1);
        m_filterBtn2  = header.findViewById(R.id.btn2);
        m_filterBtn3  = header.findViewById(R.id.btn3);
        m_llPop = header.findViewById(R.id.ll_pop);
        mRecyclerViewAdapter.addHeaderView(header);

        mRecyclerView.setLoadMoreEnabled(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.ten)
                .setColorResource(R.color.app_backgrount_color)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent it = new Intent(ContractActivity.this,ContractDetailsActivity.class);
                it.putExtra("uuid",m_contractAdapter.getListData().get(position).getId());
                startActivity(it);
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter()
    {
        return m_contractAdapter;
    }

    private void initMenuType(List<ContractTypeBean> data)
    {

        FilterMenuList menuList = new FilterMenuList(this);
        final ContractTypeAdapter adapter = new ContractTypeAdapter(this,data, R.drawable.normal, R.drawable.press);
        menuList.setAdapter(adapter);
        menuList.setOnFilterMenuItemClickListener(new OnFilterMenuItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                adapter.setPressPostion(position);
                adapter.notifyDataSetChanged();
                ContractTypeBean jobType = (ContractTypeBean) parent.getItemAtPosition(position);

                if("全部".equals(jobType.getTitle()))
                {
                    m_filterBtn1.setText("全部");
                    m_strMenuTypeID = "";
                }
                else
                {
                    m_strMenuTypeID = jobType.getId();
                    m_filterBtn1.setText(jobType.getTitle());
                }
                m_filterBtn1.hidePopup();
                onRefreshView();
            }
        });
        m_filterBtn1.setPopupView(new OnTaskSuccessComplete() {
            @Override
            public void onSuccess(Object obj) {
                m_filterBtn1.showPopUpWindow(menuList, m_llPop);
            }
        });

    }

    private void requestMenuTypeData()
    {
        ApiStores.userBatchCat(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseContractTypeBean responseVipBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseContractTypeBean.class);
                    List<ContractTypeBean> cityBeans = responseVipBean.getCats();
                    cityBeans.add(0,new ContractTypeBean("全部"));
                    initMenuType(cityBeans);
                }
                else
                {
                    if(waitDialog.isShowing())
                    {
                        waitDialog.dismiss();
                    }
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(ContractActivity.this,response,Const.ForResultData.LOGIN_ACTIVITY_CALL_HTTP);
                }

            }

            @Override
            public void OnFailure(final String message){}

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }

    @Override
    protected void requestData()
    {
        ApiStores.userBatchLst(m_strMenuTypeID, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseContractBean responseVipBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseContractBean.class);

                    executeOnLoadDataSuccess(responseVipBean.getLists(),true);
                }
                else
                {
                    executeOnLoadDataError(null);
                    FailureDataUtils.showServerReturnErrorMessageFragment(ContractActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
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
