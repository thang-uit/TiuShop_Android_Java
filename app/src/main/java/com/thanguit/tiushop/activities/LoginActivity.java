package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.LoginAdapter;
import com.thanguit.tiushop.databinding.ActivityLoginBinding;
import com.thanguit.tiushop.fragment.LoginFragment;
import com.thanguit.tiushop.fragment.SignupFragment;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private LoginAdapter loginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // https://stackoverflow.com/a/67057212
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//            View decorView = window.getDecorView();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            } else {
//                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            }
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

        // https://stackoverflow.com/a/40547374
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeViews();
        listeners();
    }

    private void initializeViews() {
        binding.tabLogin.setupWithViewPager(binding.vpgPage);

        loginAdapter = new LoginAdapter(getSupportFragmentManager());
        loginAdapter.addFragment(new LoginFragment(), getString(R.string.tvLogin));
        loginAdapter.addFragment(new SignupFragment(), getString(R.string.tvSignup));

        binding.vpgPage.setAdapter(loginAdapter);
    }

    private void listeners() {
    }
}