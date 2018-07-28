package com.xinfu.attorneyuser.utils;

import android.util.Log;

import com.xinfu.attorneyuser.settings.GlobalVariables;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/17.
 */

public class MD5Encrypt {


    private static MD5Encrypt instance;
    private MD5Util md5Util = MD5Util.getInstance();

    /**
     * 获取工具类实例
     */
    public static MD5Encrypt getInstance(){
        if(instance == null){
            instance = new MD5Encrypt();
        }
        return instance;
    }

    //得到签名，MD5加密
    public String getsignMD5(Map<String,Object> map, List<String> keyList){
        String jsession = (GlobalVariables.getUserId()+GlobalVariables.getToken()).trim().toUpperCase();

        Log.d("map","======test2"+keyList);
        StringBuffer parametersSb = new StringBuffer();
        StringBuffer signSb = new StringBuffer();
        for (int i = 0;i<keyList.size();i++){
            parametersSb.append(keyList.get(i)+map.get(keyList.get(i)));
            Log.d("map","======"+ parametersSb);

        }
        String str = jsession + parametersSb.toString().trim();
        String STR = str.toUpperCase();

        signSb.append(STR);
        //把转成大写的字符串加密3次
        for (int i  = 0;i<3;i++){
            int length = signSb.length();
            signSb.append(md5Util.md5Code(signSb.toString().trim()).toUpperCase());
            signSb.delete(0, length -1);
        }
        String sign = signSb.toString().trim().toUpperCase();
        //返回最后获取到的sign签名
        return sign;
    }
}
