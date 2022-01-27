package com.thanguit.tiushop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.model.Intro;

import java.util.List;

public class IntroAdapter extends PagerAdapter {
    private Context context;
    private final List<Intro> introList;

    public IntroAdapter(Context context, List<Intro> introList) {
        this.context = context;
        this.introList = introList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_intro, null);

        LottieAnimationView lavAnimation = view.findViewById(R.id.lavAnimation);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvHint = view.findViewById(R.id.tvHint);

        lavAnimation.setAnimation(introList.get(position).getImage());
        tvTitle.setText(introList.get(position).getTitle());
        tvHint.setText(introList.get(position).getHint());

        if (position == introList.size() - 1) {
            tvTitle.setVisibility(View.GONE);
            tvHint.setVisibility(View.GONE);
        } else {
        }

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (introList != null) {
            return introList.size();
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
