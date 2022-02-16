package com.thanguit.tiushop.presenter.listener;

import com.thanguit.tiushop.model.repository.Product;

import java.util.List;

public interface GroupProductListener {
    interface View {
        void newProductSuccess(List<Product> newProducts);

        void saleProductSuccess(List<Product> saleProducts);

        void productFail(String error);
    }

    interface Presenter {
        void optionProduct(int amount, String option);
    }
}
