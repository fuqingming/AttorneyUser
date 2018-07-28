package com.xinfu.attorneyuser.https;

import java.io.Serializable;

public class ResponseResultBean<T>  implements Serializable {

    private int code;
    private T tData;

    public ResponseResultBean() {
    }

    public ResponseResultBean(int code) {
        this.code = code;
    }

    public ResponseResultBean(int code, T tData) {
        this.code = code;
        this.tData = tData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T gettData() {
        return tData;
    }

    public void settData(T tData) {
        this.tData = tData;
    }
}
