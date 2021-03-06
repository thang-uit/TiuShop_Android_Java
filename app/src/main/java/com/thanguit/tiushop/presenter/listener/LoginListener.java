package com.thanguit.tiushop.presenter.listener;

public interface LoginListener {
    interface View {
        void loginSuccess(String userID);

        void loginFail(String error);
    }

    interface Presenter {
        void handleLogin(String username, String password);
    }
}
