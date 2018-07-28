package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.xinfu.attorneyuser.adapter.MyOrderAdapter;
import com.xinfu.attorneyuser.base.BaseListActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseMyOrderBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseDataUtils;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.ui.ChatActivity;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;

/**
 * 我的订单
 * @Author 付庆明
 */

public class MyOrderActivity extends BaseListActivity {

    private MyOrderAdapter m_myOrderAdapter = new MyOrderAdapter();

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"我的订单",true);
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter()
    {
        return m_myOrderAdapter;
    }

    @Override
    protected void initLayoutManager()
    {
        mRecyclerView.setLoadMoreEnabled(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.one)
                .setColorResource(R.color.app_backgrount_color)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent it = new Intent(MyOrderActivity.this, ChatActivity.class);
                it.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                it.putExtra(Constant.EXTRA_USER_ID, m_myOrderAdapter.getListData().get(position).getHx_user());
                if(Integer.parseInt(m_myOrderAdapter.getListData().get(position).getEnd_time()) == 0)
                {
                    it.putExtra(Constant.CHATTYPE_NO_CHAT, true);
                }
                it.putExtra(Constant.CHATTYPE_CHAT_TIME, m_myOrderAdapter.getListData().get(position).getEnd_time());
                startActivity(it);
            }
        });
    }

    @Override
    protected void requestData() {
        ApiStores.userOrderList(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseMyOrderBean>();
                    ResponseMyOrderBean data = (ResponseMyOrderBean) responseDataUtils.getListData(response.getData(),ResponseMyOrderBean.class);
                    executeOnLoadDataSuccess(data.getData(),false);
                }
                else
                {
                    executeOnLoadDataError(null);
                    FailureDataUtils.showServerReturnErrorMessageFragment(MyOrderActivity.this,response,Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
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
