package com.xinfu.attorneyuser.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.ChildBean;

import java.util.List;

public class DistrictAdapter extends MBaseAdapter<ChildBean> {

	public DistrictAdapter(Activity context, List<ChildBean> datas, int normalBg, int pressBg) {
		super(context, datas);
		  initParams(normalBg, pressBg);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ChildBean district = (ChildBean) getItem(position);
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
	        holder.tv.setText(district.getShortname());
	        if(position == selection) {
	            holder.tv.setBackgroundResource(pressBg);
	        } else {
	            holder.tv.setBackgroundResource(normalBg);
	        }
	        
//	        holder.mFilterMenuListItem.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					mOnItemClickListenerAdapterView.onItemClick(view, position, (ChildBean) getItem(position));
//				}
//			});
	        
	        return view;
	}
	
	class ViewHolder{
        TextView tv;
        LinearLayout mFilterMenuListItem;
    }

	@Override
	public String getText(int position) {
		ChildBean district = (ChildBean) getItem(position);
		return district.getShortname();
	}
}
