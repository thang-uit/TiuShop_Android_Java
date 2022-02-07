package com.thanguit.tiushop.presenter.listener;

public interface LoginListener {
    interface View {
        void loginSuccess();

        void loginFail(String error);
    }

    interface Presenter {
        void handleLogin(String email, String password);
    }
}
