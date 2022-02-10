package com.thanguit.tiushop.presenter;

import com.thanguit.tiushop.R;
import com.thanguit.tiushop.application.MyApplication;
import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.presenter.listener.SignupListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignupPresenter implements SignupListener.Presenter {
    private SignupListener.View view;

    public SignupPresenter(SignupListener.View view) {
        this.view = view;
    }

    @Override
    public void handleSignup(String username, String name, String password) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("name", name);

        DataClient dataClient = APIClient.getData();
        dataClient.signup(Common.getRequestBody(jsonBody))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponse<Account>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull APIResponse<Account> accountAPIResponse) {
                        if (accountAPIResponse.getStatus().equals(Common.STATUS_SUCCESS)) {
                            view.signupSuccess();
                        } else {
                            view.signupFail(accountAPIResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.signupFail(MyApplication.getResource().getString(R.string.tvError0));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
