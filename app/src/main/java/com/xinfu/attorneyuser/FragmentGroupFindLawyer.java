package com.xinfu.attorneyuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.joker.pager.BannerPager;
import com.joker.pager.PagerOptions;
import com.joker.pager.holder.ViewHolder;
import com.joker.pager.holder.ViewHolderCreator;
import com.xinfu.attorneyuser.adapter.CityAdapter;
import com.xinfu.attorneyuser.adapter.DistrictAdapter;
import com.xinfu.attorneyuser.adapter.FragmentGroupFindLawyerAdapter;
import com.xinfu.attorneyuser.adapter.MenuTypeAdapter;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseListFragment;
import com.xinfu.attorneyuser.bean.base.ChildBean;
import com.xinfu.attorneyuser.bean.base.CityBean;
import com.xinfu.attorneyuser.bean.base.LawyerBean;
import com.xinfu.attorneyuser.bean.base.PriceBean;
import com.xinfu.attorneyuser.bean.response.ResponseBannerBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseCityBean;
import com.xinfu.attorneyuser.bean.response.ResponseClassificationBean;
import com.xinfu.attorneyuser.bean.response.ResponseFindLawBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseDataUtils;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.view.menu.FilterMenu;
import com.xinfu.attorneyuser.view.menu.FilterMenuList;
import com.xinfu.attorneyuser.view.menu.FilterMenuTwoList;
import com.xinfu.attorneyuser.view.menu.OnFilterMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 找律师页
 * Created by LoveSnow on 2017/7/10.
 */

public class FragmentGroupFindLawyer extends BaseListFragment {

    private FragmentGroupFindLawyerAdapter m_fragmentGroupFindLawyerAdapter = new FragmentGroupFindLawyerAdapter();

    private BannerPager m_bpBanner;
    private FilterMenu m_filterMenuCity;
    private FilterMenu m_filterMenuType;
    private LinearLayout m_llPop;
    private LinearLayout m_llMove;
    private View m_vHeader;

    private ArrayList<LawyerBean> m_arrBannerBean;
    private ArrayList<CityBean> m_arrCityBeans;
    private List<PriceBean> m_arrPriceBean;

    private int m_iWidthPadding;
    private String m_strAddressID = "";
    private String m_strMenuTypeID = "";

    @BindView(R.id.ll_pop)
    LinearLayout m_llPopTop;
    @BindView(R.id.btn1)
    FilterMenu m_filterMenuCityTop;
    @BindView(R.id.btn2)
    FilterMenu m_filterMenuTypeTop;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_fragment_group_find_lawyer;
    }

    @Override
    protected void initData()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        m_iWidthPadding = (width - Utils.dp2px(getMContext(),190))/2;

        getRecommended();
        requestCityData();
        requestMenuTypeData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            m_arrBannerBean = (ArrayList<LawyerBean>) savedInstanceState.getSerializable("LIST_DATA");
        }
    }

    @Override
    protected void initLayoutManager()
    {
        m_vHeader = LayoutInflater.from(getMContext()).inflate(R.layout.common_head_find_lawyer,mRecyclerView, false);
        m_bpBanner = m_vHeader.findViewById(R.id.banner_pager);
        m_filterMenuCity  = m_vHeader.findViewById(R.id.btn1);
        m_filterMenuType  = m_vHeader.findViewById(R.id.btn2);
        m_llPop = m_vHeader.findViewById(R.id.ll_pop);
        m_llMove = m_vHeader.findViewById(R.id.ll_move);
        mRecyclerViewAdapter.addHeaderView(m_vHeader);
        mRecyclerView.setLoadMoreEnabled(true);
        onScrollChangedListen();
        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getMContext(), LawyerDetailsActivity.class);
                intent.putExtra("law_id", m_fragmentGroupFindLawyerAdapter.getListData().get(position).getId());
                getMContext().startActivity(intent);
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter()
    {
        return m_fragmentGroupFindLawyerAdapter;
    }

    private void initBanner()
    {

        final PagerOptions pagerOptions = new PagerOptions.Builder(getMContext())
                .setTurnDuration(4000)
                .setPageTransformer(new com.joker.pager.transformer.ScaleTransformer())
                .setIndicatorSize(Utils.dp2px(getMContext(),6))
                .setPagePadding(m_iWidthPadding/2)
                .setPrePagerWidth(m_iWidthPadding)
                .setIndicatorVisibility(View.GONE)
                .build();

        m_bpBanner.setPagerOptions(pagerOptions).setPages(m_arrBannerBean, new ViewHolderCreator<BannerPagerHolder>()
        {
            @Override
            public BannerPagerHolder createViewHolder()
            {
                final View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_banner_find_lawyer, null);
                return new BannerPagerHolder(view);
            }
        });

        m_bpBanner.setOnItemClickListener(new com.joker.pager.listener.OnItemClickListener()
        {
            @Override
            public void onItemClick(int location, int position)
            {
                Intent intent = new Intent(getMContext(), LawyerDetailsActivity.class);
                intent.putExtra("law_id", m_arrBannerBean.get(position).getId());
                getMContext().startActivity(intent);
            }
        });
    }

    private class BannerPagerHolder extends ViewHolder<LawyerBean>
    {

        private ImageView image;
        private TextView nameText;
        private TextView tv_nian;
        private ImageView star1;
        private ImageView star2;
        private ImageView star3;

        private BannerPagerHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.image);

            nameText = itemView.findViewById(R.id.tv_name);
            tv_nian = itemView.findViewById(R.id.tv_nian);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
        }

        @Override
        public void onBindView(View view, LawyerBean data, int position)
        {
            Glide.with(image.getContext())
                    .load(data.getHeadimg())
                    .into(image);

            /*holder.ratingBar.setRating(Integer.parseInt(data.getGrade()));*/
            nameText.setText(data.getUsername() + "(" + data.getAddress() + ")");
            if (!TextUtils.isEmpty(data.getWork_year()))
            {
                tv_nian.setText(data.getWork_year());
            }
            else {
                tv_nian.setText("--");
            }

            if (data.getGrade().equals("1"))
            {
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.INVISIBLE);
                star3.setVisibility(View.INVISIBLE);
            }
            else if (data.getGrade().equals("2"))
            {
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.INVISIBLE);
            }
            else if (data.getGrade().equals("3"))
            {
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.VISIBLE);
            }
            else
            {
                star1.setVisibility(View.INVISIBLE);
                star2.setVisibility(View.INVISIBLE);
                star3.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void requestData()
    {
        requestCityData();
        getRecommended();
        requestMenuTypeData();
        ApiStores.getLawList(m_strAddressID,m_strMenuTypeID,mCurrentPage, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseFindLawBean>();
                    ResponseFindLawBean responseClassificationBean = (ResponseFindLawBean) responseDataUtils.getListData(response.getData(),ResponseFindLawBean.class);
                    executeOnLoadDataSuccess(responseClassificationBean.getData(),true);
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

    private void getRecommended()
    {
        if(m_arrBannerBean != null && m_arrBannerBean.size() > 0 )
        {
            initBanner();
            return;
        }
        ApiStores.getRecommended(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseFindLawBean>();
                    ResponseFindLawBean responseClassificationBean = (ResponseFindLawBean) responseDataUtils.getListData(response.getData(),ResponseFindLawBean.class);
                    m_arrBannerBean = responseClassificationBean.getData();
                    initBanner();
                }
                else
                {
                    executeOnLoadDataError(null);
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(getMContext(),response,Const.ForResultData.LOGIN_FRAGMENT_GET_DATA);
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

    protected void requestCityData()
    {
        if(m_arrCityBeans != null && m_arrCityBeans.size() > 0)
        {
            initMenuCity();
            return;
        }
        ApiStores.getCity(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseCityBean>();
                    ResponseCityBean responseCityBean = (ResponseCityBean) responseDataUtils.getListData(response.getData(),ResponseCityBean.class);
                    m_arrCityBeans = responseCityBean.getData();
                    m_arrCityBeans.add(0,new CityBean("全部"));

                    initMenuCity();
                }
                else
                {
                    if(waitDialog.isShowing())
                    {
                        waitDialog.dismiss();
                    }
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(getMContext(),response,Const.ForResultData.LOGIN_FRAGMENT_GET_DATA);
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

    protected void requestMenuTypeData()
    {
        if(m_arrPriceBean != null && m_arrPriceBean.size() > 0)
        {
            initMenuType();
            return;
        }
        ApiStores.getProfessionList(new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseDataUtils responseDataUtils = new ResponseDataUtils<ResponseBannerBean>();
                    ResponseClassificationBean responseClassificationBean = (ResponseClassificationBean) responseDataUtils.getListData(response.getData(),ResponseClassificationBean.class);
                    m_arrPriceBean = responseClassificationBean.getData().get(0).getChild();
                    m_arrPriceBean.add(0,new PriceBean("全部"));
                    initMenuType();
                }
                else
                {
                    if(waitDialog.isShowing())
                    {
                        waitDialog.dismiss();
                    }
                    setEventBus();
                    FailureDataUtils.showServerReturnErrorMessageFragment(getMContext(),response,Const.ForResultData.LOGIN_FRAGMENT_GET_DATA);
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

    private void initMenuCity()
    {
        final FilterMenuTwoList filterMenuMultiList = new FilterMenuTwoList(getActivity());
        final CityAdapter cityAdapter = new CityAdapter(getActivity(),m_arrCityBeans, R.drawable.normal, R.drawable.press);
        final DistrictAdapter districtAdapter = new DistrictAdapter(getActivity(),m_arrCityBeans.get(0).getChild(), R.drawable.normal,R.drawable.press);

        filterMenuMultiList.setParentAdapter(cityAdapter);
        filterMenuMultiList.setChildrenAdapter(districtAdapter);

        filterMenuMultiList.setOnParentFilterMenuItemClickListener(new OnFilterMenuItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                cityAdapter.setPressPostion(position);
                cityAdapter.notifyDataSetChanged();
                CityBean city = (CityBean) parent.getItemAtPosition(position);
                districtAdapter.setDataNotify(city.getChild());
                districtAdapter.setPressPostion(-1);
                filterMenuMultiList.setParentSelection(0);
                if("全部".equals(city.getShortname()))
                {
                    m_strAddressID = "";
                    if(!m_filterMenuCity.getText().equals("区域"))
                    {
                        onRefreshView();
                    }
                    m_filterMenuCity.setText("区域");
                    m_filterMenuCity.hidePopup();
                    m_filterMenuCityTop.setText("区域");
                    m_filterMenuCityTop.hidePopup();
                }
            }
        });

        filterMenuMultiList.setOnChildrenFilterMenuItemClickListener(new OnFilterMenuItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                districtAdapter.setPressPostion(position);
                districtAdapter.notifyDataSetChanged();
                ChildBean district = (ChildBean) parent.getItemAtPosition(position);
                if(!m_filterMenuCity.getText().equals(district.getShortname()))
                {
                    m_strAddressID = district.getId();
                    onRefreshView();
                }
                m_filterMenuCity.setText(district.getShortname());
                m_filterMenuCity.hidePopup();
                m_filterMenuCityTop.setText(district.getShortname());
                m_filterMenuCityTop.hidePopup();
                m_strAddressID = district.getId();
            }
        });

        m_filterMenuCity.setPopupView(new OnTaskSuccessComplete()
        {
            @Override
            public void onSuccess(Object obj) {
                MoveToPosition();
                m_filterMenuCity.showPopUpWindow(filterMenuMultiList,m_llPop);
            }
        });

        final FilterMenuTwoList filterMenuMultiList1 = new FilterMenuTwoList(getActivity());
        final CityAdapter cityAdapter1 = new CityAdapter(getActivity(),m_arrCityBeans, R.drawable.normal, R.drawable.press);
        final DistrictAdapter districtAdapter1 = new DistrictAdapter(getActivity(),m_arrCityBeans.get(0).getChild(), R.drawable.normal,R.drawable.press);

        filterMenuMultiList1.setParentAdapter(cityAdapter1);
        filterMenuMultiList1.setChildrenAdapter(districtAdapter1);

        filterMenuMultiList1.setOnParentFilterMenuItemClickListener(new OnFilterMenuItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                cityAdapter1.setPressPostion(position);
                cityAdapter1.notifyDataSetChanged();
                CityBean city = (CityBean) parent.getItemAtPosition(position);
                districtAdapter1.setDataNotify(city.getChild());
                districtAdapter1.setPressPostion(-1);
                filterMenuMultiList1.setParentSelection(0);
                if("全部".equals(city.getShortname()))
                {
                    m_strAddressID = "";
                    if(!m_filterMenuCity.getText().equals("区域"))
                    {
                        onRefreshView();
                    }
                    m_filterMenuCity.setText("区域");
                    m_filterMenuCity.hidePopup();
                    m_filterMenuCityTop.setText("区域");
                    m_filterMenuCityTop.hidePopup();
                }
            }
        });

        filterMenuMultiList1.setOnChildrenFilterMenuItemClickListener(new OnFilterMenuItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                districtAdapter1.setPressPostion(position);
                districtAdapter1.notifyDataSetChanged();
                ChildBean district = (ChildBean) parent.getItemAtPosition(position);
                if(!m_filterMenuCity.getText().equals(district.getShortname()))
                {
                    m_strAddressID = district.getId();
                    onRefreshView();
                }
                m_filterMenuCity.setText(district.getShortname());
                m_filterMenuCity.hidePopup();
                m_filterMenuCityTop.setText(district.getShortname());
                m_filterMenuCityTop.hidePopup();
                m_strAddressID = district.getId();
            }
        });

        m_filterMenuCityTop.setPopupView(new OnTaskSuccessComplete()
        {
            @Override
            public void onSuccess(Object obj) {
                m_filterMenuCityTop.showPopUpWindow(filterMenuMultiList1,m_llPopTop);
            }
        });

    }

    private void initMenuType()
    {

        FilterMenuList menuList = new FilterMenuList(getActivity());
        final MenuTypeAdapter adapter = new MenuTypeAdapter(getActivity(),m_arrPriceBean, R.drawable.normal, R.drawable.press);
        menuList.setAdapter(adapter);
        menuList.setOnFilterMenuItemClickListener(new OnFilterMenuItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.setPressPostion(position);
                adapter.notifyDataSetChanged();
                PriceBean jobType = (PriceBean) parent.getItemAtPosition(position);

                if("全部".equals(jobType.getName()))
                {
                    m_filterMenuType.setText("案件类型");
                    m_filterMenuTypeTop.setText("案件类型");
                    m_strMenuTypeID = "";
                }
                else
                {
                    m_strMenuTypeID = jobType.getNum();
                    m_filterMenuType.setText(jobType.getName());
                    m_filterMenuTypeTop.setText(jobType.getName());
                }
                m_filterMenuType.hidePopup();
                onRefreshView();
            }
        });

        m_filterMenuType.setPopupView(new OnTaskSuccessComplete()
        {
            @Override
            public void onSuccess(Object obj) {
                MoveToPosition();
                m_filterMenuType.showPopUpWindow(menuList,m_llPop);
            }
        });

        FilterMenuList menuList1 = new FilterMenuList(getActivity());
        final MenuTypeAdapter adapter1 = new MenuTypeAdapter(getActivity(),m_arrPriceBean, R.drawable.normal, R.drawable.press);
        menuList1.setAdapter(adapter1);
        menuList1.setOnFilterMenuItemClickListener(new OnFilterMenuItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter1.setPressPostion(position);
                adapter1.notifyDataSetChanged();
                PriceBean jobType = (PriceBean) parent.getItemAtPosition(position);

                if("全部".equals(jobType.getName()))
                {
                    m_filterMenuType.setText("案件类型");
                    m_filterMenuTypeTop.setText("案件类型");
                    m_strMenuTypeID = "";
                }
                else
                {
                    m_strMenuTypeID = jobType.getNum();
                    m_filterMenuType.setText(jobType.getName());
                    m_filterMenuTypeTop.setText(jobType.getName());
                }
                m_filterMenuTypeTop.hidePopup();
                onRefreshView();
            }
        });

        m_filterMenuTypeTop.setPopupView(new OnTaskSuccessComplete()
        {
            @Override
            public void onSuccess(Object obj) {
                m_filterMenuTypeTop.showPopUpWindow(menuList1,m_llPopTop);
            }
        });
    }

    private void MoveToPosition()
    {
        View itemView = m_linearLayoutManager.findViewByPosition(1);
        if (itemView != null)
        {
            if (-itemView.getTop() < m_vHeader.getHeight() - m_llPopTop.getHeight())
            {
                mRecyclerView.scrollBy(0,m_llMove.getHeight()+itemView.getTop()-m_llPop.getHeight());
            }
        }
    }

    private void onScrollChangedListen(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View itemView = m_linearLayoutManager.findViewByPosition(1);
                if (itemView != null) {
                    int a = -itemView.getTop();
                    int b = m_llMove.getHeight();
                    int c = m_llPop.getHeight();
                    Log.d("--------->",a+"@"+b+"@"+c+"");
                    if (-itemView.getTop() <= m_llMove.getHeight()-m_llPop.getHeight()) {
                        m_llPopTop.setVisibility(View.GONE);
                    } else {

                        m_llPopTop.setVisibility(View.VISIBLE);
                    }
                }else{
                    m_llPopTop.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LIST_DATA", m_arrBannerBean);
    }
}
