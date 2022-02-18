package com.thanguit.tiushop.presenter;

import android.util.Log;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Collection;
import com.thanguit.tiushop.presenter.listener.CollectionListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CollectionPresenter implements CollectionListener.Presenter {
    private static final String TAG = "CollectionPresenter";
    private CollectionListener.View view;

    public CollectionPresenter(CollectionListener.View view) {
        this.view = view;
    }

    @Override
    public void handleCollection() {
        DataClient dataClient = APIClient.getData();
        dataClient.getCollections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<List<Collection>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull APIResponse<List<Collection>> listAPIResponse) {
                        if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            view.collectionSuccess(listAPIResponse.getData());
                        } else {
                            view.collectionFail(listAPIResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                        view.collectionFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}
