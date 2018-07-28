package com.xinfu.attorneyuser.adapter;

import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.ContractBean;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import butterknife.BindView;

public class ContractDetailsAdapter extends BaseRecyclerAdapter<ContractBean>
{
    @BindView(R.id.tv_title)
    TextView ivTitle;

    public ContractDetailsAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_contract_details_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final ContractBean data, final int position)
    {
        ivTitle.setText(data.getTitle());
    }
}
