package com.thanguit.tiushop.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.ImageProductDetailAdapter;
import com.thanguit.tiushop.base.MyToast;
import com.thanguit.tiushop.databinding.ActivityProductDetailBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.presenter.ProductDetailPresenter;
import com.thanguit.tiushop.presenter.listener.ProductDetailListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;
import com.thanguit.tiushop.util.LoadingDialog;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailListener.View {
    private static final String TAG = "ProductDetailActivity";
    private static final String DEBUG_TAG = "Gestures";
    private static final String GTAG = "GESTURE";
    private ActivityProductDetailBinding binding;

    private ProductDetailPresenter productDetailPresenter;

    private LoadingDialog loadingDialog;

    private String productID = "";
    private int quantity = 1;

    private String[] size = {"M", "L", "XL", "XXL", "XXL"};

    private GestureDetector gestureDetector;

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

        gestureDetector = new GestureDetector(this, new SwipeDetector());

        initializeViews();
        listeners();
    }

    private void initializeViews() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Common.PRODUCT_ID)) {
                loadingDialog.startLoading(this, false);
                productID = intent.getStringExtra(Common.PRODUCT_ID);
                productDetailPresenter.handleProduct(intent.getStringExtra(Common.PRODUCT_ID), DataLocalManager.getUserID());
            }
        }

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.snSize.setAdapter(arrayAdapter);

        binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void listeners() {
        binding.fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
            }
        });

        binding.fabWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoading(ProductDetailActivity.this, false);

                HashMap<String, Object> jsonBody = new HashMap<>();
                jsonBody.put("userID", DataLocalManager.getUserID());
                jsonBody.put("productID", productID);

                DataClient dataClient = APIClient.getData();
                dataClient.handleWishList(Common.getRequestBody(jsonBody))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<APIResponse<Cart>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
                                if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                                    if (cartAPIResponse.getMessage().equals("1")) {
                                        binding.fabWishList.setImageResource(R.drawable.ic_wishlist_full);
                                        loadingDialog.cancelLoading();
                                        MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast2), Toast.LENGTH_LONG).show();
                                    } else if (cartAPIResponse.getMessage().equals("3")) {
                                        binding.fabWishList.setImageResource(R.drawable.ic_wishlist);
                                        loadingDialog.cancelLoading();
                                        MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast3), Toast.LENGTH_LONG).show();
                                    } else {
                                        loadingDialog.cancelLoading();
                                        MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    loadingDialog.cancelLoading();
                                    MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, e.getMessage());
                                loadingDialog.cancelLoading();
                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });

        binding.fabDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(binding.tvQuantity.getText().toString()) == 1) {
                    quantity = 1;
                } else {
                    quantity = quantity - 1;
                }

                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.fabIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(binding.tvQuantity.getText().toString()) == 10) {
                    quantity = 10;
                } else {
                    quantity = quantity + 1;
                }

                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoading(ProductDetailActivity.this, false);

                HashMap<String, Object> jsonBody = new HashMap<>();
                jsonBody.put("userID", DataLocalManager.getUserID());
                jsonBody.put("productID", productID);
                jsonBody.put("size", binding.snSize.getSelectedItem().toString());
                jsonBody.put("quantity", Integer.parseInt(binding.tvQuantity.getText().toString()));

                DataClient dataClient = APIClient.getData();
                dataClient.addToCart(Common.getRequestBody(jsonBody))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<APIResponse<Cart>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
                                if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                                    loadingDialog.cancelLoading();
                                    MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast4), Toast.LENGTH_LONG).show();
                                } else {
                                    loadingDialog.cancelLoading();
                                    MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, e.getMessage());
                                loadingDialog.cancelLoading();
                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TouchEvent dispatcher.
        if (gestureDetector != null) {
            if (gestureDetector.onTouchEvent(ev))
                // If the gestureDetector handles the event, a swipe has been
                // executed and no more needs to be done.
                return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener { // https://stackoverflow.com/a/38442055
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + e1.getX() + "|" + e2.getX() + "|");

            if ((e2.getX() - e1.getX()) > 0 && Math.abs(e2.getX() - e1.getX()) > 600) {
                finish();
                return true;
            } else {
                return false;
            }


//            // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
//            // then dismiss the swipe.
//            if (Math.abs(e1.getY() - e2.getY()) > 250)
//                return false;
//
//            // Swipe from left to right.
//            // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE)
//            // and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
//            if (e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200) {
//                finish();
//                return true;
//            }
        }
    }

}