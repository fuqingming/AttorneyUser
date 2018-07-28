package com.xinfu.attorneyuser.adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.WebViewActivity;
import com.xinfu.attorneyuser.bean.base.VadioBean;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;
import com.xinfu.attorneyuser.utils.recycler.GridSpacingItemDecoration;

import butterknife.BindView;

public class VadioAdapter extends BaseRecyclerAdapter<VadioBean>
{
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public VadioAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_vadio_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final VadioBean data, final int position)
    {
        tvText.setText(data.getLabel());

        GridLayoutManager mgr = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(mgr);
        int length = Utils.dp2px(mContext,10);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(length, length, true));
        VadioGridViewAdapter vadioGridViewAdapter= new VadioGridViewAdapter();
        recyclerView.setAdapter(vadioGridViewAdapter);
        vadioGridViewAdapter.setDataList(data.getEntitys());

        vadioGridViewAdapter.onDoClickListener(new DoClickListener()
        {
            @Override
            public void DoClick(Object obj)
            {
                Intent it = new Intent(mContext,WebViewActivity.class);
                it.putExtra("webViewUrl",obj.toString());
                mContext.startActivity(it);
            }
        });
    }
}
