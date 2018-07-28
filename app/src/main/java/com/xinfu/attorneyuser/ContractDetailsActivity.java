package com.xinfu.attorneyuser;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.google.gson.Gson;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;
import com.xinfu.attorneyuser.adapter.ContractDetailsAdapter;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseListActivity;
import com.xinfu.attorneyuser.bean.base.WXRechargeBean;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseContractDetailsBean;
import com.xinfu.attorneyuser.https.ApiStores;
import com.xinfu.attorneyuser.https.FailureDataUtils;
import com.xinfu.attorneyuser.https.HttpCallback;
import com.xinfu.attorneyuser.https.HttpClient;
import com.xinfu.attorneyuser.https.HttpCode;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.CallHttpUserBalanceUtil;
import com.xinfu.attorneyuser.utils.Decrypt;
import com.xinfu.attorneyuser.utils.ImageLoader;
import com.xinfu.attorneyuser.utils.RechargeDataUtils;
import com.xinfu.attorneyuser.utils.Utils;
import com.xinfu.attorneyuser.utils.alert.AlertUtils;
import com.xinfu.attorneyuser.utils.popupwindow.PopupWindowRecharge;
import com.xinfu.attorneyuser.utils.recycler.BaseRecyclerAdapter;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeHandler;
import com.xinfu.attorneyuser.wxapi.RechargeUtils.RechargeThread;
import com.xinfu.attorneyuser.wxapi.WXPayUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author 付庆明
 */

public class ContractDetailsActivity extends BaseListActivity {

    private ContractDetailsAdapter m_contractAdapter = new ContractDetailsAdapter();

    @BindView(R.id.tv_left)
    TextView m_tvBtnLeft;
    @BindView(R.id.tv_right)
    TextView m_tvBtnRight;

    private ImageView m_ivPic;
    private TextView m_ivTitle;
    private TextView m_ivAmount;
    private RelativeLayout m_rlPrice;
    private TextView m_tvPrice;

    private String m_strUuid;
    private String m_strFileUrlPath;
    private String m_strFilePath;


    @Override
    protected int setLayoutResourceId()
     {
        return R.layout.activity_contract_details;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(this,"合同文书",true);

        m_strUuid = getIntent().getStringExtra("uuid");

        initPhotoError();
    }


    private void initPhotoError()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            builder.detectFileUriExposure();
        }
    }

    @Override
    protected BaseRecyclerAdapter getListAdapter() {
        return m_contractAdapter;
    }

    @Override
    protected void initLayoutManager() {
        View header = LayoutInflater.from(this).inflate(R.layout.common_head_contract_details,mRecyclerView, false);
        m_ivPic = header.findViewById(R.id.iv_pic);
        m_ivTitle = header.findViewById(R.id.tv_title);
        m_ivAmount = header.findViewById(R.id.tv_amount);
        m_rlPrice = header.findViewById(R.id.rl_price);
        m_tvPrice = header.findViewById(R.id.tv_price);
        mRecyclerViewAdapter.addHeaderView(header);

        mRecyclerView.setLoadMoreEnabled(false);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.one)
                .setColorResource(R.color.app_backgrount_color)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position) {
                if (m_strUuid.equals(m_contractAdapter.getListData().get(position).getId()))
                {
                    return;
                }
                Intent it = new Intent(ContractDetailsActivity.this,ContractDetailsActivity.class);
                it.putExtra("uuid",m_contractAdapter.getListData().get(position).getId());
                startActivity(it);
            }
        });
    }

    @OnClick({R.id.tv_right})
    public void onViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_right:
                if(m_tvBtnRight.getText().equals("分享"))
                {
                    downLoadFiles();
                }
                else
                {
                    PopupWindowRecharge m_pwMenuRecharge = new PopupWindowRecharge(ContractDetailsActivity.this,onTaskSuccessCompleted);
                    m_pwMenuRecharge.showAtLocation(findViewById(R.id.ll_pop), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
        }
    }

    private OnTaskSuccessComplete onTaskSuccessCompleted = new OnTaskSuccessComplete() {
        @Override
        public void onSuccess(Object obj) {
            if((int)obj == PopupWindowRecharge.RECHARGE_WX)
            {
                callHttpForRecharge(true);
            }
            else if((int)obj == PopupWindowRecharge.RECHARGE_ZFB)
            {
                callHttpForRecharge(false);
            }
        }
    };

    private void downLoadFiles()
    {
        HttpClient.download(m_strFileUrlPath, new DownLoadCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSucess(String key, String path, String name, long fileSize) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path+name)));
                shareIntent.setType("*/*");//此处可发送多种文件
                startActivity(Intent.createChooser(shareIntent, "分享到："));
            }
        });
    }

    private void callHttpForRecharge(boolean isWX)
    {
        ApiStores.userBatchBuy(m_strUuid,isWX, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                  String data = Decrypt.getInstance().decrypt(response.getData());
                    if(isWX)
                    {
                        WXRechargeBean beans = RechargeDataUtils.getWxRechargeData(data);

                        WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                        builder .setAppId(beans.getAppId())
                                .setPrepayId(beans.getPrepayId())
                                .setPackageValue(beans.getPackageName())
                                .setNonceStr(beans.getNonce_str())
                                .setTimeStamp(beans.getTimeStamp())
                                .setSign(beans.getSign())
                                .setPartnerId(beans.getPartnerid())
                                .build().toWXPayNotSign(ContractDetailsActivity.this);
                    }
                    else
                    {
                        RechargeHandler rechargeHandler = new RechargeHandler(ContractDetailsActivity.this, new OnTaskSuccessComplete() {
                            @Override
                            public void onSuccess(Object obj) {
                                CallHttpUserBalanceUtil.callHttpBalance(ContractDetailsActivity.this,waitDialog,null);
                            }
                        });
                        RechargeThread rechargeThread = new RechargeThread(ContractDetailsActivity.this,rechargeHandler,RechargeDataUtils.getZHFRechargeData(data));
                        rechargeThread.start();
                    }
                }
                else
                {
                    FailureDataUtils.showServerReturnErrorMessageEx(ContractDetailsActivity.this,response,setIntent( Const.ForResultData.LOGIN_ACTIVITY_GET_DATA));
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                AlertUtils.MessageAlertShow(ContractDetailsActivity.this, "错误", message);
            }

            @Override
            public void OnRequestStart()
            {
                waitDialog.show();
            }

            @Override
            public void OnRequestFinish()
            {
                waitDialog.dismiss();
            }

        });
    }
    
    @Override
    protected void requestData() {
        ApiStores.userBatchDetail(m_strUuid, new HttpCallback<ResponseBaseBean>()
        {
            @Override
            public void OnSuccess(final ResponseBaseBean response)
            {
                if(response.getStatus() == HttpCode.HTTPCODE_SUCCESS)
                {
                    ResponseContractDetailsBean responseVipBean = new Gson().fromJson(Decrypt.getInstance().decrypt(response.getData()), ResponseContractDetailsBean.class);

                    executeOnLoadDataSuccess(responseVipBean.getHots(),true);

                    ImageLoader.getInstace().loadImg(ContractDetailsActivity.this, m_ivPic, responseVipBean.getBatch().getMainImage());

                    m_ivTitle.setText(responseVipBean.getBatch().getTitle());
                    m_strFileUrlPath = responseVipBean.getBatch().getFilePath();

                    if("0.00".equals(responseVipBean.getBatch().getPrice()))
                    {
                        m_tvBtnLeft.setText("免费");
                        m_tvBtnRight.setText("分享");
                        m_ivAmount.setText("免费");
                        m_rlPrice.setVisibility(View.VISIBLE);
                        m_tvPrice.setText(responseVipBean.getBatch().getMarketPrice());
                    }
                    else
                    {
                        if("1".equals(responseVipBean.getBatch().getHasBuy()))
                        {
                            m_tvBtnLeft.setText("已购");
                            m_tvBtnRight.setText("分享");
                        }
                        else
                        {
                            m_tvBtnLeft.setText(MessageFormat.format("{0}元", responseVipBean.getBatch().getPrice()));
                            m_tvBtnRight.setText("购买");
                        }
                        m_ivAmount.setText(MessageFormat.format("{0}元", responseVipBean.getBatch().getPrice()));
                        m_rlPrice.setVisibility(View.GONE);
                        m_tvPrice.setText(responseVipBean.getBatch().getMarketPrice());
                    }
                }
                else
                {
                    executeOnLoadDataError(null);
                    FailureDataUtils.showServerReturnErrorMessageFragment(ContractDetailsActivity.this,response,Const.ForResultData.LOGIN_ACTIVITY_GET_DATA);
                }

            }

            @Override
            public void OnFailure(final String message)
            {
                executeOnLoadDataError(null);
            }

            @Override
            public void OnRequestStart(){}

            @Override
            public void OnRequestFinish()
            {
                executeOnLoadFinish();
            }

        });
    }
}
