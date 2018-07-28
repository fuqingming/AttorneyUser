package com.xinfu.attorneyuser.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.utils.imagetrans.CustomTransform;

import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import it.liuting.imagetrans.ScaleType;

public class PictureSelectionAdapter extends BaseAdapter
{

	private LayoutInflater m_listContainer;
	private Context m_context;
	private int m_height;
	private List<MediaBean> m_listItems;
	public PictureSelectionAdapter(Context context, List<MediaBean> listItems, int height)
	{
		super();
		this.m_context = context;
		this.m_height = height;
		this.m_listItems = listItems;
		m_listContainer = LayoutInflater.from(context);
	}

	/**
	 * ListView Item设置
	 */
	@SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final Holder holder;
		if (convertView == null)
		{
			holder = new Holder();
			convertView = m_listContainer.inflate(R.layout.index_item_picture, null);
			holder.m_ivIconPay = convertView.findViewById(R.id.iv_icon);
			holder.m_llSize = convertView.findViewById(R.id.ll_size);
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}

		if(m_listItems.get(position) == null)
		{
			Glide.with(m_context).load(R.mipmap.icon_add).into(holder.m_ivIconPay);
		}
		else
		{
			Glide.with(m_context).load(m_listItems.get(position).getThumbnailBigPath())
					.placeholder(R.drawable.place_holder)
					.transform(new CustomTransform(m_context, ScaleType.CENTER_CROP))
					.into(holder.m_ivIconPay);
		}

		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.m_llSize.getLayoutParams(); //取控件item当前的布局参数
		linearParams.height = m_height;//动态设置item高度
		return convertView;

	}

	public int getCount()
	{
		return m_listItems.size();
	}

	public Object getItem(int position)
	{
		return position;
	}

	public long getItemId(int position)
	{
		return position;
	}

	private class Holder
	{
		ImageView m_ivIconPay;
		LinearLayout m_llSize;
	}
}
