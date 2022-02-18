package com.thanguit.tiushop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.databinding.ItemCollectionBinding;
import com.thanguit.tiushop.model.repository.Collection;
import com.thanguit.tiushop.util.Common;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private Context context;
    private List<Collection> collectionList;

    public CollectionAdapter(Context context, List<Collection> collectionList) {
        this.context = context;
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection = collectionList.get(position);
        if (collection != null) {
            holder.binding.sdvImage.setImageURI(Common.ipUrl + collection.getImage());
            holder.binding.tvCollection.setText(collection.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (collectionList != null) {
            return collectionList.size();
        }
        return 0;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCollectionBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemCollectionBinding.bind(itemView);
        }
    }
}
