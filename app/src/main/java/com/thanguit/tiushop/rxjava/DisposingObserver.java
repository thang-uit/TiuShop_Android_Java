package com.thanguit.tiushop.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class DisposingObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        DisposableManager.add(disposable);
    }

    @Override
    public void onNext(@androidx.annotation.NonNull T next) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
