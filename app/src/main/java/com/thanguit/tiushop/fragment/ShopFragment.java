package com.thanguit.tiushop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanguit.tiushop.adapter.SliderAdapter;
import com.thanguit.tiushop.databinding.FragmentShopBinding;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Slider;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShopFragment extends Fragment {
    private FragmentShopBinding binding;

    private SliderAdapter sliderAdapter;

    private CompositeDisposable compositeDisposable;

    private List<Slider> sliderList = new ArrayList<>();
    private Handler handler;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews();
        listeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        compositeDisposable.dispose();
        binding = null;
    }

    private void initializeViews() {
        compositeDisposable = new CompositeDisposable();
        handler = new Handler();

        DataClient dataClient = APIClient.getData();
        dataClient.getSlider(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<List<Slider>>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<List<Slider>> listAPIResponse) {
                        if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            sliderAdapter = new SliderAdapter(getContext(), listAPIResponse.getData());
                            sliderList = listAPIResponse.getData();

                            binding.vpgBanner.setAdapter(sliderAdapter);
                            binding.tabIndicatorBanner.setupWithViewPager(binding.vpgBanner);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void listeners() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (binding.vpgBanner.getCurrentItem() == sliderList.size() - 1) {
                    binding.vpgBanner.setCurrentItem(0);
                } else {
                    binding.vpgBanner.setCurrentItem(binding.vpgBanner.getCurrentItem() + 1);
                }
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }
}