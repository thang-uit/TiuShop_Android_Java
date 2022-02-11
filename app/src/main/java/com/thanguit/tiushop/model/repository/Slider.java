package com.thanguit.tiushop.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {
    @SerializedName("sliderID")
    @Expose
    private String sliderID;

    @SerializedName("sliderImg")
    @Expose
    private String sliderImg;

    @SerializedName("productID")
    @Expose
    private String productID;

    public Slider() {
    }

    public Slider(String sliderID, String sliderImg, String productID) {
        this.sliderID = sliderID;
        this.sliderImg = sliderImg;
        this.productID = productID;
    }

    public String getSliderID() {
        return sliderID;
    }

    public void setSliderID(String sliderID) {
        this.sliderID = sliderID;
    }

    public String getSliderImg() {
        return sliderImg;
    }

    public void setSliderImg(String sliderImg) {
        this.sliderImg = sliderImg;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
