package com.thanguit.tiushop.retrofit;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.model.repository.User;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataClient {
    // https://stackoverflow.com/a/28712275

    @POST("User/Login.php")
    Observable<APIResponse<Account>> login(@Body RequestBody requestBody);

    @POST("User/Register.php")
    Observable<APIResponse<Account>> signup(@Body RequestBody requestBody);

    @POST("User/ChangePassword.php")
    Observable<APIResponse<Account>> changePassword(@Body RequestBody requestBody);

    @POST("User/GetUserInfo.php")
    Observable<APIResponse<User>> getUserInfo(@Body RequestBody requestBody);
}
