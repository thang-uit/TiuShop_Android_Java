package com.thanguit.tiushop.activities;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.thanguit.tiushop.adapter.CartAdapter;
import com.thanguit.tiushop.base.SwipeToBackActivity;
import com.thanguit.tiushop.databinding.ActivityCartBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.presenter.CartPresenter;
import com.thanguit.tiushop.presenter.listener.CartListener;
import com.thanguit.tiushop.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends SwipeToBackActivity implements CartAdapter.IOnclickListener{
    private static final String TAG = "CartActivity";
    private ActivityCartBinding activityCartBinding;

//    private CartPresenter cartPresenter;

    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(activityCartBinding.getRoot());

        DataLocalManager.init(this);
//        cartPresenter = new CartPresenter(this);

        initializeViews();
        listeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        cartPresenter.handleCart(DataLocalManager.getUserID());
    }

    private void initializeViews() {
//        cartPresenter.handleCart(DataLocalManager.getUserID());

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCart(DataLocalManager.getUserID()).observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                if (cartList != null) {
                    if (cartList.size() > 0) {
                        activityCartBinding.rvCart.setHasFixedSize(true);
                        activityCartBinding.rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                        activityCartBinding.rvCart.addItemDecoration(new DividerItemDecoration(CartActivity.this, DividerItemDecoration.VERTICAL));
                        cartAdapter = new CartAdapter(CartActivity.this, cartList, CartActivity.this);
                        activityCartBinding.rvCart.setAdapter(cartAdapter);

                        activityCartBinding.lavAnimation.setVisibility(View.GONE);
                        activityCartBinding.rvCart.setVisibility(View.VISIBLE);
                    } else {
                        activityCartBinding.rvCart.setVisibility(View.GONE);
                        activityCartBinding.lavAnimation.setVisibility(View.VISIBLE);
                    }
                } else {
                    activityCartBinding.rvCart.setVisibility(View.GONE);
                    activityCartBinding.lavAnimation.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void listeners() {
        activityCartBinding.tbCart.setNavigationOnClickListener(view -> finish());

        activityCartBinding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                cartPresenter.handleCart(DataLocalManager.getUserID());
                activityCartBinding.srlRefresh.setRefreshing(false);
            }
        });

//        activityCartBinding.cbAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
    }

    @Override
    public void onClickCheck(boolean status, Cart cart) {

    }

    @Override
    public void onClickDelete(Cart cart) {
        cartViewModel.deleteCart(cart);
    }

    @Override
    public void onClickDecrease(Cart cart) {

    }

    @Override
    public void onClickIncrease(Cart cart) {

    }

//    @Override
//    public void cartSuccess(List<Cart> cartList) {
//        if (cartList != null) {
//            if (cartList.size() > 0) {
//                activityCartBinding.rvCart.setHasFixedSize(true);
//                activityCartBinding.rvCart.setLayoutManager(new LinearLayoutManager(this));
//                activityCartBinding.rvCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//                cartAdapter = new CartAdapter(this, cartList, activityCartBinding.tvTotalPrice, false);
//                activityCartBinding.rvCart.setAdapter(cartAdapter);
//
//                activityCartBinding.lavAnimation.setVisibility(View.GONE);
//                activityCartBinding.rvCart.setVisibility(View.VISIBLE);
//            } else {
//                activityCartBinding.rvCart.setVisibility(View.GONE);
//                activityCartBinding.lavAnimation.setVisibility(View.VISIBLE);
//            }
//        } else {
//            activityCartBinding.rvCart.setVisibility(View.GONE);
//            activityCartBinding.lavAnimation.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void cartFail(String error) {
//        activityCartBinding.rvCart.setVisibility(View.GONE);
//        activityCartBinding.lavAnimation.setVisibility(View.VISIBLE);
//    }
}