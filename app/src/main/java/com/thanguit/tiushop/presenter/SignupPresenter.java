package com.thanguit.tiushop.presenter;

import com.thanguit.tiushop.presenter.listener.SignupListener;

public class SignupPresenter implements SignupListener.Presenter{
    private SignupListener.View view;

    public SignupPresenter(SignupListener.View view) {
        this.view = view;
    }

    @Override
    public void handleSignup(String username, String name, String password) {

    }
}
