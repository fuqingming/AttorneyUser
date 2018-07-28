package com.xinfu.attorneyuser.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.PriceBean;

import java.util.List;

public class MenuTypeAdapter extends MBaseAdapter<PriceBean> {

	public MenuTypeAdapter(Activity context, List<PriceBean> datas, int normalBg, int pressBg) {
		super(context, datas);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		PriceBean jobType = (PriceBean) getItem(position);
	        final View view;
	        ViewHolder holder;
	        if(convertView == null) {
	            view = mInflater.inflate(R.layout.filter_menu_listview_item,null);
	            holder = new ViewHolder();
	            holder.mFilterMenuListItem = (LinearLayout) view.findViewById(R.id.mFilterMenuListItem);
	            holder.tv = (TextView) view.findViewById(R.id.tv);
	            view.setTag(holder);
	        } else {
	            view = convertView;
	            holder = (ViewHolder) view.getTag();
	        }
	        holder.tv.setText(jobType.getName());
	        if(position == selection) {
	            holder.tv.setBackgroundResource(pressBg);
	        } else {
	            holder.tv.setBackgroundResource(normalBg);
	        }
	        
//	        holder.mFilterMenuListItem.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					mOnItemClickListenerAdapterView.onItemClick(view, position, (PriceBean) getItem(position));
//				}
//			});
	        
	        return view;
	}

	public void setPressPostion(int position) {
        this.selection = position;
    }
	
	class ViewHolder{
        TextView tv;
        LinearLayout mFilterMenuListItem;
    }

	@Override
	public String getText(int position) {
		PriceBean jobType = (PriceBean) getItem(position);
		return jobType.getName();
	}
}
