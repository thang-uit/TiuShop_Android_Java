package com.thanguit.tiushop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.util.Common;

import java.util.List;

public class ImageProductDetailAdapter extends PagerAdapter {
    private List<String> images;

    public ImageProductDetailAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_image_list, null);

        SimpleDraweeView simpleDraweeView = view.findViewById(R.id.sdvImage);
        simpleDraweeView.setImageURI(Common.ipUrl + images.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (images != null) {
            return images.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
