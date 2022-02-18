package com.thanguit.tiushop.presenter.listener;

import com.thanguit.tiushop.model.repository.Product;

public interface ProductDetailListener {
    interface View {
        void productSuccess(Product product);

        void productFail(String error);
    }

    interface Presenter {
        void handleProduct(String productID, String userID);
    }
}
