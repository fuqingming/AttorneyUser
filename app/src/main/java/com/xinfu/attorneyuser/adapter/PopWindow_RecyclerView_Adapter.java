package com.xinfu.attorneyuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinfu.attorneyuser.R;

/**
 * Created by LoveSnow on 2017/7/16.
 */

public class PopWindow_RecyclerView_Adapter extends RecyclerView.Adapter<PopWindow_RecyclerView_Adapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private Integer[] imgList;
    private String[] mList;
    private OnItemClickListener myOnItemClickListener;

    public PopWindow_RecyclerView_Adapter(Context mContext, Integer[] imgList, String[] mList) {
        this.mContext = mContext;
        this.imgList = imgList;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_item,parent,false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemText.setText(mList[position]);
        holder.itemImg.setImageResource(imgList[position]);
        RelativeLayout.MarginLayoutParams lm_h = (RelativeLayout.LayoutParams)holder.h_vi.getLayoutParams();
        RelativeLayout.MarginLayoutParams lm_s = (RelativeLayout.LayoutParams)holder.s_vi.getLayoutParams();
        if (position == mList.length-1){
//            holder.h_vi.setVisibility(View.GONE);
        }
        /*if (position == 0 ||
                position == 1 ||
                position == 2){
            lm_s.setMargins(0,20,0,0);

        }*/

//        if (position == 0 ||
//                position == 3 ||
//                position == 6){
////            lm_h.setMargins(20,0,0,0);
//        }
//
//        if (position == 2 ||
//                position == 5 ||
//                position == 8){
//            lm_h.setMargins(0,0,20,0);
//        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    @Override
    public void onClick(View v) {
        if (myOnItemClickListener != null){
            myOnItemClickListener.OnItemClick(v,(int)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImg;
        private TextView itemText;
        private View h_vi;
        private View s_vi;
        public ViewHolder(View itemView) {
            super(itemView);
            itemImg = (ImageView) itemView.findViewById(R.id.img_item);
            itemText = (TextView) itemView.findViewById(R.id.tv_item);
            h_vi = (View) itemView.findViewById(R.id.h_vi);
            s_vi = (View) itemView.findViewById(R.id.s_vi);
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.myOnItemClickListener = onItemClickListener;
    }
}
