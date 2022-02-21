package com.thanguit.tiushop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.databinding.ItemCartBinding;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.util.Common;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<Cart> cartList;

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
            Cart cart = cartList.get(position);

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
