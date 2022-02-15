package com.thanguit.tiushop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.SliderAdapter;
import com.thanguit.tiushop.databinding.FragmentHomeBinding;
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

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;

    private static final int TIME = 5000;


    private SliderAdapter sliderAdapter;

    private CompositeDisposable compositeDisposable;

    private List<Slider> sliderList;
    private Handler handler;
    private Runnable runnable;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compositeDisposable = new CompositeDisposable();
        sliderList = new ArrayList<>();
        handler = new Handler();

        initializeViews();
        listeners();
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();

        handler.postDelayed(runnable, TIME);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        compositeDisposable.dispose();
        binding = null;
    }

    private void initializeViews() {
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

                            binding.tabIndicatorBanner.setupWithViewPager(binding.vpgBanner);
                            binding.vpgBanner.setAdapter(sliderAdapter);
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

    private void listeners() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (binding.vpgBanner.getCurrentItem() == sliderList.size() - 1) {
                    binding.vpgBanner.setCurrentItem(0);
                } else {
                    binding.vpgBanner.setCurrentItem(binding.vpgBanner.getCurrentItem() + 1);
                }
            }
        };

        handler.postDelayed(runnable, TIME);

        binding.vpgBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, TIME);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}