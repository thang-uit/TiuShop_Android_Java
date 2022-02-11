package com.thanguit.tiushop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.model.repository.Slider;
import com.thanguit.tiushop.util.Common;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private static final String TAG = "SliderAdapter";

    private final Context context;
    private final List<Slider> sliderList;

    public SliderAdapter(Context context, List<Slider> mSliderList) {
        this.context = context;
        this.sliderList = mSliderList;
    }

//                Intent intent = new Intent(context, WebActivity.class);
//                intent.putExtra(Common.INTENT_KEY_ARTICLE.trim(), mArticle.getData().get(holder.getLayoutPosition()).getLink());
//                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_banner, null);

        SimpleDraweeView simpleDraweeView = view.findViewById(R.id.sdvBanner);

        String img = Common.ipUrl + sliderList.get(position).getSliderImg();
        simpleDraweeView.setImageURI(img);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (sliderList != null) {
            return sliderList.size();
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
