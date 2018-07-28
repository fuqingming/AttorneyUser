package com.xinfu.attorneyuser.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.VipBean;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import butterknife.BindView;

public class VipAdapter extends BaseRecyclerAdapter<VipBean>
{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    public VipAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_vip_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final VipBean data, final int position)
    {
        tvTitle.setText(data.getTitle());
        ImageLoader.getInstace().loadRoundedCornersImg(mContext, ivPic, data.getImg(),10, R.drawable.place_holder);
    }
}
