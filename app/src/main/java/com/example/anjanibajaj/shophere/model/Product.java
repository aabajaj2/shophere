package com.example.anjanibajaj.shophere.model;

import java.util.List;

/**
 * Created by Anjani Bajaj on 7/6/2017.
 * Model for the gadgets in the store
 */

public class Product {
    private String name;
    private String category;
    private String imageUrl;
    private Integer price, pid, cid;
    private List<String> imageList;


    public Product(String name, String category, Integer price, Integer pid, Integer cid, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.pid = pid;
        this.cid = cid;
        this.imageUrl = imageUrl;
    }
    public Product(String name, String category, Integer price, Integer pid, Integer cid) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.pid = pid;
        this.cid = cid;
    }

    public Product(String name, String category, Integer price, Integer pid, Integer cid, String imageUrl, List<String> id2) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.pid = pid;
        this.cid = cid;
        this.imageUrl = imageUrl;
        this.imageList = id2;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
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


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}