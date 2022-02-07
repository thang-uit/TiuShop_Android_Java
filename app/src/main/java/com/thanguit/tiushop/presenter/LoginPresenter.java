package com.thanguit.tiushop.presenter;

import com.thanguit.tiushop.presenter.listener.LoginListener;

public class LoginPresenter implements LoginListener.Presenter {
    private LoginListener.View view;

    public void setView(LoginListener.View mView) {
        view = mView;
    }


    @Override
    public void handleLogin() {

    }
}
