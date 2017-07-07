package com.example.anjanibajaj.shophere.model;

/**
 * Created by Anjani Bajaj on 7/7/2017.
 */

public class Category {
    public Integer cid;
    public String type;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category(Integer cid, String type) {
        this.cid = cid;
        this.type = type;
    }
}
