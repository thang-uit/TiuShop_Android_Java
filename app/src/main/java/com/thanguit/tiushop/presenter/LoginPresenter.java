package com.thanguit.tiushop.presenter;

import com.thanguit.tiushop.presenter.listener.LoginListener;

public class LoginPresenter implements LoginListener.Presenter {
    private LoginListener.View view;

    public LoginPresenter(LoginListener.View view) {
        this.view = view;
    }

    @Override
    public void handleLogin(String email, String password) {
    }
}