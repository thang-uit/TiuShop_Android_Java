package com.thanguit.tiushop.presenter;

import android.util.Log;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.presenter.listener.GroupProductListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GroupProductPresenter implements GroupProductListener.Presenter {
    private static final String TAG = "GPPresenter";
    private GroupProductListener.View view;

    public GroupProductPresenter(GroupProductListener.View view) {
        this.view = view;
    }

    @Override
    public void optionProduct(int amount, String option) {

        DataClient dataClient = APIClient.getData();
        dataClient.getGroupProduct(amount, option)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<List<Product>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull APIResponse<List<Product>> listAPIResponse) {

                        if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            if (option.equals(Common.NEW_PRODUCT)) {
                                view.newProductSuccess(listAPIResponse.getData());
                            } else if (option.equals(Common.DISCOUNT_PRODUCT)) {
                                view.saleProductSuccess(listAPIResponse.getData());
                            }
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
