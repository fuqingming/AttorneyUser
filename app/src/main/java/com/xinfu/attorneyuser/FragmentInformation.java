package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.InformationAdapter;
import com.xinfu.attorneyuser.base.BaseListFragment;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseEnterpriseMsgBean;
import com.xinfu.attorneyuser.bean.response.ResponseRefereeBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseResultBean;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentInformation extends BaseListFragment
{
    private InformationAdapter m_informationAdapter = new InformationAdapter();

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(getContentView(),"法律咨询");
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter() {
        return m_informationAdapter;
    }

    @Override
    protected void initLayoutManager() {
        mRecyclerView.setLoadMoreEnabled(false);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                Intent it = new Intent(getMContext(),WebViewActivity.class);
                it.putExtra("webViewUrl",m_informationAdapter.getListData().get(position).getLink()+"&channel=ios");
                startActivity(it);
            }
        });
    }

    @Override
    protected void requestData() {
        ApiStores.helpListTag("news", new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseEnterpriseMsgBean responseEnterpriseMsgBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseEnterpriseMsgBean.class);

                    executeOnLoadDataSuccess(responseEnterpriseMsgBean.getArticles(),false);
                }
                else
                {
                    executeOnLoadDataError(null);
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(getMContext(),response, Const.ForResultData.LOGIN_FRAGMENT_GET_DATA);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Object obj)
    {
        ResponseResultBean responseResultBean = (ResponseResultBean) obj;
        if(responseResultBean.getCode() == Const.ForResultData.LOGIN_FRAGMENT_GET_DATA)
        {
            requestData();
        }
    }
}
