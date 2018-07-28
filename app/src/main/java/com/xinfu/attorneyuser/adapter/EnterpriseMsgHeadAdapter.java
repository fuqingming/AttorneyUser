package com.xinfu.attorneyuser.adapter;

import android.view.View;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import butterknife.BindView;

public class EnterpriseMsgHeadAdapter extends BaseRecyclerAdapter<String>
{
    @BindView(R.id.tv_text)
    TextView tvName;

    public EnterpriseMsgHeadAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_enterprise_head_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final String data, final int position)
    {
        tvName.setText(data);

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClickListener.DoClick(data);
            }
        });
    }
}
