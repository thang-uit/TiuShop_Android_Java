package com.thanguit.tiushop.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Product {
    @SerializedName("productID")
    @Expose
    private String productID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private List<String> image;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("sale")
    @Expose
    private String sale;

    @SerializedName("isSale")
    @Expose
    private String isSale;

    @SerializedName("finalPrice")
    @Expose
    private String finalPrice;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("categoryID")
    @Expose
    private String categoryID;

    @SerializedName("collectionID")
    @Expose
    private String collectionID;

    @SerializedName("stock")
    @Expose
    private String stock;

    @SerializedName("isWishList")
    @Expose
    private boolean isWishList;

    @SerializedName("isBought")
    @Expose
    private boolean isBought;

    public Product() {
    }

    public Product(String productID, String name, List<String> image, String price, String sale, String isSale, String finalPrice, String description, String date, String gender, String categoryID, String collectionID, String stock, boolean isWishList, boolean isBought) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.sale = sale;
        this.isSale = isSale;
        this.finalPrice = finalPrice;
        this.description = description;
        this.date = date;
        this.gender = gender;
        this.categoryID = categoryID;
        this.collectionID = collectionID;
        this.stock = stock;
        this.isWishList = isWishList;
        this.isBought = isBought;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getIsSale() {
        return isSale;
    }

    public void setIsSale(String isSale) {
        this.isSale = isSale;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public boolean isWishList() {
        return isWishList;
    }

    public void setWishList(boolean wishList) {
        isWishList = wishList;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", price='" + price + '\'' +
                ", sale='" + sale + '\'' +
                ", isSale='" + isSale + '\'' +
                ", finalPrice='" + finalPrice + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", gender='" + gender + '\'' +
                ", categoryID='" + categoryID + '\'' +
                ", collectionID='" + collectionID + '\'' +
                ", stock='" + stock + '\'' +
                ", isWishList=" + isWishList +
                ", isBought=" + isBought +
                '}';
    }
}
