package com.xinfu.attorneyuser.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.EntitysBean;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import butterknife.BindView;

public class VadioGridViewAdapter extends BaseRecyclerAdapter<EntitysBean>
{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.ll_item)
    LinearLayout llItem;

    public VadioGridViewAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_vadio_gridview_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final EntitysBean data, final int position)
    {
        ImageLoader.getInstace().loadImg(mContext, ivPic, data.getImage(), R.drawable.place_holder);
        tvTitle.setText(data.getTitle());

        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClickListener.DoClick(data.getLink());
            }
        });
    }
}
