package com.thanguit.tiushop.presenter;

import androidx.annotation.NonNull;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.presenter.listener.LoginListener;
import com.thanguit.tiushop.retrofit.APIClient;
import com.thanguit.tiushop.retrofit.DataClient;
import com.thanguit.tiushop.util.Common;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginListener.Presenter {
    private LoginListener.View view;


    public LoginPresenter(LoginListener.View view) {
        this.view = view;
    }

    @Override
    public void handleLogin(String username, String password) {
        HashMap<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("username", username);
        jsonBody.put("password", password);

        DataClient dataClient = APIClient.getData();
        Call<APIResponse<Account>> call = dataClient.login(Common.getRequestBody(jsonBody));
        call.enqueue(new Callback<APIResponse<Account>>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse<Account>> call, @NonNull Response<APIResponse<Account>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<Account>> call, @NonNull Throwable t) {

            }
        });
    }
}
