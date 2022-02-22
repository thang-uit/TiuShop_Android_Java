package com.thanguit.tiushop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.activities.ProductDetailActivity;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.base.MyToast;
import com.thanguit.tiushop.databinding.ItemCartBinding;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;
import com.thanguit.tiushop.util.LoadingDialog;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private static final String TAG = "CartAdapter";

    private int quantity = 0;

    private Context context;
    private List<Cart> cartList;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cartList != null) {
            LoadingDialog loadingDialog = LoadingDialog.getInstance();

            Cart cart = cartList.get(position);
            viewBinderHelper.bind(holder.binding.srlSwipeToDelete, cart.getCartID());

            holder.binding.sdvProductImage.setImageURI(Common.ipUrl + cart.getImage().get(0));
            holder.binding.tvProductName.setText(cart.getName());
            holder.binding.tvSize.setText(cart.getSize());
            holder.binding.tvQuantity.setText(String.valueOf(cart.getQuantity()));
            holder.binding.tvProductFinalPrice.setText(cart.getFinalPrice());

            if (cart.getIsSale().equals(Common.TRUE)) {
                holder.binding.tvProductPrice.setText(cart.getPrice());
                holder.binding.tvSale.setText(cart.getSale());
            } else {
                holder.binding.tvProductPrice.setVisibility(View.GONE);
                holder.binding.llSale.setVisibility(View.GONE);
            }

            holder.binding.fabDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity = Integer.parseInt(holder.binding.tvQuantity.getText().toString());

                    if (Integer.parseInt(holder.binding.tvQuantity.getText().toString()) == 1) {
                        quantity = 1;
                    } else {
                        quantity = quantity - 1;

                        loadingDialog.startLoading(context, false);

                        HashMap<String, Object> jsonBody = new HashMap<>();
                        jsonBody.put("cartID", cartList.get(holder.getLayoutPosition()).getCartID());
                        jsonBody.put("quantity", quantity);

                        DataClient dataClient = APIClient.getData();
                        dataClient.updateCart(Common.getRequestBody(jsonBody))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<APIResponse<Cart>>() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                    }

                                    @Override
                                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<Cart> cartAPIResponse) {
                                        if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                                            holder.binding.tvQuantity.setText(String.valueOf(quantity));
                                        }
                                        loadingDialog.cancelLoading();
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        Log.d(TAG, e.getMessage());

                                        loadingDialog.cancelLoading();
                                        MyToast.makeText(context, MyToast.TYPE.ERROR, context.getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });
                    }
                }
            });

            holder.binding.fabIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity = Integer.parseInt(holder.binding.tvQuantity.getText().toString());

                    if (Integer.parseInt(holder.binding.tvQuantity.getText().toString()) == 10) {
                        quantity = 10;
                    } else {
                        quantity = quantity + 1;

                        loadingDialog.startLoading(context, false);

                        HashMap<String, Object> jsonBody = new HashMap<>();
                        jsonBody.put("cartID", cartList.get(holder.getLayoutPosition()).getCartID());
                        jsonBody.put("quantity", quantity);

                        DataClient dataClient = APIClient.getData();
                        dataClient.updateCart(Common.getRequestBody(jsonBody))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<APIResponse<Cart>>() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                    }

                                    @Override
                                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<Cart> cartAPIResponse) {
                                        if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                                            holder.binding.tvQuantity.setText(String.valueOf(quantity));
                                        }
                                        loadingDialog.cancelLoading();
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        Log.d(TAG, e.getMessage());

                                        loadingDialog.cancelLoading();
                                        MyToast.makeText(context, MyToast.TYPE.ERROR, context.getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });
                    }
                }
            });

            holder.binding.flProductInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Common.PRODUCT_ID, cartList.get(holder.getLayoutPosition()).getProductID());
                    context.startActivity(intent);
                }
            });

            holder.binding.flDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.tvAlertTitle))
                            .setMessage(context.getString(R.string.tvAlertMessage1))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .setPositiveButton(context.getString(R.string.tvAlertButton3), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loadingDialog.startLoading(context, false);

                                    HashMap<String, Object> jsonBody = new HashMap<>();
                                    jsonBody.put("cartID", cartList.get(holder.getLayoutPosition()).getCartID());

                                    DataClient dataClient = APIClient.getData();
                                    dataClient.deleteCart(Common.getRequestBody(jsonBody))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<APIResponse<Cart>>() {
                                                @Override
                                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                                }

                                                @Override
                                                public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<Cart> cartAPIResponse) {
                                                    if (cartAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                                                        dialogInterface.dismiss();
                                                        loadingDialog.cancelLoading();

                                                        cartList.remove(holder.getLayoutPosition());
                                                        notifyItemRemoved(holder.getLayoutPosition());
                                                    } else {
                                                        dialogInterface.dismiss();
                                                        loadingDialog.cancelLoading();
                                                        MyToast.makeText(context, MyToast.TYPE.ERROR, context.getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                                    Log.d(TAG, e.getMessage());

                                                    dialogInterface.dismiss();
                                                    loadingDialog.cancelLoading();
                                                    MyToast.makeText(context, MyToast.TYPE.ERROR, context.getString(R.string.tvError0), Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onComplete() {
                                                }
                                            });
                                }
                            })
                            .setNegativeButton(context.getString(R.string.tvAlertButton2), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemCartBinding.bind(itemView);

            binding.tvProductName.setSelected(true);
            binding.tvProductPrice.setPaintFlags(binding.tvProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
