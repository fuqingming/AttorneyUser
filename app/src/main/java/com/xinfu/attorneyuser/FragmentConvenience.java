package com.xinfu.attorneyuser;

import android.content.Intent;
import android.view.View;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.base.BaseFragment;
import com.xinfu.attorneyuser.utils.Utils;

import butterknife.OnClick;

public class FragmentConvenience extends BaseFragment
{
    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.fragment_convenience;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(getContentView(),"便民查询");
    }

    @Override
    protected void initData()
    {

    }

    @OnClick({R.id.ll_enterprise_msg,R.id.ll_statute,R.id.ll_referee,R.id.ll_hotline})
    public void onViewClick(View view)
    {
        Intent it = null;
        switch (view.getId())
        {
            case R.id.ll_enterprise_msg://企业信息
                it = new Intent(getMContext(),EnterpriseMsgActivity.class);
                break;
            //法律法规
            case R.id.ll_statute:
                it = new Intent(getMContext(),StatuteActivity.class);
                break;
            //裁判文书
            case R.id.ll_referee:
                it = new Intent(getMContext(),RefereeActivity.class);
                break;
            //求助热线
            case R.id.ll_hotline:
                it = new Intent(getMContext(),HotLineActivity.class);
                break;
        }
        startActivity(it);
    }
}
