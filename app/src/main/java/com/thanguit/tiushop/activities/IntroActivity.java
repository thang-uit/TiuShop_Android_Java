package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.IntroAdapter;
import com.thanguit.tiushop.databinding.ActivityIntroBinding;
import com.thanguit.tiushop.model.Intro;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private IntroAdapter introAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = this.getWindow();
//            // clear FLAG_TRANSLUCENT_STATUS flag:
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        }

        // https://developer.android.com/training/system-ui/status#41
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeView();
    }

    private void initializeView() {
        introAdapter = new IntroAdapter(this, getIntroList());
        binding.vpgIntro.setAdapter(introAdapter);
    }

    private List<Intro> getIntroList() {
        List<Intro> introList = new ArrayList<>();
        introList.add(new Intro(R.raw.animation_success, "Shopping", getString(R.string.tvHint)));
        introList.add(new Intro(R.raw.animation_empty_cart, "Easy Payment", getString(R.string.tvHint)));

        return introList;
    }
}