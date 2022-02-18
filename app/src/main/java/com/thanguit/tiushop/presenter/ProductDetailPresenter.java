package com.thanguit.tiushop.presenter;

import android.util.Log;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.presenter.listener.ProductDetailListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailPresenter implements ProductDetailListener.Presenter {
    private static final String TAG = "ProductDetailPresenter";
    private ProductDetailListener.View view;

    public ProductDetailPresenter(ProductDetailListener.View view) {
        this.view = view;
    }

    @Override
    public void handleProduct(String productID, String userID) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("productID", productID);
        jsonBody.put("userID", userID);

        DataClient dataClient = APIClient.getData();
        dataClient.getProductDetail(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull APIResponse<Product> productAPIResponse) {
                        if (productAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            view.productSuccess(productAPIResponse.getData());
                        } else {
                            view.productFail(MyApplication.getResource().getString(R.string.tvError9));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                        view.productFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
