package com.xinfu.attorneyuser.https;

import com.google.gson.Gson;
import com.xinfu.attorneyuser.bean.response.ResponseDataBean;
import com.xinfu.attorneyuser.utils.Decrypt;

public class ResponseDataUtils<T> {

    public T getListData(ResponseDataBean data, Class<T> tClass)
    {
        String strData = "{\"data\":"+ Decrypt.getInstance().decrypt(data)+"}";

        T t = new Gson().fromJson(strData, tClass);

        return t;
    }
}
