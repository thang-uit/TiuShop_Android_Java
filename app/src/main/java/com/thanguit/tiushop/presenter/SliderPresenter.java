package com.thanguit.tiushop.presenter;

import android.util.Log;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Slider;
import com.thanguit.tiushop.presenter.listener.SliderListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SliderPresenter implements SliderListener.Presenter {
    private static final String TAG = "SliderPresenter";
    private SliderListener.View view;

    public SliderPresenter(SliderListener.View view) {
        this.view = view;
    }

    @Override
    public void handleSlider(int amount) {
        DataClient dataClient = APIClient.getData();
        dataClient.getSlider(amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<List<Slider>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<List<Slider>> listAPIResponse) {
                        if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            view.sliderSuccess(listAPIResponse.getData());
                        } else {
                            view.sliderFail(MyApplication.getResource().getString(R.string.tvError9));
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
}
