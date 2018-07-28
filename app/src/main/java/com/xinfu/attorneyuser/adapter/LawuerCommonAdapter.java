package com.xinfu.attorneyuser.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.LawyerCommonBean;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;
import butterknife.BindView;

public class LawuerCommonAdapter extends BaseRecyclerAdapter<LawyerCommonBean>
{
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_time)
    TextView ivTime;
    @BindView(R.id.tv_text)
    TextView tvText;

    public LawuerCommonAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_lawyer_common_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final LawyerCommonBean data, final int position)
    {
        ImageLoader.getInstace().loadCircleImg(mContext, ivIcon, data.getHead());

        ivTime.setText(data.getHowLongAgo());
        tvText.setText(data.getLcon());
    }
}
