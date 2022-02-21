package com.thanguit.tiushop.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.thanguit.tiushop.adapter.CartAdapter;
import com.thanguit.tiushop.base.SwipeToBackActivity;
import com.thanguit.tiushop.databinding.ActivityCartBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.presenter.CartPresenter;
import com.thanguit.tiushop.presenter.listener.CartListener;
import com.thanguit.tiushop.util.Common;

import java.util.List;

public class CartActivity extends SwipeToBackActivity implements CartListener.View {
    private static final String TAG = "CartActivity";
    private ActivityCartBinding binding;

    private CartPresenter cartPresenter;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataLocalManager.init(this);
        cartPresenter = new CartPresenter(this);

        initializeViews();
        listeners();
    }

    private void initializeViews() {
        cartPresenter.handleCart(DataLocalManager.getUserID());
    }

    private void listeners() {
        binding.tbCart.setNavigationOnClickListener(view -> finish());

        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cartPresenter.handleCart(DataLocalManager.getUserID());
                binding.srlRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void cartSuccess(List<Cart> cartList) {
        if (cartList != null) {
            if (cartList.size() > 0) {
                binding.rvCart.setHasFixedSize(true);
                binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
                binding.rvCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                        int position = viewHolder.getAdapterPosition();
//                        arrayList.remove(position);
//                        adapter.notifyDataSetChanged();

                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(binding.rvCart);

                cartAdapter = new CartAdapter(this, cartList);
                binding.rvCart.setAdapter(cartAdapter);

                binding.lavAnimation.setVisibility(View.GONE);
                binding.rvCart.setVisibility(View.VISIBLE);
            } else {
                binding.rvCart.setVisibility(View.GONE);
                binding.lavAnimation.setVisibility(View.VISIBLE);
            }
        } else {
            binding.rvCart.setVisibility(View.GONE);
            binding.lavAnimation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void cartFail(String error) {
        binding.rvCart.setVisibility(View.GONE);
        binding.lavAnimation.setVisibility(View.VISIBLE);
    }
}