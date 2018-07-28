package com.xinfu.attorneyuser;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.xinfu.attorneyuser.adapter.ModuleSelectionAdapter;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseHttpCompatActivity;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseUserInfoBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.https.ResponseResultBean;
import com.xinfu.attorneyuser.huanxin.Constant;
import com.xinfu.attorneyuser.huanxin.ui.ChatActivity;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 * author 付庆明
 */

public class PersonalCenterActivity extends BaseHttpCompatActivity{

    public static final int MODIFY_MINE_CENTER = 0;

    @BindView(R.id.iv_icon)
    ImageView m_ivIcon;
    @BindView(R.id.tv_name)
    TextView m_tvName;
    @BindView(R.id.gridview_functions)
    GridView m_gridView;

    private String[] m_arrText = {  "我的钱包", "订单中心", "会员中心","收藏中心", "我的卡券", "客服中心"};

    private int[] m_arrIcon = { R.mipmap.user_wallet, R.mipmap.user_order, R.mipmap.user_vip,
                                R.mipmap.user_collect, R.mipmap.user_coupon, R.mipmap.user_kefu};

    private enum FunctionIndex{ USER_WALLET, USER_ORDER, USER_VIP,
                                USER_COLLECT, USER_COUPON, USER_KEFU}

    private String m_strIcon;
    private String m_strName;
    private String m_strSex;
    private String m_strPhone;
    private String m_strMail;
    private String m_strScore;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"个人中心",true);
    }

    @Override
    protected void initData()
    {
        // 换算gridview中item高度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int nWidth = dm.widthPixels/3;

        // 获取数据
        List<Map<String, Object>> m_listData = getListData(m_arrIcon, m_arrText);
        ModuleSelectionAdapter m_adapter = new ModuleSelectionAdapter(this, m_listData, nWidth);

        m_gridView.setAdapter(m_adapter);

        m_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        // 点击一项
        m_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent it;
                switch (FunctionIndex.values()[position])
                {
                    case USER_WALLET:
                    {
                        it = new Intent(PersonalCenterActivity.this,MyWalletActivity.class);
                        startActivity(it);
                    }
                    break;

                    case USER_ORDER:
                    {
                        it = new Intent(PersonalCenterActivity.this,MyOrderActivity.class);
                        startActivity(it);
                    }
                    break;

                    case USER_VIP:
                    {
                        it = new Intent(PersonalCenterActivity.this,MemberCenterActivity.class);
                        it.putExtra("strIcon",m_strIcon);
                        it.putExtra("strName",m_strName);
                        it.putExtra("strScore",m_strScore);
                        startActivity(it);
                    }
                    break;

                    case USER_COLLECT:
                    {
                        it = new Intent(PersonalCenterActivity.this,MyCollectionActivity.class);
                        startActivity(it);
                    }
                    break;

                    case USER_COUPON:
                    {
                        it = new Intent(PersonalCenterActivity.this,MyCardActivity.class);
                        startActivity(it);
                    }
                    break;

                    case USER_KEFU:
                    {
                        it = new Intent(PersonalCenterActivity.this, ChatActivity.class);
                        it.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        it.putExtra(Constant.EXTRA_USER_ID, "kefu");
                        it.putExtra(Constant.CHATTYPE_KEFU, true);
                        it.putExtra(Constant.CHATTYPE_CHAT_TIME, "900");
                        startActivity(it);
                    }
                    break;


                }
            }
        });
    }

    private List<Map<String, Object>> getListData(int[] m_arrIcon, String[] m_arrText)
    {
        ArrayList<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

        for(int i = 0; i < m_arrIcon.length; i ++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", m_arrIcon[i]);
            map.put("text", m_arrText[i]);
            listData.add(map);
        }

        return listData;
    }

    @OnClick({R.id.ll_mine,R.id.tv_contract,R.id.tv_about,R.id.btn_logout})
    public void onViewClick(View v)
    {
        Intent it = null;
        switch (v.getId())
        {
            case R.id.ll_mine://我的
                it = new Intent(this,MineActivity.class);
                it.putExtra("strIcon",m_strIcon);
                it.putExtra("strName",m_strName);
                it.putExtra("strSex",m_strSex);
                it.putExtra("strPhone",m_strPhone);
                it.putExtra("strMail",m_strMail);
                startActivityForResult(it,MODIFY_MINE_CENTER);
                break;
            case R.id.tv_contract://我的合同文书
                it = new Intent(this,ContractActivity.class);
                startActivity(it);
                break;
            case R.id.tv_about://关于律接通

                break;
            case R.id.btn_logout:
                Utils.showLogOutDialog(this, obj ->
                {
                    EventBus.getDefault().post(new ResponseResultBean(Const.ForResultData.LOGOUT));
                    finish();
                });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent it)
    {
        super.onActivityResult(requestCode, resultCode, it);
        if(requestCode == MODIFY_MINE_CENTER && resultCode == RESULT_OK)
        {
            finish();
            Intent intent = new Intent(PersonalCenterActivity.this,PersonalCenterActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void getData()
    {
        ApiStores.userInfo( new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    executeOnLoadDataSuccess(true);
                    ResponseUserInfoBean data = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseUserInfoBean.class);

                    ImageLoader.getInstace().loadCircleImg(PersonalCenterActivity.this, m_ivIcon,data.getCommon().getHead() );
                    m_tvName.setText(data.getCommon().getNickname());

                    m_strIcon = data.getCommon().getHead();
                    m_strName = data.getCommon().getNickname();
                    m_strSex = data.getCommon().getSex();
                    m_strPhone = data.getCommon().getMobile();
                    m_strMail = data.getCommon().getEmail();
                    m_strScore = data.getCommon().getScore();
                }
                else
                {
                    executeOnLoadDataSuccess(false);
                    FailureDataUtils.showServerReturnErrorMessageEx(PersonalCenterActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataSuccess(false);
                AlertUtils.MessageAlertShow(PersonalCenterActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish(){}

        });
    }
}
