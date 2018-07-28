package com.xinfu.attorneyuser;

import android.view.Gravity;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.adapter.HotLineAdapter;
import com.xinfu.attorneyuser.base.BaseListActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseRefereeBean;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowTelephone;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;

/**
 * author 付庆明
 */

public class HotLineActivity extends BaseListActivity
{
    private HotLineAdapter m_hotLineAdapter = new HotLineAdapter();

    private PopupWindowTelephone m_pwMenu;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_common_list;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"求助热线",true);
    }

    @Override
    protected void initLayoutManager()
    {
        mRecyclerView.setLoadMoreEnabled(false);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                m_pwMenu = new PopupWindowTelephone(HotLineActivity.this,m_hotLineAdapter.getListData().get(position).getRemark());
                m_pwMenu.showAtLocation(findViewById(R.id.recycler_view), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter()
    {
        return m_hotLineAdapter;
    }

    @Override
    protected void requestData() {
        ApiStores.helpListHotline(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseRefereeBean responseRefereeBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseRefereeBean.class);
                    executeOnLoadDataSuccess(responseRefereeBean.getData(),false);
                }
                else
                {
                    executeOnLoadDataError(null);
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(HotLineActivity.this,response, Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
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
