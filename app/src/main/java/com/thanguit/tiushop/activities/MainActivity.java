package com.thanguit.tiushop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.FragmentPagerAdapter;
import com.thanguit.tiushop.databinding.ActivityMainBinding;
import com.thanguit.tiushop.model.repository.CartModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CartModel cartModel) {
        if (cartModel != null) {
            binding.tvAmount.setText(String.valueOf(cartModel.getCartList().size()));
        } else {
            binding.tvAmount.setText("2");
        }
    }

    private void initializeViews() {
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.vpgMain.setCurrentItem(0);
        binding.vpgMain.setAdapter(fragmentPagerAdapter);
    }

    private void listeners() {
        binding.vpgMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

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
                super.onPageScrollStateChanged(state);
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

        binding.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }
}