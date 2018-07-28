package com.xinfu.attorneyuser.utils.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.xinfu.attorneyuser.R;

/**
 * Created by junweiliu on 16/11/7.
 */
public class CommonFilterPop extends PopupWindow
{
    /**
     * 布局填充器
     */
    private LayoutInflater mInflater;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * pop整体View
     */
    private View popupView;
    /**
     * 选择条件的list
     */
    private ListView contentLv;
    /**
     * 筛选条件选择后的回调
     */
    AdapterView.OnItemClickListener itemClickListener;
    /**
     * 适配器
     */
    BaseAdapter adapter;

    private ImageView view;

    /**
     * 构造函数
     *  @param context
     *
     */
    public CommonFilterPop(Context context, BaseAdapter baseAdapter)
    {
        this.mInflater = LayoutInflater.from(context);
        this.adapter = baseAdapter;
        this.mContext = context;
        popupView = mInflater.inflate(
                R.layout.popup_list_dialog, null);
        //设置View
        this.setContentView(popupView);
        //设置弹出窗体的宽高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //初始化控件
        initPopView();
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        //需要动画效果的话可以设置
        //this.setAnimationStyle(R.style.PopupWindowAnimation);
        this.update();
    }


    private void initPopView()
    {
        view = popupView.findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
//        view.getBackground().setAlpha(12);
        contentLv = popupView.findViewById(R.id.lv_pop);
        contentLv.setAdapter(adapter);
    }


    /**
     * listview点击事件
     *
     * @param itemClickListener
     */
    public void setOnItemSelectedListener(AdapterView.OnItemClickListener itemClickListener)
    {
        if (null != itemClickListener && null != contentLv)
        {
            contentLv.setOnItemClickListener(itemClickListener);
        }
    }

    @Override
    public void showAsDropDown(View anchor)
    {
        if(Build.VERSION.SDK_INT == 24)
        {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}