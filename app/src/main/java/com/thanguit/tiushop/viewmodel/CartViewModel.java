package com.thanguit.tiushop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartViewModel extends ViewModel {
    private static final String TAG = "CartViewModel";
    private MutableLiveData<List<Cart>> cartList;
    private MutableLiveData<Integer> totalPrice;

    private MutableLiveData<Integer> removePosition;

//    private MutableLiveData<String> cartAmount;

    public LiveData<List<Cart>> getCart(String userID) {
        if(cartList == null) {
            cartList = new MutableLiveData<>();
        }
        loadCart(userID);
        return cartList;
    }

    public LiveData<Integer> removePosition() {
        if(removePosition == null) {
            removePosition = new MutableLiveData<>();
            removePosition.setValue(-1);
        }
        return removePosition;
    }

    public LiveData<List<Cart>> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList.setValue(cartList);
    }

    private void loadCart(String userID) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("userID", userID);

        DataClient dataClient = APIClient.getData();
        dataClient.getCart(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<List<Cart>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull APIResponse<List<Cart>> listAPIResponse) {
                        if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            cartList.setValue(listAPIResponse.getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void deleteCart(Cart cart, int position) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("cartID", cart.getCartID());
        DataClient dataClient = APIClient.getData();
        dataClient.deleteCart(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
                        if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            removePosition.setValue(position);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
