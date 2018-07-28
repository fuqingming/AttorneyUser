package com.xinfu.attorneyuser.adapter;

import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.PriceBean;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

public class ClassificationAdapter extends BaseRecyclerAdapter<PriceBean>
{

    @BindView(R.id.tv_select)
    TextView cbSelect;

    private ArrayList<PriceBean> m_arrSelectsList;

    public ClassificationAdapter(ArrayList<PriceBean> arrSelectsList)
    {
        this.m_arrSelectsList = arrSelectsList;
    }

//    public void setSelectList(ArrayList<PriceBean> arrSelectsList)
//    {
//        this.m_arrSelectsList.clear();
//        this.m_arrSelectsList.addAll(arrSelectsList);
//        notifyDataSetChanged();
//    }

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_classification_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final PriceBean data, final int position)
    {
        cbSelect.setText(data.getName().trim());
        cbSelect.setTag(data.getNum());
        for(int i = 0 ; i < m_arrSelectsList.size() ; i ++)
        {
            if(m_arrSelectsList.get(i).getName().trim().equals(data.getName().trim()))
            {
                cbSelect.setBackgroundResource(R.drawable.shape_text_select2);
                cbSelect.setTextColor(mContext.getResources().getColor(R.color.white_color));
                break;
            }
            else
            {
                cbSelect.setBackgroundResource(R.drawable.shape_text_select1);
                cbSelect.setTextColor(mContext.getResources().getColor(R.color.mainColor));
            }
        }
    }
}
