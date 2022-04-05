package com.thanguit.tiushop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Product;
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

public class ShopViewModel extends ViewModel {
    private static final String TAG = "ShopViewModel";
    private MutableLiveData<List<Product>> newProducts, saleProducts;
    private MutableLiveData<Product> productDetail;

    public LiveData<List<Product>> getNewProduct(int amount) {
        if (newProducts == null) {
            newProducts = new MutableLiveData<>();
            loadProduct(amount, Common.NEW_PRODUCT);
        }
        return newProducts;
    }

    public LiveData<List<Product>> getSaleProduct(int amount) {
        if (saleProducts == null) {
            saleProducts = new MutableLiveData<>();
            loadProduct(amount, Common.DISCOUNT_PRODUCT);
        }
        return saleProducts;
    }

    public LiveData<Product> getProductDetail(String productID, String userID) {
        if (productDetail == null) {
            productDetail = new MutableLiveData<>();
            loadProductDetail(productID, userID);
        }
        return productDetail;
    }

    public LiveData<Product> getProduct() {
        return productDetail;
    }

    public void setProduct(Product product) {
        productDetail.setValue(product);
    }

    private void loadProduct(int amount, String option) {
        DataClient dataClient = APIClient.getData();
        dataClient.getGroupProduct(amount, option)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<List<Product>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<List<Product>> listAPIResponse) {
                        if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            if (option.equals(Common.NEW_PRODUCT)) {
                                newProducts.setValue(listAPIResponse.getData());
                            } else if (option.equals(Common.DISCOUNT_PRODUCT)) {
                                saleProducts.setValue(listAPIResponse.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void loadProductDetail(String productID, String userID) {
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
                            productDetail.setValue(productAPIResponse.getData());
                            Log.d(TAG, productDetail.toString());
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
}
