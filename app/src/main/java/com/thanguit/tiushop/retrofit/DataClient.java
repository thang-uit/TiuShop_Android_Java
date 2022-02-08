package com.thanguit.tiushop.retrofit;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.model.repository.User;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataClient {
    // https://stackoverflow.com/a/28712275

    @POST("/User/Login.php")
    Observable<APIResponse<Account>> login(@Body RequestBody requestBody);

    @POST("/User/Register.php")
    Call<APIResponse<Account>> signup(@Body RequestBody requestBody);

    @POST("/User/ChangePassword.php")
    Call<APIResponse<Account>> changePassword(@Body RequestBody requestBody);

    @POST("/User/GetUserInfo.php")
    Call<APIResponse<User>> getUserInfo(@Body RequestBody requestBody);
}
