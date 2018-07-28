package com.xinfu.attorneyuser.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.CommonHeader;
import com.github.jdsjlzx.view.LoadingFooter;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.BackHandlerHelper;
import com.xinfu.attorneyuser.https.HttpClient;
import com.xinfu.attorneyuser.utils.popupwindow.CommonFilterPop;
import com.xinfu.attorneyuser.settings.Constant;
import com.xinfu.attorneyuser.settings.GlobalInstanceStateHelper;
import com.xinfu.attorneyuser.utils.dialog.WaitDialog;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.view.ErrorLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public abstract class BasePopListActivity<T> extends AppCompatActivity
{
    protected WaitDialog waitDialog;
    /**每一页展示多少条数据*/
    protected int mCurrentPage = 0;
    protected int totalPage = 10;
    protected final int REQUEST_COUNT = 10;
    protected LRecyclerView mRecyclerView;
    protected ErrorLayout mErrorLayout;
    protected Button toTopBtn;

    protected BaseRecyclerAdapter<T> mListAdapter;
    protected LRecyclerViewAdapter mRecyclerViewAdapter;

    protected boolean isRequestInProcess = false;
    protected boolean mIsStart = false;
    private boolean isHaveEventBus;

    protected CommonHeader headerView;

    private View.OnClickListener mFooterClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            RecyclerViewStateUtils.setFooterViewState(BasePopListActivity.this, mRecyclerView, getPageSize(), LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
        waitDialog = new WaitDialog(this);
        setContentView(setLayoutResourceId());
        ButterKnife.bind(this);
        mRecyclerView = (LRecyclerView) findViewById(R.id.recycler_view);
        toTopBtn = (Button) findViewById(R.id.top_btn);
        mErrorLayout = (ErrorLayout) findViewById(R.id.error_layout);
        initView();
        initData();
        initViewBase();
        clickView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    protected void init()
    {
        HttpClient.init(getApplicationContext(),true);
    }

    protected int setLayoutResourceId()
    {
        return 0;
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

    private void initViewBase()
    {

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

        LinearLayoutManager m_linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
        if (data == null)
        {
            data = new ArrayList<T>();
        }
        totalPage = data.size();

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

    protected String getNoDataTip()
    {
        return "";
    }

    @Override
    public void onBackPressed()
    {
        if (!BackHandlerHelper.handleBackPress(this))
        {
            super.onBackPressed();
        }
    }

    @Override
    public Resources getResources()
    {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    /**
     * 筛选pop
     */
    private CommonFilterPop mPopupWindow;

    /**
     * 列表选择popupWindow
     *
     * @param parentView        父View
     * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView,
                                      AdapterView.OnItemClickListener itemClickListener, BaseAdapter mPopAdapter,
                                      CustomerDismissListener dismissListener)
    {
        showFilterPopupWindow(parentView, itemClickListener,mPopAdapter, dismissListener, 0);
    }

    /**
     * 列表选择popupWindow
     *
     * @param parentView        父View
     * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView,
                                      AdapterView.OnItemClickListener itemClickListener, BaseAdapter mPopAdapter,
                                      CustomerDismissListener dismissListener, float alpha)
    {

        // 判断当前是否显示
        if (mPopupWindow != null && mPopupWindow.isShowing())
        {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        mPopupWindow = new CommonFilterPop(this, mPopAdapter);
        mPopupWindow.setOnDismissListener(dismissListener);
        // 绑定筛选点击事件
        mPopupWindow.setOnItemSelectedListener(itemClickListener);
        // 如果透明度设置为0的话,则默认设置为0.6f
        if (0 == alpha)
        {
            alpha = 0.6f;
        }
        // 设置背景透明度
//        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//        lp.alpha = alpha;
//        getActivity().getWindow().setAttributes(lp);
        // 显示pop
        mPopupWindow.showAsDropDown(parentView);
    }

    /**
     * Tab筛选栏切换
     *  @param isChecked         选中状态
     * @param showView          展示pop的跟布局
     * @param itemClickListener 点击回调
     * @param tabs              所有的cb(需要几个输入几个就可以,cb1,cb2....)
     */
    public void filterTabToggle(boolean isChecked, View showView, BaseAdapter mPopAdapter, AdapterView.OnItemClickListener itemClickListener, final CheckBox... tabs)
    {
        if (isChecked)
        {
            if (tabs.length <= 0)
            {
                return;
            }
            // 第一个checkBox为当前点击选中的cb,其他cb进行setChecked(false);
            for (int i = 1; i < tabs.length; i++)
            {
                tabs[i].setChecked(false);
            }

            showFilterPopupWindow(showView, itemClickListener, mPopAdapter,new CustomerDismissListener()
            {
                @Override
                public void onDismiss()
                {
                    super.onDismiss();
                    // 当pop消失时对第一个cb进行.setChecked(false)操作
                    tabs[0].setChecked(false);
                }
            });
        }
        else
        {
            // 关闭checkBox时直接隐藏popuwindow
            hidePopListView();
        }
    }

    /**
     * 自定义OnDismissListener
     */
    public class CustomerDismissListener implements PopupWindow.OnDismissListener
    {
        @Override
        public void onDismiss()
        {
            // 当pop消失的时候,重置背景色透明度
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 1.0f;
            getWindow().setAttributes(lp);
        }
    }

    /**
     * 隐藏pop
     */
    public void hidePopListView()
    {
        // 判断当前是否显示,如果显示则dismiss
        if (mPopupWindow != null && mPopupWindow.isShowing())
        {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    protected void setEventBus()
    {
        isHaveEventBus = true;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // 保存MyApplication中保存的全局变量
        GlobalInstanceStateHelper.saveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        GlobalInstanceStateHelper.restoreInstanceState(this, savedInstanceState);
    }
}
