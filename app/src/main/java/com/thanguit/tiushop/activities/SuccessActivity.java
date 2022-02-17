package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.databinding.ActivitySuccessBinding;
import com.thanguit.tiushop.util.Common;

public class SuccessActivity extends AppCompatActivity {
    private ActivitySuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeViews();
//        listeners();
    }

    private void initializeViews() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Common.LAYOUT_SIGNUP)) {
                binding.tvContent.setText(getString(R.string.tvSCSignUp));
                binding.btnOK.setText(getString(R.string.btnOK1));
                binding.btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SuccessActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            }
        }
    }
}