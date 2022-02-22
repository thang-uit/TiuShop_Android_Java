package com.thanguit.tiushop.model.repository;

import java.util.List;

public class CartModel {
    private List<Cart> cartList;

    public CartModel(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
