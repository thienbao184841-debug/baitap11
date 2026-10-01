package com.videoshare.models;

import java.io.Serializable;

public class Category_24110162 implements Serializable {
    private int categoryId;
    private String categoryname;
    private String categorycode;
    private String images;
    private boolean status;

    public Category_24110162() {
    }

    public Category_24110162(int categoryId, String categoryname, String categorycode, String images, boolean status) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
        this.categorycode = categorycode;
        this.images = images;
        this.status = status;
    }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }

    public String getCategorycode() { return categorycode; }
    public void setCategorycode(String categorycode) { this.categorycode = categorycode; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
