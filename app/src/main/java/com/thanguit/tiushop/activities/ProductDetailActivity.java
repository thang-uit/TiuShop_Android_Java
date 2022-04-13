package com.thanguit.tiushop.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.base.MyToast;
import com.thanguit.tiushop.base.SwipeToBackActivity;
import com.thanguit.tiushop.databinding.ActivityProductDetailBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.util.Common;
import com.thanguit.tiushop.util.LoadingDialog;
import com.thanguit.tiushop.viewmodel.CartViewModel;
import com.thanguit.tiushop.viewmodel.ShopViewModel;

import java.util.List;

public class ProductDetailActivity extends SwipeToBackActivity {
    private static final String TAG = "ProductDetailActivity";
    private ActivityProductDetailBinding activityProductDetailBinding;

    private LoadingDialog loadingDialog;

    private int amount = 0;

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

        activityProductDetailBinding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(activityProductDetailBinding.getRoot());

        DataLocalManager.init(this);
//        productDetailPresenter = new ProductDetailPresenter(this);
        loadingDialog = LoadingDialog.getInstance();

        initializeViews();
        listeners();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initializeViews() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Common.PRODUCT_ID)) {
                loadingDialog.startLoading(this, false);
                String productID = intent.getStringExtra(Common.PRODUCT_ID);

                //    private ProductDetailPresenter productDetailPresenter;
                ShopViewModel shopViewModel = new ViewModelProvider(ProductDetailActivity.this).get(ShopViewModel.class);
                CartViewModel cartViewModel = new ViewModelProvider(ProductDetailActivity.this).get(CartViewModel.class);

                cartViewModel.getCart(DataLocalManager.getUserID()).observe(ProductDetailActivity.this, new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(List<Cart> cartList) {
                        if(cartList != null) {
                            amount = cartList.size();
                        }
                    }
                });

                shopViewModel.getProductDetail(productID, DataLocalManager.getUserID()).observe(this, new Observer<Product>() {
                    @Override
                    public void onChanged(Product product) {
                        activityProductDetailBinding.setShopViewModel(shopViewModel);
                        loadingDialog.cancelLoading();
                    }
                });

                shopViewModel.handleQuantity().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        activityProductDetailBinding.tvQuantity.setText(String.valueOf(integer));
                    }
                });

                shopViewModel.handleStatusWishList().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean status) {
                        if(status != null) {
                            if(status) {
                                activityProductDetailBinding.fabWishList.setImageResource(R.drawable.ic_wishlist_full);
                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast2), Toast.LENGTH_LONG).show();
                            } else {
                                activityProductDetailBinding.fabWishList.setImageResource(R.drawable.ic_wishlist);
                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast3), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "Status Add to wishlist null");
                        }
                    }
                });

                shopViewModel.handleStatusAddToCart().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean status) {
                        if(status != null) {
                            if(status) {

                                CartViewModel cartViewModel = new ViewModelProvider(ProductDetailActivity.this).get(CartViewModel.class);
                                cartViewModel.getCart(DataLocalManager.getUserID()).observe(ProductDetailActivity.this, new Observer<List<Cart>>() {
                                    @Override
                                    public void onChanged(List<Cart> cartList) {
                                        if(cartList != null) {
                                            amount = cartList.size();
                                        }
                                    }
                                });

                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast4), Toast.LENGTH_LONG).show();
                            } else {
                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "Status Add to cart null");
                        }
                    }
                });

//                productDetailPresenter.handleProduct(intent.getStringExtra(Common.PRODUCT_ID), DataLocalManager.getUserID());
            }
        }

//        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        activityProductDetailBinding.snSize.setAdapter(arrayAdapter);

        activityProductDetailBinding.tvAmount.setText(String.valueOf(amount));
        activityProductDetailBinding.tvPrice.setPaintFlags(activityProductDetailBinding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void listeners() {
        activityProductDetailBinding.fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activityProductDetailBinding.fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
            }
        });

//        binding.fabWishList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadingDialog.startLoading(ProductDetailActivity.this, false);
//
//                HashMap<String, Object> jsonBody = new HashMap<>();
//                jsonBody.put("userID", DataLocalManager.getUserID());
//                jsonBody.put("productID", productID);
//
//                DataClient dataClient = APIClient.getData();
//                dataClient.handleWishList(Common.getRequestBody(jsonBody))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<APIResponse<Cart>>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//                            }
//
//                            @Override
//                            public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
//                                if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
//                                    if (cartAPIResponse.getMessage().equals("1")) {
//                                        binding.fabWishList.setImageResource(R.drawable.ic_wishlist_full);
//                                        loadingDialog.cancelLoading();
//                                        MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast2), Toast.LENGTH_LONG).show();
//                                    } else if (cartAPIResponse.getMessage().equals("3")) {
//                                        binding.fabWishList.setImageResource(R.drawable.ic_wishlist);
//                                        loadingDialog.cancelLoading();
//                                        MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast3), Toast.LENGTH_LONG).show();
//                                    } else {
//                                        loadingDialog.cancelLoading();
//                                        MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
//                                    }
//                                } else {
//                                    loadingDialog.cancelLoading();
//                                    MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                                Log.d(TAG, e.getMessage());
//                                loadingDialog.cancelLoading();
//                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onComplete() {
//                            }
//                        });
//            }
//        });

//        activityProductDetailBinding.fabDecrease.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Integer.parseInt(activityProductDetailBinding.tvQuantity.getText().toString()) == 1) {
//                    quantity = 1;
//                } else {
//                    quantity = quantity - 1;
//                }
//
//                activityProductDetailBinding.tvQuantity.setText(String.valueOf(quantity));
//            }
//        });
//
//        activityProductDetailBinding.fabIncrease.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Integer.parseInt(activityProductDetailBinding.tvQuantity.getText().toString()) == 10) {
//                    quantity = 10;
//                } else {
//                    quantity = quantity + 1;
//                }
//
//                activityProductDetailBinding.tvQuantity.setText(String.valueOf(quantity));
//            }
//        });

//        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadingDialog.startLoading(ProductDetailActivity.this, false);
//
//                HashMap<String, Object> jsonBody = new HashMap<>();
//                jsonBody.put("userID", DataLocalManager.getUserID());
//                jsonBody.put("productID", productID);
//                jsonBody.put("size", binding.snSize.getSelectedItem().toString());
//                jsonBody.put("quantity", Integer.parseInt(binding.tvQuantity.getText().toString()));
//
//                DataClient dataClient = APIClient.getData();
//                dataClient.addToCart(Common.getRequestBody(jsonBody))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<APIResponse<Cart>>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//                            }
//
//                            @Override
//                            public void onNext(@NonNull APIResponse<Cart> cartAPIResponse) {
//                                if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
//
//                                    HashMap<String, Object> jsonBody1 = new HashMap<>();
//                                    jsonBody1.put("userID", DataLocalManager.getUserID());
//
//                                    DataClient dataClient = APIClient.getData();
//                                    dataClient.getCart(Common.getRequestBody(jsonBody1))
//                                            .subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe(new Observer<APIResponse<List<Cart>>>() {
//                                                @Override
//                                                public void onSubscribe(@NonNull Disposable d) {
//                                                }
//
//                                                @Override
//                                                public void onNext(@NonNull APIResponse<List<Cart>> listAPIResponse) {
//                                                    if (listAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
//                                                        EventBus.getDefault().post(new CartModel(listAPIResponse.getData()));
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onError(@NonNull Throwable e) {
//                                                    Log.d(TAG, e.getMessage());
//                                                }
//
//                                                @Override
//                                                public void onComplete() {
//                                                }
//                                            });
//
//                                    loadingDialog.cancelLoading();
//                                    MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.SUCCESS, getString(R.string.toast4), Toast.LENGTH_LONG).show();
//                                } else {
//                                    loadingDialog.cancelLoading();
//                                    MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                                Log.d(TAG, e.getMessage());
//                                loadingDialog.cancelLoading();
//                                MyToast.makeText(ProductDetailActivity.this, MyToast.TYPE.ERROR, getString(R.string.tvError0), Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onComplete() {
//                            }
//                        });
//            }
//        });
    }

//    @Override
//    public void productSuccess(Product product) {
//        binding.vpgProductImage.setAdapter(new ImageProductDetailAdapter(product.getImage()));
//        binding.tabIndicatorImage.setupWithViewPager(binding.vpgProductImage);
//
//        if (product.getIsSale().equals(Common.TRUE)) {
//            binding.tvSale.setText(product.getSale());
//            binding.tvPrice.setText(product.getPrice());
//        } else {
//            binding.llSale.setVisibility(View.GONE);
//            binding.tvPrice.setVisibility(View.GONE);
//        }
//
//        binding.tvProductName.setText(product.getName());
//        binding.tvFinalPrice.setText(product.getFinalPrice());
//
//        if (product.isWishList()) {
//            binding.fabWishList.setImageResource(R.drawable.ic_wishlist_full);
//        } else {
//            binding.fabWishList.setImageResource(R.drawable.ic_wishlist);
//        }
//
//        binding.tvDescription.setText(product.getDescription());
//
//        loadingDialog.cancelLoading();
//    }
//
//    @Override
//    public void productFail(String error) {
//        loadingDialog.cancelLoading();
//    }
}