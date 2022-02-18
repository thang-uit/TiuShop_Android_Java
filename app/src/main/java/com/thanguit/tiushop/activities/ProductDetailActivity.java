package com.thanguit.tiushop.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.ImageProductDetailAdapter;
import com.thanguit.tiushop.databinding.ActivityProductDetailBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.presenter.ProductDetailPresenter;
import com.thanguit.tiushop.presenter.listener.ProductDetailListener;
import com.thanguit.tiushop.util.Common;
import com.thanguit.tiushop.util.LoadingDialog;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailListener.View {
    private static final String TAG = "ProductDetailActivity";
    private ActivityProductDetailBinding binding;

    private ProductDetailPresenter productDetailPresenter;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // https://stackoverflow.com/a/56038639

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataLocalManager.init(this);
        productDetailPresenter = new ProductDetailPresenter(this);
        loadingDialog = LoadingDialog.getInstance();

        initializeViews();
        listeners();
    }

    private void initializeViews() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Common.PRODUCT_ID)) {
                loadingDialog.startLoading(this, false);
                productDetailPresenter.handleProduct(intent.getStringExtra(Common.PRODUCT_ID), DataLocalManager.getUserID());
            }
        }

        binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void listeners() {
        binding.fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void productSuccess(Product product) {
        binding.vpgProductImage.setAdapter(new ImageProductDetailAdapter(product.getImage()));
        binding.tabIndicatorImage.setupWithViewPager(binding.vpgProductImage);

        if (product.getIsSale().equals(Common.TRUE)) {
            binding.tvSale.setText(product.getSale());
            binding.tvPrice.setText(product.getPrice());
        } else {
            binding.llSale.setVisibility(View.GONE);
            binding.tvPrice.setVisibility(View.GONE);
        }

        binding.tvProductName.setText(product.getName());
        binding.tvFinalPrice.setText(product.getFinalPrice());

        if (product.isWishList()) {
            binding.fabWishList.setImageResource(R.drawable.ic_wishlist_full);
        } else {
            binding.fabWishList.setImageResource(R.drawable.ic_wishlist);
        }

        binding.tvDescription.setText(product.getDescription());

        loadingDialog.cancelLoading();
    }

    @Override
    public void productFail(String error) {
        loadingDialog.cancelLoading();
    }
}