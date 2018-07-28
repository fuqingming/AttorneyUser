package com.xinfu.attorneyuser.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.ContractBean;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import java.text.MessageFormat;

import butterknife.BindView;

public class ContractAdapter extends BaseRecyclerAdapter<ContractBean>
{
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_title)
    TextView ivTitle;
    @BindView(R.id.tv_amount)
    TextView ivAmount;
    @BindView(R.id.rl_price)
    RelativeLayout rlPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public ContractAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_contract_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final ContractBean data, final int position)
    {
        ImageLoader.getInstace().loadImg(mContext, ivPic, data.getMainImage());

        ivTitle.setText(data.getTitle());

        if("0.00".equals(data.getPrice()))
        {
            ivAmount.setText("免费");
            rlPrice.setVisibility(View.VISIBLE);
            tvPrice.setText(data.getMarketPrice());
        }
        else
        {
            ivAmount.setText(MessageFormat.format("{0}元", data.getPrice()));
            rlPrice.setVisibility(View.GONE);
            tvPrice.setText(data.getMarketPrice());
        }
    }
}
