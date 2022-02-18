package com.thanguit.tiushop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.thanguit.tiushop.adapter.ProductAdapter;
import com.thanguit.tiushop.adapter.SliderAdapter;
import com.thanguit.tiushop.databinding.FragmentHomeBinding;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.model.repository.Slider;
import com.thanguit.tiushop.presenter.GroupProductPresenter;
import com.thanguit.tiushop.presenter.SliderPresenter;
import com.thanguit.tiushop.presenter.listener.GroupProductListener;
import com.thanguit.tiushop.presenter.listener.SliderListener;
import com.thanguit.tiushop.util.Common;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomeFragment extends Fragment implements GroupProductListener.View, SliderListener.View {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;

    private static final int TIME = 5000;

    private SliderPresenter sliderPresenter;
    private GroupProductPresenter groupProductPresenter;

    private CompositeDisposable compositeDisposable;

    private List<Slider> sliderLists;
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
        sliderLists = new ArrayList<>();
        handler = new Handler();

        sliderPresenter = new SliderPresenter(this);
        groupProductPresenter = new GroupProductPresenter(this);

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
        sliderPresenter.handleSlider(5);
        groupProductPresenter.optionProduct(6, Common.NEW_PRODUCT);
        groupProductPresenter.optionProduct(6, Common.DISCOUNT_PRODUCT);
    }

    private void listeners() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (binding.vpgBanner.getCurrentItem() == sliderLists.size() - 1) {
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

        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sliderPresenter.handleSlider(5);
                groupProductPresenter.optionProduct(6, Common.NEW_PRODUCT);
                groupProductPresenter.optionProduct(6, Common.DISCOUNT_PRODUCT);

                binding.srlRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void newProductSuccess(List<Product> newProducts) {
        if (newProducts != null) {
            binding.rvNew.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            binding.rvNew.setLayoutManager(layoutManager);
            binding.rvNew.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    int action = e.getAction();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                }
            });
            binding.rvNew.setAdapter(new ProductAdapter(getContext(), newProducts));
            binding.pbNew.setVisibility(View.GONE);
            binding.rvNew.setVisibility(View.VISIBLE);
        } else {
            binding.rvNew.setVisibility(View.GONE);
            binding.pbNew.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void saleProductSuccess(List<Product> saleProducts) {
        if (saleProducts != null) {
            binding.rvSale.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            binding.rvSale.setLayoutManager(layoutManager);

            binding.rvSale.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    int action = e.getAction();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
            binding.rvSale.setAdapter(new ProductAdapter(getContext(), saleProducts));
            binding.pbSale.setVisibility(View.GONE);
            binding.rvSale.setVisibility(View.VISIBLE);
        } else {
            binding.rvSale.setVisibility(View.GONE);
            binding.pbSale.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void productFail(String error) {
        binding.rvSale.setVisibility(View.GONE);
        binding.pbSale.setVisibility(View.VISIBLE);
    }

    @Override
    public void sliderSuccess(List<Slider> sliderList) {
        sliderLists = sliderList;
        binding.tabIndicatorBanner.setupWithViewPager(binding.vpgBanner);
        binding.vpgBanner.setAdapter(new SliderAdapter(getContext(), sliderList));
    }

    @Override
    public void sliderFail(String error) {
    }
}