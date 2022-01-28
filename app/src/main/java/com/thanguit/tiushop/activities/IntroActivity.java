package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.IntroAdapter;
import com.thanguit.tiushop.databinding.ActivityIntroBinding;
import com.thanguit.tiushop.local.DataLocalManager;
import com.thanguit.tiushop.model.Intro;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IntroActivity extends AppCompatActivity {
    private static final String TAG = "IntroActivity";

    private ActivityIntroBinding binding;
    private IntroAdapter introAdapter;

    private int position = 0;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

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

        DataLocalManager.init(this);

        initializeViews();
        listeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (DataLocalManager.getFirstRun()) {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        Log.d(TAG, "Dispose: " + compositeDisposable.isDisposed());
    }

    private void initializeViews() {
        Observable.fromArray(getIntroList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Intro>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Intro> intros) {
                        Log.d(TAG, "onNext");

                        introAdapter = new IntroAdapter(IntroActivity.this, getIntroList());
                        binding.vpgIntro.setAdapter(introAdapter);

                        binding.tabIndicatorIntro.setupWithViewPager(binding.vpgIntro);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void listeners() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = binding.vpgIntro.getCurrentItem();

                if (position < getIntroList().size()) {
                    position++;
                    binding.vpgIntro.setCurrentItem(position);
                }

                if (position == getIntroList().size() - 1) {
                    lastPage();
                }
            }
        });

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.vpgIntro.setCurrentItem(getIntroList().size() - 1);
                lastPage();
            }
        });

        binding.tabIndicatorIntro.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "Tab Position: " + tab.getPosition());

                if (tab.getPosition() == getIntroList().size() - 1) {
                    lastPage();
                } else {
                    notLastPage();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.vpgIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "ViewPager Position: " + position);

                if (position == getIntroList().size() - 1) {
                    lastPage();
                } else {
                    notLastPage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void lastPage() {
        binding.btnNext.setText(getString(R.string.btnStart));
        binding.tvSkip.setVisibility(View.GONE);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
                DataLocalManager.setFirstRun(true);
            }
        });
    }

    private void notLastPage() {
        binding.btnNext.setText(getString(R.string.btnNext));

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = binding.vpgIntro.getCurrentItem();

                if (position < getIntroList().size()) {
                    position++;
                    binding.vpgIntro.setCurrentItem(position);
                }

                if (position == getIntroList().size() - 1) {
                    lastPage();
                }
            }
        });
    }

    private List<Intro> getIntroList() {
        List<Intro> introList = new ArrayList<>();
        introList.add(new Intro(R.raw.animation_intro_1, getResources().getStringArray(R.array.tvTitle)[0], getResources().getStringArray(R.array.tvHint)[0]));
        introList.add(new Intro(R.raw.animation_intro_2, getResources().getStringArray(R.array.tvTitle)[1], getResources().getStringArray(R.array.tvHint)[1]));
        introList.add(new Intro(R.raw.animation_intro_3, getResources().getStringArray(R.array.tvTitle)[2], getResources().getStringArray(R.array.tvHint)[2]));
        introList.add(new Intro(R.raw.animation_intro_4, getResources().getStringArray(R.array.tvTitle)[3], getResources().getStringArray(R.array.tvHint)[3]));
        introList.add(new Intro(R.raw.animation_intro_5, "", ""));

        return introList;
    }
}