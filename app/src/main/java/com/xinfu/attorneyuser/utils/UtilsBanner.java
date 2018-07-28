package com.xinfu.attorneyuser.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.joker.pager.BannerPager;
import com.joker.pager.PagerOptions;
import com.joker.pager.holder.ViewHolder;
import com.joker.pager.holder.ViewHolderCreator;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.bean.base.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class UtilsBanner
{
    
    public static void banner(Context content, BannerPager m_bpBanner, List<BannerBean> data, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        ArrayList<String> m_arrBanner = new ArrayList<>();
        for(int i = 0 ; i < data.size() ; i ++)
        {
            m_arrBanner.add(data.get(i).getFile());
        }
        final PagerOptions pagerOptions = new PagerOptions.Builder(content)
                .setTurnDuration(2000)
                .setIndicatorSize(Utils.dp2px(content,6))
                .setIndicatorColor(content.getResources().getColor(R.color.dark),content.getResources().getColor(R.color.red) )
                .setIndicatorAlign(RelativeLayout.ALIGN_PARENT_RIGHT)
                .setIndicatorMarginBottom(15)
                .build();

        m_bpBanner.setPagerOptions(pagerOptions).setPages(m_arrBanner, new ViewHolderCreator<BannerPagerHolder>() {
            @Override
            public BannerPagerHolder createViewHolder() {
                final View view = LayoutInflater.from(content).inflate(R.layout.item_image_banner_bottom, null);
                return new BannerPagerHolder(view);
            }
        });
        m_bpBanner.startTurning();
        m_bpBanner.setOnItemClickListener(new com.joker.pager.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int location, int position) {
                onTaskSuccessComplete.onSuccess(position);
            }
        });
    }

    public static void bannerFile(Context content, BannerPager m_bpBanner, OnTaskSuccessComplete onTaskSuccessComplete)
    {
        ArrayList<Integer> m_arrBanner = new ArrayList<>();
        m_arrBanner.add(R.mipmap.contact_download);
        m_arrBanner.add(R.mipmap.lawyer_connect);

        final PagerOptions pagerOptions = new PagerOptions.Builder(content)
                .setTurnDuration(2000)
                .setIndicatorSize(Utils.dp2px(content,6))
                .setIndicatorColor(content.getResources().getColor(R.color.dark),content.getResources().getColor(R.color.red) )
                .setIndicatorAlign(RelativeLayout.ALIGN_PARENT_RIGHT)
                .setIndicatorMarginBottom(15)
                .build();

        m_bpBanner.setPagerOptions(pagerOptions).setPages(m_arrBanner, new ViewHolderCreator<BannerPagerFileHolder>() {
            @Override
            public BannerPagerFileHolder createViewHolder() {
                final View view = LayoutInflater.from(content).inflate(R.layout.item_image_banner_bottom, null);
                return new BannerPagerFileHolder(view);
            }
        });
        m_bpBanner.startTurning();
        m_bpBanner.setOnItemClickListener(new com.joker.pager.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int location, int position) {
                onTaskSuccessComplete.onSuccess(position);
            }
        });
    }

    private static class BannerPagerHolder extends ViewHolder<String> {

        private ImageView mImage;

        private BannerPagerHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
        }

        @Override
        public void onBindView(View view, String data, int position) {
            Glide.with(mImage.getContext())
                    .load(data)
                    .into(mImage);
        }
    }

    private static class BannerPagerFileHolder extends ViewHolder<Integer> {

        private ImageView mImage;

        private BannerPagerFileHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
        }

        @Override
        public void onBindView(View view, Integer data, int position) {
            mImage.setImageResource(data);
        }
    }
    
}
