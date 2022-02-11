package com.thanguit.tiushop.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryID")
    @Expose
    private String categoryID;

    @SerializedName("name")
    @Expose
    private String name;

    public Category() {
    }

    public Category(String categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
