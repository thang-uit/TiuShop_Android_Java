package com.thanguit.tiushop.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.thanguit.tiushop.fragment.CollectionFragment;
import com.thanguit.tiushop.fragment.MeFragment;
import com.thanguit.tiushop.fragment.ShopFragment;

public class FragmentPagerAdapter extends BaseFragmentPagerAdapter {
    public FragmentPagerAdapter(FragmentManager mFragmentManager) {
        super(mFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1: {
                return new CollectionFragment();
            }

            case 2: {
                return new MeFragment();
            }

            default: {
                return new ShopFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
