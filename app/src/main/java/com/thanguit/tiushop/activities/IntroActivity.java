package com.thanguit.tiushop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.adapter.IntroAdapter;
import com.thanguit.tiushop.databinding.ActivityIntroBinding;
import com.thanguit.tiushop.model.Intro;
import com.thanguit.tiushop.rxjava.DisposableManager;
import com.thanguit.tiushop.rxjava.DisposingObserver;

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

        initializeView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        Log.d(TAG, "Dispose: " + compositeDisposable.isDisposed());
    }

    private void initializeView() {
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



//        introAdapter = new IntroAdapter(this, getIntroList());
//        binding.vpgIntro.setAdapter(introAdapter);
    }

    private List<Intro> getIntroList() {
        List<Intro> introList = new ArrayList<>();
        introList.add(new Intro(R.raw.animation_intro_1, getResources().getStringArray(R.array.tvTitle)[0], getResources().getStringArray(R.array.tvHint)[0]));
        introList.add(new Intro(R.raw.animation_intro_2, getResources().getStringArray(R.array.tvTitle)[1], getResources().getStringArray(R.array.tvHint)[1]));
        introList.add(new Intro(R.raw.animation_intro_3, getResources().getStringArray(R.array.tvTitle)[2], getResources().getStringArray(R.array.tvHint)[2]));

        return introList;
    }
}