package com.xinfu.attorneyuser.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.EnterpriseMsgBean;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import butterknife.BindView;

public class EnterpriseMsgAdapter extends BaseRecyclerAdapter<EnterpriseMsgBean>
{
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_read_count)
    TextView tvReadCount;
    @BindView(R.id.tv_praise_count)
    TextView tvPraiseCount;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    public EnterpriseMsgAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_enterprise_msg_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final EnterpriseMsgBean data, final int position)
    {
        tvText.setText(data.getTitle());
        tvReadCount.setText(data.getReadCount());
        tvPraiseCount.setText(data.getPraiseCount());
        ImageLoader.getInstace().loadImg(mContext, ivPic, data.getFeature(), R.drawable.place_holder);
    }
}
