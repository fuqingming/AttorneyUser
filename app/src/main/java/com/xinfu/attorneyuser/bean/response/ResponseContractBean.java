package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.ContractBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseContractBean {
    private ArrayList<ContractBean> lists;

    public ArrayList<ContractBean> getLists() {
        return lists;
    }

    public void setLists(ArrayList<ContractBean> lists) {
        this.lists = lists;
    }
}
