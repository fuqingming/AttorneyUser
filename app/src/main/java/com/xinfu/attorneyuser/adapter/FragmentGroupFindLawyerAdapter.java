package com.xinfu.attorneyuser.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.base.LawyerBean;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerViewHolder;

import java.text.MessageFormat;

import butterknife.BindView;

public class FragmentGroupFindLawyerAdapter extends BaseRecyclerAdapter<LawyerBean>
{


    @BindView(R.id.head_img)
    ImageView headimg;

    @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.tv_area)
    TextView area;
    @BindView(R.id.tv_firm)
    TextView firm;
    @BindView(R.id.tv_pression)
    TextView pression;
    @BindView(R.id.tv_experience)
    TextView experience;
    @BindView(R.id.star1)
    ImageView star1;
    @BindView(R.id.star2)
    ImageView star2;
    @BindView(R.id.star3)
    ImageView star3;

    public FragmentGroupFindLawyerAdapter() {}

    @Override
    protected int getContentView(int viewType)
    {
        return R.layout.item_fragment_find_law_info;
    }

    @Override
    protected void covert(BaseRecyclerViewHolder holder, final LawyerBean data, final int position)
    {
        ImageLoader.getInstace().loadRoundedCornersImg(mContext,headimg,data.getHeadimg(),7,R.drawable.place_holder);
        if (!TextUtils.isEmpty(data.getUsername()) && data.getUsername() != null) {
            name.setText(data.getUsername());
        } else {
            name.setText("--");
        }

        if (!TextUtils.isEmpty(data.getAddress()) && data.getAddress() != null) {
            area.setText(MessageFormat.format("({0})", data.getAddress()));
        }

        if (!TextUtils.isEmpty(data.getOffice()) && data.getOffice() != null) {
            firm.setText(data.getOffice());
        } else {
            firm.setText("--");
        }


        if (data.getProfession() != null && !data.getProfession().isEmpty()) {
            String strPression = "";
            for (int i = 0; i < data.getProfession().size(); i++) {
                if (i == data.getProfession().size() - 1) {
                    strPression += data.getProfession().get(i).getName();
                } else {
                    strPression += data.getProfession().get(i).getName() + "/";
                }
            }
            pression.setText(strPression);
        } else {
            pression.setText("--");
        }
        if (!TextUtils.isEmpty(data.getWork_year()) && data.getWork_year() != null) {
            experience.setText(data.getWork_year() + "年");
        } else {
            experience.setText("--年");
        }
            /*ratingbar.setStar(2);
            ratingbar.setRight(Integer.parseInt(data.getGrade()));
            ratingbar.setClickable(false);
            ratingbar.setStarImageSize(50);*/
        if (data.getGrade().equals("1")) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
        } else if (data.getGrade().equals("2")) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.INVISIBLE);
        } else if (data.getGrade().equals("3")) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
        } else {
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
        }
    }
}
