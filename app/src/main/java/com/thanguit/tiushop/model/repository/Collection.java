package com.thanguit.tiushop.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection {
    @SerializedName("collectionsID ")
    @Expose
    private String collectionsID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    public Collection() {
    }

    public Collection(String collectionsID, String name, String image) {
        this.collectionsID = collectionsID;
        this.name = name;
        this.image = image;
    }

    public String getCollectionsID() {
        return collectionsID;
    }

    public void setCollectionsID(String collectionsID) {
        this.collectionsID = collectionsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
