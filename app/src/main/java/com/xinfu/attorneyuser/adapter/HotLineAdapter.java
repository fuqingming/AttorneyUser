package com.xinfu.attorneyuser.adapter;

import android.text.Html;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.RefereeBean;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import butterknife.BindView;

public class HotLineAdapter extends BaseRecyclerAdapter<RefereeBean>
{
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    public HotLineAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_hotline_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final RefereeBean data, final int position)
    {
        tvName.setText(Html.fromHtml(data.getTitle()));
        tvPhone.setText(data.getRemark());
    }
}
