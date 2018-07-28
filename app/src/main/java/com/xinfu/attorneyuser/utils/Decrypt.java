package com.xinfu.attorneyuser.utils;

import android.util.Base64;

import com.xinfu.attorneyuser.bean.response.ResponseDataBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解密网络请求到的数据
 * Created by LoveSnow on 2017/7/18.
 */

public class Decrypt {

    public RSA rsa = RSA.getInstance();
    public AESUtil aes = AESUtil.getInstance();
    private String key;
    private String iv;
    private String data1;

    public static Decrypt instance;

    /**
     * 获取工具类实例
     * @return
     */
    public static Decrypt getInstance(){
        if(instance == null){
            instance = new Decrypt();
        }
        return instance;
    }

    public String decrypt(String data){
        try {
            JSONObject object = new JSONObject(data);
            key = rsa.decryptByPublicKey(object.getString("key"));
            iv = rsa.decryptByPublicKey(object.getString("iv"));
            data1 = object.getString("data1");
            byte[] new_key = key.getBytes();
            byte[] new_iv = iv.getBytes();
            byte[] new_data = Base64.decode(data1, Base64.DEFAULT);
            byte[] AESdecrypt_Data = aes.decrypt(new_data,new_key,new_iv);
            //将byte[] AESdecrypt_Data转成String
            String AESdecryptData = new String(AESdecrypt_Data);
            /*JSONObject jsonObject = new JSONObject(AESdecryptData);*/
            return AESdecryptData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(ResponseDataBean data){
        key = rsa.decryptByPublicKey(data.getKey());
        iv = rsa.decryptByPublicKey(data.getIv());
        data1 = data.getData1();
        byte[] new_key = key.getBytes();
        byte[] new_iv = iv.getBytes();
        byte[] new_data = Base64.decode(data1, Base64.DEFAULT);
        byte[] AESdecrypt_Data = aes.decrypt(new_data,new_key,new_iv);
        //将byte[] AESdecrypt_Data转成String
        String AESdecryptData = new String(AESdecrypt_Data);
            /*JSONObject jsonObject = new JSONObject(AESdecryptData);*/
        return AESdecryptData;
    }
}
