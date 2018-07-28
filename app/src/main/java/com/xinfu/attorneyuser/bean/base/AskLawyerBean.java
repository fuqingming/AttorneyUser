package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class AskLawyerBean implements Serializable {
    private String wzid;
    private List list;

    public String getWzid() {
        return wzid;
    }

    public void setWzid(String wzid) {
        this.wzid = wzid;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public class List
    {
        private String wzid;
        private String type;
        private String content;
        private String create_time;

        public String getWzid() {
            return wzid;
        }

        public void setWzid(String wzid) {
            this.wzid = wzid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
