package com.xinfu.attorneyuser.utils;

import com.xinfu.attorneyuser.bean.base.WXRechargeBean;

import org.json.JSONException;
import org.json.JSONObject;

public class RechargeDataUtils
{

    public static WXRechargeBean getWxRechargeData(String dataJson)
    {
        WXRechargeBean wxRechargeBean = new WXRechargeBean();
        try {
            JSONObject json = new JSONObject(dataJson);
            String sign = json.getString("sign");
            String appId = json.getString("appId");
            String partnerId = json.getString("partnerid");
            String prepayId = json.getString("prepayId");
            String nonceStr = json.getString("nonce_str");
            String timeStamp = json.getString("timeStamp");
            String packageValue = json.getString("package");

            wxRechargeBean.setSign(sign);
            wxRechargeBean.setPartnerid(partnerId);
            wxRechargeBean.setAppId(appId);
            wxRechargeBean.setPrepayId(prepayId);
            wxRechargeBean.setNonce_str(nonceStr);
            wxRechargeBean.setTimeStamp(timeStamp);
            wxRechargeBean.setPackageName(packageValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wxRechargeBean;
    }

    public static String getZHFRechargeData(String dataJson)
    {
        String sign = "";
        try {
            JSONObject json = new JSONObject(dataJson);
            sign = json.getString("sign");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sign;
    }
}
