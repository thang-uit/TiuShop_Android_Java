package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.thanguit.tiushop.adapter.ProductAdapter;
import com.thanguit.tiushop.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";
    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeViews();
        listeners();
    }

    private void initializeViews() {
        binding.rvCart.setHasFixedSize(true);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
//        binding.rvCart.setAdapter(new ProductAdapter(this, cartList));
    }

    private void listeners() {
        binding.tbCart.setNavigationOnClickListener(view -> finish());
    }
}