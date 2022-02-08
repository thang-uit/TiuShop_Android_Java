package com.thanguit.tiushop.presenter.listener;

public interface SignupListener {
    interface View {
        void signupSuccess();

        void signupFail(String error);
    }

    interface Presenter {
        void handleSignup(String username, String name, String password);
    }
}
