package com.thanguit.tiushop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thanguit.tiushop.fragment.CollectionFragment;
import com.thanguit.tiushop.fragment.HomeFragment;
import com.thanguit.tiushop.fragment.MeFragment;;

public class FragmentPagerAdapter extends FragmentStateAdapter {
    public FragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new HomeFragment();
            }

            case 1: {
                return new CollectionFragment();
            }

            case 2: {
                return new MeFragment();
            }

            default: {
                return new HomeFragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
