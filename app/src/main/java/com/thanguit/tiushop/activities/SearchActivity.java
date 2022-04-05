package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thanguit.tiushop.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}