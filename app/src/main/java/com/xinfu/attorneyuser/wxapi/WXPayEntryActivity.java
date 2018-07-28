package com.xinfu.attorneyuser.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.https.ResponseResultBean;
import com.xinfu.attorneyuser.settings.BroadcastConstant;
import com.xinfu.attorneyuser.settings.Const;
import com.xinfu.attorneyuser.utils.CallHttpUserBalanceUtil;
import com.xinfu.attorneyuser.utils.dialog.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private WaitDialog waitDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx5679dbc4dc224b36");
        api.handleIntent(getIntent(), this);
        waitDialog = new WaitDialog(this);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req)
    {
    }

    @Override
    public void onResp(BaseResp resp)
    {
        if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX)
        {
            if(resp.errCode==0)
            {
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            }
        }
        Intent it = new Intent(BroadcastConstant.BROADCAST_RECEIVE_RECHARGE_WX);
        it.putExtra("errCode",resp.errCode);
        it.putExtra("type",resp.getType());
        sendBroadcast(it);
        finish();
    }
}