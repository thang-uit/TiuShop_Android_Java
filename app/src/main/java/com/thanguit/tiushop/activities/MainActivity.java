package com.thanguit.tiushop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.FragmentPagerAdapter;
import com.thanguit.tiushop.databinding.ActivityMainBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.viewmodel.CartViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        initializeViews();
        listeners();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCart(DataLocalManager.getUserID()).observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                if(cartList != null) {
                    if(cartList.size() >= 100) {
                        activityMainBinding.tvAmount.setText(getString(R.string.tvAmountOverSize));
                    } else {
                        activityMainBinding.tvAmount.setText(String.valueOf(cartList.size()));
                    }
                } else {
                    activityMainBinding.tvAmount.setText("0");
                }
            }
        });
    }

    private void initializeViews() {
        DataLocalManager.init(this);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        activityMainBinding.vpgMain.setCurrentItem(0);
        activityMainBinding.vpgMain.setAdapter(fragmentPagerAdapter);

    }

    private void listeners() {
        activityMainBinding.vpgMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0: {
                        activityMainBinding.bnvNav.getMenu().findItem(R.id.menuActionShop).setChecked(true);
                        break;
                    }
                    case 1: {
                        activityMainBinding.bnvNav.getMenu().findItem(R.id.menuActionCollection).setChecked(true);
                        break;
                    }
                    case 2: {
                        activityMainBinding.bnvNav.getMenu().findItem(R.id.menuActionUser).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        activityMainBinding.bnvNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuActionShop) {
                    activityMainBinding.vpgMain.setCurrentItem(0);
                } else if (item.getItemId() == R.id.menuActionCollection) {
                    activityMainBinding.vpgMain.setCurrentItem(1);
                } else if (item.getItemId() == R.id.menuActionUser) {
                    activityMainBinding.vpgMain.setCurrentItem(2);
                }
                return false;
            }
        });

        activityMainBinding.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }
}