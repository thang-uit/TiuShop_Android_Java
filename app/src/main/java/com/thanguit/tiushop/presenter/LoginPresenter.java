package com.thanguit.tiushop.presenter;

import android.util.Log;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.presenter.listener.LoginListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements LoginListener.Presenter {
    private static final String TAG = "LoginPresenter";
    private final LoginListener.View view;

    public LoginPresenter(LoginListener.View view) {
        this.view = view;
    }

    @Override
    public void handleLogin(String username, String password) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("username", username);
        jsonBody.put("password", password);

        DataClient dataClient = APIClient.getData();
        dataClient.login(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<Account>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull APIResponse<Account> accountAPIResponse) {
                        if (accountAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            view.loginSuccess(accountAPIResponse.getData().getUserID());
                        } else {
                            view.loginFail(MyApplication.getResource().getString(R.string.tvError9));
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, e.getMessage());
                        view.loginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });


//        call.enqueue(new Callback<APIResponse<Account>>() {
//            @Override
//            public void onResponse(@NonNull Call<APIResponse<Account>> call, @NonNull Response<APIResponse<Account>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        if (response.body().getStatus().equals(Common.STATUS_SUCCESS)) {
//                            view.loginSuccess();
//                        } else {
//                            view.loginFail(MyApplication.getResource().getString(R.string.tvError9));
//                        }
//                    } else {
//                        view.loginFail(MyApplication.getResource().getString(R.string.tvError9));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<APIResponse<Account>> call, @NonNull Throwable t) {
//                Log.d(TAG, t.getMessage());
//                view.loginFail(MyApplication.getResource().getString(R.string.tvError9));
//            }
//        });
    }
}
