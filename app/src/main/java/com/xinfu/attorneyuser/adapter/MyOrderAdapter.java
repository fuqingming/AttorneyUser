package com.xinfu.attorneyuser.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.MyOrderBean;
import com.xinfu.attorneyuser.utils.RoundImageUtil;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;
import butterknife.BindView;

public class MyOrderAdapter extends BaseRecyclerAdapter<MyOrderBean>
{
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView ivName;
    @BindView(R.id.tv_text)
    TextView ivText;
    @BindView(R.id.tv_time)
    TextView ivTime;
    @BindView(R.id.tv_type)
    TextView ivType;

    public MyOrderAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_my_order_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final MyOrderBean data, final int position)
    {
        RoundImageUtil.setRoundImage(mContext,data.getHead(),ivIcon);
        ivName.setText(data.getLawyername());
        ivText.setText(data.getName());
        ivTime.setText(data.getCreatedAt());
        ivType.setText(data.getTag());
    }
}
