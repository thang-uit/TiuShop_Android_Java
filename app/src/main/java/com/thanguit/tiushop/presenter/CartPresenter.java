package com.thanguit.tiushop.presenter;

import android.util.Log;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.presenter.listener.CartListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartPresenter implements CartListener.Presenter {
    private static final String TAG = "CartPresenter";
    private CartListener.View view;

    public CartPresenter(CartListener.View view) {
        this.view = view;
    }

    @Override
    public void handleCart(String userID) {
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
                            view.cartSuccess(listAPIResponse.getData());
                        } else {
                            view.cartFail(MyApplication.getResource().getString(R.string.tvError9));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                        view.cartFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
