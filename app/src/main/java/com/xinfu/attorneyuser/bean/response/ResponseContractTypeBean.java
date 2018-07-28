package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.ContractBean;
import com.xinfu.attorneyuser.bean.base.ContractTypeBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseContractTypeBean {
    private ArrayList<ContractTypeBean> cats;

    public ArrayList<ContractTypeBean> getCats() {
        return cats;
    }

    public void setCats(ArrayList<ContractTypeBean> cats) {
        this.cats = cats;
    }
}
