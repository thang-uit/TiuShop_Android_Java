package com.thanguit.tiushop.presenter.listener;

import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.model.repository.Collection;

import java.util.List;

public interface CartListener {
    interface View {
        void cartSuccess(List<Cart> cartList);

        void cartFail(String error);
    }

    interface Presenter {
        void handleCart(String userID);
    }
}
