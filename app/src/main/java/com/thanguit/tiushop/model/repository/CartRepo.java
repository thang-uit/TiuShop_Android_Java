package com.thanguit.tiushop.model.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {
    private MutableLiveData<List<Cart>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<Cart>> getCart() {
        if (mutableLiveData.getValue() == null) {
            initCart();
        }
        return mutableLiveData;
    }

    public void initCart() {
        mutableLiveData.setValue(new ArrayList<Cart>());
    }
}
