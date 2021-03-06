package com.xinfu.attorneyuser.utils.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by HH
 * Date: 2017/11/13
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder>
{
    protected Context mContext;
    protected List<T> mDatas  = new ArrayList<>();

    public BaseRecyclerAdapter(){}

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(getContentView(viewType),parent,false);
        return new BaseRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position)
    {
        ButterKnife.bind(this, holder.getView());
        covert(holder,mDatas.get(position),position);
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    protected abstract int getContentView(int viewType);

    protected abstract void covert(BaseRecyclerViewHolder holder, T data, int position);

    protected OnSelectClickListener m_ListenerSelectFragment = null;

    public interface OnSelectClickListener
    {
        void OnSelectClick(Bundle bundle);
    }

    public void onSelectFragmentClickListener(OnSelectClickListener listener)
    {
        m_ListenerSelectFragment = listener;
    }

    public void setDataList(Collection<T> list)
    {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getListData()
    {
        return mDatas;
    }

    public void addAll(Collection<T> list)
    {
        int lastIndex = this.mDatas.size();
        if (this.mDatas.addAll(list))
        {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void clear()
    {
        mDatas.clear();
        notifyDataSetChanged();
    }

    protected DoClickListener doClickListener = null;

    public interface DoClickListener
    {
        void DoClick(Object obj);
    }

    public void onDoClickListener (DoClickListener doClickListener)
    {
        this.doClickListener = doClickListener;
    }
}
