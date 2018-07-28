package com.xinfu.attorneyuser.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.CommonHeader;
import com.github.jdsjlzx.view.LoadingFooter;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.https.HttpClient;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.settings.Constant;
import com.xinfu.attorneyuser.utils.dialog.WaitDialog;
import com.xinfu.attorneyuser.view.ErrorLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public abstract class BaseListFragment<T> extends Fragment
{
    protected Unbinder unbinder;
    protected WaitDialog waitDialog;
    /**每一页展示多少条数据*/
    protected int mCurrentPage = 0;
    protected int totalPage = 10;
    protected final int REQUEST_COUNT = 10;
    protected LayoutInflater mInflater;
    private Context mContext;
    protected LRecyclerView mRecyclerView;
    protected ErrorLayout mErrorLayout;
    protected Button toTopBtn;
    protected LinearLayoutManager m_linearLayoutManager;

    protected BaseRecyclerAdapter<T> mListAdapter;
    protected LRecyclerViewAdapter mRecyclerViewAdapter;

    protected boolean isRequestInProcess = false;
    protected boolean mIsStart = false;
    private boolean isHaveEventBus;

    protected CommonHeader headerView;
    private View mContentView;

    private View.OnClickListener mFooterClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, getPageSize(), LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(mContentView != null)
        {
            ViewGroup vgParent = (ViewGroup) mContentView.getParent();
            if (vgParent != null)
            {
                vgParent.removeView(mContentView);
            }
            return mContentView;
        }
        waitDialog = new WaitDialog(getContext());
        HttpClient.init(getContext().getApplicationContext(),false);
        mContext = getContext();
        mContentView = inflater.inflate(setLayoutResourceId(),null);
        unbinder = ButterKnife.bind(this, mContentView);
        this.mInflater = inflater;

        mRecyclerView = mContentView.findViewById(R.id.recycler_view);
        toTopBtn = mContentView.findViewById(R.id.top_btn);
        mErrorLayout = mContentView.findViewById(R.id.error_layout);
        initView();
        initData();
        initViewBase();
        clickView();
        return mContentView;
    }

    protected View getContentView()
    {
        return mContentView;
    }

    protected int setLayoutResourceId()
    {
        return 0;
    }

    public Context getMContext()
    {
        return mContext;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(isHaveEventBus)
        {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void initData(){}

    protected void initView() {}

    protected void clickView() {}

    private void initViewBase() {

        if (mListAdapter != null)
        {
            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        }
        else
        {
            mListAdapter = getListAdapter();

            if (requestDataIfViewCreated())
            {
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                mCurrentPage++;
                isRequestInProcess = true;
                requestData();
            }
            else
            {
                mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            }
        }

        m_linearLayoutManager = new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(m_linearLayoutManager);

        AnimationAdapter adapter = new ScaleInAnimationAdapter(mListAdapter);
        adapter.setFirstOnly(false);
        adapter.setDuration(500);
        adapter.setInterpolator(new OvershootInterpolator(.5f));

        mRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        initLayoutManager();

        mRecyclerView.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                onRefreshView();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {

                if ( REQUEST_COUNT <= totalPage)
                {
                    mCurrentPage++;
                    requestData();
                    isRequestInProcess = true;
                }
                else
                {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener()
        {

            @Override
            public void onScrollUp()
            {
                // 滑动时隐藏float button
                if (toTopBtn.getVisibility() == View.VISIBLE)
                {
                    toTopBtn.setVisibility(View.GONE);
                    animate(toTopBtn, R.anim.floating_action_button_hide);
                }
            }

            @Override
            public void onScrollDown()
            {
                if (toTopBtn.getVisibility() != View.VISIBLE)
                {
                    toTopBtn.setVisibility(View.VISIBLE);
                    animate(toTopBtn, R.anim.floating_action_button_show);
                }
            }

            @Override
            public void onScrolled(int distanceX, int distanceY)
            {

                if (null != headerView)
                {
                    if (distanceY == 0 || distanceY < headerView.getHeight())
                    {
                        toTopBtn.setVisibility(View.GONE);
                    }
                }
                else
                {
                    if (distanceY == 0)
                    {
                        toTopBtn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(int state) {}

        });

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                mCurrentPage = 0;
                mErrorLayout.setErrorType(ErrorLayout.NETWORK_LOADING);
                mCurrentPage++;
                isRequestInProcess = true;
                requestData();
            }
        });

        toTopBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mRecyclerView.scrollToPosition(0);
                toTopBtn.setVisibility(View.GONE);
            }
        });

        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);
    }

    protected boolean requestDataIfViewCreated()
    {
        return true;
    }

    private void animate(View view, int anim)
    {
        if (anim != 0)
        {
            Animation a = AnimationUtils.loadAnimation(view.getContext(), anim);
            view.startAnimation(a);
        }
    }

    /** 设置顶部正在加载的状态 */
    protected void setSwipeRefreshLoadingState() {}

    /**
     * 设置顶部加载完毕的状态
     */
    protected void setSwipeRefreshLoadedState()
    {
        if(null != mRecyclerView)
        {
            mRecyclerView.refreshComplete(REQUEST_COUNT);
        }

    }

    // 完成刷新
    protected void executeOnLoadFinish()
    {
        if(waitDialog.isShowing())
        {
            waitDialog.dismiss();
        }
        setSwipeRefreshLoadedState();
        isRequestInProcess = false;
        mIsStart = false;
    }

    protected abstract BaseRecyclerAdapter<T> getListAdapter();

    protected void requestData() {}

    protected void onRefreshView()
    {
        if (isRequestInProcess)
        {
            return;
        }
        // 设置顶部正在刷新
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mCurrentPage++;
        isRequestInProcess = true;
        requestData();

    }

    protected abstract void initLayoutManager();

    protected int getPageSize()
    {
        return Constant.PAGE_SIZE;
    }

    protected void executeOnLoadDataSuccess(List<T> data, boolean isHavaHead)
    {
        totalPage = data.size();
        if (data == null)
        {
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);

        // 判断等于是因为最后有一项是listview的状态
        if (mListAdapter.getItemCount() == 0)
        {

            if (needShowEmptyNoData())
            {
                mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            }
        }

        if (mCurrentPage == 1)
        {
            mListAdapter.setDataList(data);
            if(mListAdapter.getItemCount() == 0)
            {
                if(isHavaHead)
                {
                    mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
                }
                else
                {
                    mErrorLayout.setErrorType(ErrorLayout.NODATA);
                }

            }
        }
        else
        {
            mListAdapter.addAll(data);
        }
    }

    protected boolean needShowEmptyNoData()
    {
        return true;
    }

    protected void executeOnLoadDataError(String error)
    {
        executeOnLoadFinish();
        if (mCurrentPage == 1)
        {
            mErrorLayout.setErrorType(ErrorLayout.NETWORK_ERROR);
        }
        else
        {

            //在无网络时，滚动到底部时，mCurrentPage先自加了，然而在失败时却
            //没有减回来，如果刻意在无网络的情况下上拉，可以出现漏页问题
            //find by TopJohn
            mCurrentPage--;

            mErrorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
            mListAdapter.notifyDataSetChanged();
        }
    }

    protected void setEventBus()
    {
        isHaveEventBus = true;
        EventBus.getDefault().register(this);
    }

    protected String getNoDataTip()
    {
        return "";
    }



}
