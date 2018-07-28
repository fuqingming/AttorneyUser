package com.xinfu.attorneyuser;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xinfu.attorneyuser.adapter.MyFrPagerAdapter;
import com.xinfu.attorneyuser.adapter.PopWindow_RecyclerView_Adapter;
import com.xinfu.attorneyuser.base.BaseFragment;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.ui.ChatActivity;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.view.PagerSlidingTabStrip;
import java.text.MessageFormat;
import butterknife.BindView;
import butterknife.OnClick;

public class FragmentGroup extends BaseFragment
{
    public LocationClient mLocationClient = null;
    public MyLocationListenner myListener = new MyLocationListenner();

    @BindView(R.id.tabs)
    PagerSlidingTabStrip m_pagerSlidingTabStrip;
    @BindView(R.id.pager)
    ViewPager m_viewPager;
    @BindView(R.id.tv_title)
    TextView m_tvTitle;
    @BindView(R.id.popup_layout)
    RelativeLayout m_rlPop;
    @BindView(R.id.popup_recycler)
    RecyclerView m_rlPupop;

    private String m_textArray[] = {"问律师","找律师","审文书","写文书","资产托管","知识产权","调查取证","上市服务","保险服务","专家证人" };

    private Integer[] m_imgArray = new Integer[]{  R.mipmap.wenlvshi, R.mipmap.zhaolvshi, R.mipmap.shenwenshu,
                                                R.mipmap.xiewenshu,R.mipmap.zichantuoguan, R.mipmap.zhishichanquan,
                                                R.mipmap.diaochaquzheng, R.mipmap.shangshifuwu,R.mipmap.baoxianfuwu,
                                                R.mipmap.zhuanjiazhengren};

    // 各tab页对应的fragment
    private Fragment m_fragmentArray[] = {  new FragmentGroupAsk(),
                                            new FragmentGroupFindLawyer(),
                                            new FragmentGroupTrialDocuments(),
                                            new FragmentGroupWriteDocuments(),
                                            new FragmentGroupAssetEscrow(),
                                            new FragmentGroupIntellectual(),
                                            new FragmentGroupInvestigation(),
                                            new FragmentGroupListed(),
                                            new FragmentGroupInsurance(),
                                            new FragmentGroupExperts()};

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.fragment_group;
    }

    @Override
    protected void initView()
    {
        m_viewPager.setAdapter(new MyFrPagerAdapter(getChildFragmentManager(), m_textArray, m_fragmentArray));
        m_pagerSlidingTabStrip.setViewPager(m_viewPager);
        m_viewPager.setCurrentItem(0);

        PopWindow_RecyclerView_Adapter mAdapter = new PopWindow_RecyclerView_Adapter(getMContext(), m_imgArray, m_textArray);
        GridLayoutManager mPoplayoutManager = new GridLayoutManager(getMContext(), 3);
        m_rlPupop.setLayoutManager(mPoplayoutManager);
        m_rlPupop.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) ->
        {
            m_viewPager.setCurrentItem(position);
            m_rlPop.setVisibility(View.GONE);
        });

        mLocationClient = new LocationClient(getMContext());
        mLocationClient.registerLocationListener(myListener);
        setLocationOption();
        mLocationClient.start();
    }

    @OnClick({R.id.img_popup,R.id.iv_left})
    public void onViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_popup://显示pop
                if (m_rlPop.getVisibility() == View.VISIBLE)
                {
                    m_rlPop.setVisibility(View.GONE);
                }
                else if (m_rlPop.getVisibility() == View.GONE)
                {
                    m_rlPop.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_left:
                Intent it = new Intent(getMContext(),PersonalCenterActivity.class);
                startActivity(it);
                break;
        }
    }

    public class MyLocationListenner implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            m_tvTitle.setText(MessageFormat.format("律界通·{0}{1}", location.getCity(), location.getDistrict()));
        }
    }

    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //打开gps
        option.setServiceName("com.baidu.location.service_v2.9");
        option.setAddrType("all");
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setPriority(LocationClientOption.GpsFirst);       //gps
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
