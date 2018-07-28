package com.xinfu.attorneyuser.utils.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by HH
 * Date: 2017/11/13
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder
{

    View mView;

    public PhotoViewHolder(View itemView)
    {
        super(itemView);
        mView = itemView;
    }

    public View getView()
    {
        return mView;
    }
}
