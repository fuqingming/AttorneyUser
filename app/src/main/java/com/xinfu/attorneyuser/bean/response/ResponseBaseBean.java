package com.xinfu.attorneyuser.bean.response;

/**
 * Created by HH
 * Date: 2017/11/9
 */

public class ResponseBaseBean{
   private int status;
   private String result;
   private ResponseDataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ResponseDataBean getData() {
        return data;
    }

    public void setData(ResponseDataBean data) {
        this.data = data;
    }
}
