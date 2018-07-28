package com.xinfu.attorneyuser;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.VadioAdapter;
import com.xinfu.attorneyuser.base.BaseListFragment;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseVadioBean;
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

public class FragmentVadio extends BaseListFragment
{
    private VadioAdapter m_informationAdapter = new VadioAdapter();

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(getContentView(),"律界说法");
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter() {
        return m_informationAdapter;
    }

    @Override
    protected void initLayoutManager()
    {
        mRecyclerView.setLoadMoreEnabled(false);
        DividerDecoration divider = new DividerDecoration.Builder(getMContext())
                .setHeight(R.dimen.ten)
                .setColorResource(R.color.infoTextColor)
                .build();
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    protected void requestData() {
        ApiStores.fragmentList(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseVadioBean responseEnterpriseMsgBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseVadioBean.class);

                    executeOnLoadDataSuccess(responseEnterpriseMsgBean.getData(),false);
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
