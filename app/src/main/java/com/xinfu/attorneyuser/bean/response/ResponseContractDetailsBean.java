package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.ContractBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseContractDetailsBean {
    private Batch batch;
    private ArrayList<ContractBean> hots;

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public ArrayList<ContractBean> getHots() {
        return hots;
    }

    public void setHots(ArrayList<ContractBean> hots) {
        this.hots = hots;
    }

    public class Batch
    {
        private String id;
        private String mainImage;
        private String title;
        private String subTitle;
        private String price;
        private String marketPrice;
        private String taskId;
        private String sellCount;
        private String task;
        private String filePath;
        private String hasBuy;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getSellCount() {
            return sellCount;
        }

        public void setSellCount(String sellCount) {
            this.sellCount = sellCount;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getHasBuy() {
            return hasBuy;
        }

        public void setHasBuy(String hasBuy) {
            this.hasBuy = hasBuy;
        }
    }

}
