package com.example.anjanibajaj.shophere.model;

/**
 * Created by Anjani Bajaj on 7/6/2017.
 */

public class Product {
    private String name, category;
    private Integer price, pid, cid;

    public Product(String name, String category, Integer price, Integer pid, Integer cid) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.pid = pid;
        this.cid = cid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}