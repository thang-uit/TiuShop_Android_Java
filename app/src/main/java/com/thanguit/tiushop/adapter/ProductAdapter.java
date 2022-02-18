package com.thanguit.tiushop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.activities.ProductDetailActivity;
import com.thanguit.tiushop.databinding.ItemProductBinding;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.util.Common;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        if (product != null) {
            holder.binding.sdvImage.setImageURI(Common.ipUrl + product.getImage().get(0));
            holder.binding.tvName.setText(product.getName().trim());

            if (product.getIsSale().equals(Common.TRUE)) {
                holder.binding.tvSale.setText(product.getSale());
                holder.binding.tvPrice.setText(product.getPrice().trim());
            } else {
                holder.binding.llSale.setVisibility(View.GONE);
                holder.binding.tvPrice.setVisibility(View.GONE);
            }
            holder.binding.tvFinalPrice.setText(product.getFinalPrice().trim());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 0, 0);
            holder.binding.tvFinalPrice.setLayoutParams(params);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Common.PRODUCT_ID, productList.get(holder.getLayoutPosition()).getProductID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemProductBinding.bind(itemView);
            binding.tvName.setSelected(true);
            binding.tvPrice.setSelected(true);
            binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.tvFinalPrice.setSelected(true);
        }
    }
}
