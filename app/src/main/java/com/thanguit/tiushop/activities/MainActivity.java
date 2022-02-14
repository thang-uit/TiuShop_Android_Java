package com.thanguit.tiushop.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationBarView;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.base.FragmentPagerAdapter;
import com.thanguit.tiushop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeViews();
        listeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initializeViews() {
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        binding.vpgMain.setCurrentItem(0);
        binding.vpgMain.setAdapter(fragmentPagerAdapter);
    }

    private void listeners() {
        binding.vpgMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        binding.bnvNav.getMenu().findItem(R.id.menuActionShop).setChecked(true);
                        break;
                    }
                    case 1: {
                        binding.bnvNav.getMenu().findItem(R.id.menuActionCollection).setChecked(true);
                        break;
                    }
                    case 2: {
                        binding.bnvNav.getMenu().findItem(R.id.menuActionUser).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        binding.bnvNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuActionShop) {
                    binding.vpgMain.setCurrentItem(0);
                } else if (item.getItemId() == R.id.menuActionCollection) {
                    binding.vpgMain.setCurrentItem(1);
                } else if (item.getItemId() == R.id.menuActionUser) {
                    binding.vpgMain.setCurrentItem(2);
                }
                return false;
            }
        });
    }
}