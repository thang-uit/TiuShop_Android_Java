package com.thanguit.tiushop.rxjava;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class DisposableManager {
    private static CompositeDisposable compositeDisposable;

    private DisposableManager() {
    }

    public static void add(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    public static void dispose() {
        getCompositeDisposable().dispose();
    }

    private static CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }
}
