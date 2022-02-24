package com.thanguit.tiushop.model.repository;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Cart {
    @SerializedName("cartID")
    @Expose
    private String cartID;

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

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    public Cart() {
    }

    public Cart(String cartID, String productID, String name, List<String> image, String price, String sale, String isSale, String finalPrice, String size, int quantity) {
        this.cartID = cartID;
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.sale = sale;
        this.isSale = isSale;
        this.finalPrice = finalPrice;
        this.size = size;
        this.quantity = quantity;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID='" + cartID + '\'' +
                ", productID='" + productID + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", price='" + price + '\'' +
                ", sale='" + sale + '\'' +
                ", isSale='" + isSale + '\'' +
                ", finalPrice='" + finalPrice + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return getQuantity() == cart.getQuantity() && getCartID().equals(cart.getCartID()) && getProductID().equals(cart.getProductID()) && getName().equals(cart.getName()) && getImage().equals(cart.getImage()) && getPrice().equals(cart.getPrice()) && getSale().equals(cart.getSale()) && getIsSale().equals(cart.getIsSale()) && getFinalPrice().equals(cart.getFinalPrice()) && getSize().equals(cart.getSize());
    }
}
