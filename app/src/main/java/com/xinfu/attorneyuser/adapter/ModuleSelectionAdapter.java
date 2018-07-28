package com.xinfu.attorneyuser.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ModuleSelectionAdapter extends BaseAdapter {
	
	private LayoutInflater m_listContainer;
	private Context m_context;
	private int m_height;
	private List<Map<String, Object>> m_listItems;
	public ModuleSelectionAdapter(Context context, List<Map<String, Object>> listItems, int height) {
		super();
		this.m_context = context;
		this.m_height = height;
		if (listItems == null) {
			listItems = new ArrayList<Map<String, Object>>();
		} else {
			this.m_listItems = listItems;
		}
		m_listContainer = LayoutInflater.from(context);
	}

	/**
	 * ListView Item设置
	 */
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = m_listContainer.inflate(R.layout.index_item, null);
			holder.m_ivIconPay = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.m_tvTextPay = (TextView) convertView.findViewById(R.id.tv_text);
			holder.m_llSize = (LinearLayout)convertView.findViewById(R.id.ll_size);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		int tplj = Integer.parseInt(m_listItems.get(position).get("icon").toString());
		holder.m_ivIconPay.setImageResource(tplj);
		holder.m_tvTextPay.setText(m_listItems.get(position).get("text").toString());
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.m_llSize.getLayoutParams(); //取控件item当前的布局参数
		linearParams.height = m_height;//动态设置item高度
		return convertView;

	}

	public int getCount() {
		return m_listItems.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	private class Holder {
		ImageView m_ivIconPay;
		TextView m_tvTextPay;
		LinearLayout m_llSize;
	}	

}
