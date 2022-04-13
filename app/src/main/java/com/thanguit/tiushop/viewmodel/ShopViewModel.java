package com.thanguit.tiushop.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.thanguit.tiushop.adapter.ImageProductDetailAdapter;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Cart;
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

    private MutableLiveData<Integer> quantity;

    private MutableLiveData<Boolean> statusWishList;
    private MutableLiveData<Boolean> statusAddToCart;

    private String size = "";

    public LiveData<Integer> handleQuantity() {
        if(quantity == null) {
            quantity = new MutableLiveData<>();
            quantity.setValue(1);
        }
        return quantity;
    }

    public LiveData<Boolean> handleStatusAddToCart() {
        if(statusAddToCart == null) {
            statusAddToCart = new MutableLiveData<>();
            statusAddToCart.setValue(null);
        }
        return statusAddToCart;
    }

    public LiveData<Boolean> handleStatusWishList() {
        if(statusWishList == null) {
            statusWishList = new MutableLiveData<>();
            statusWishList.setValue(null);
        }
        return statusWishList;
    }

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
                            Log.d(TAG, productAPIResponse.getData().getName());
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

    @BindingAdapter("adapter")
    public static void setViewPagerFragments(ViewPager viewPager, List<String> images){
        viewPager.setAdapter(new ImageProductDetailAdapter(images));
    }

    @BindingAdapter("setUpWithViewpager")
    public static void setUpWithViewpager(final TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) { // https://stackoverflow.com/a/50348218
        //pos                                 get selected item position
        //view.getText()                      get label of selected item
        //parent.getAdapter().getItem(pos)    get item by pos
        //parent.getAdapter().getCount()      get item count
        //parent.getCount()                   get item count
        //parent.getSelectedItem()            get selected item
        //and other...

        size = parent.getSelectedItem().toString();
        Log.d(TAG, size);
    }

    public void addToWishList(Product product) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("userID", DataLocalManager.getUserID());
        jsonBody.put("productID", product.getProductID());

        DataClient dataClient = APIClient.getData();
        dataClient.handleWishList(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
                        if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            if (cartAPIResponse.getMessage().equals("1")) {
                                statusWishList.setValue(true);
                            } else if (cartAPIResponse.getMessage().equals("3")) {
                                statusWishList.setValue(false);
                            }
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

    public void addToCart(Product product) {
        HashMap<String, Object> jsonBody = new HashMap<>();
                jsonBody.put("userID", DataLocalManager.getUserID());
                jsonBody.put("productID", product.getProductID());
                jsonBody.put("size", size);
                jsonBody.put("quantity", quantity.getValue());

        DataClient dataClient = APIClient.getData();
        dataClient.addToCart(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
                        if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            statusAddToCart.setValue(true);
                        } else {
                            statusAddToCart.setValue(false);
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

    public void decrease() {
        if(quantity.getValue() != null) {
            if (quantity.getValue() == 1) {
                quantity.setValue(1);
            } else {
                quantity.setValue(quantity.getValue() - 1);
            }
        }
    }

    public void increase() {
        if(quantity.getValue() != null) {
            if (quantity.getValue() == 10) {
                quantity.setValue(10);
            } else {
                quantity.setValue(quantity.getValue() + 1);
            }
        }
    }
}
