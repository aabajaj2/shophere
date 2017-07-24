package com.example.anjanibajaj.shophere.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Anjani Bajaj on 7/14/2017.
 *
 */

@Entity
public class ProductTable {

    @PrimaryKey
    private int pid;
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private int cid;
    private String name;
    private Integer price;
    private String imageUrl;

    public ProductTable() {
    }

    public ProductTable(int pid, int cid, String name, Integer price, String imageUrl, String category, Product product) {
        this.pid = pid;
        this.cid = cid;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    @Embedded(prefix = "pt")
    private
    Product product;

    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

