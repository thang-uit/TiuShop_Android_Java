package com.thanguit.tiushop.retrofit;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.model.repository.Category;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.model.repository.Slider;
import com.thanguit.tiushop.model.repository.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @POST("User/UpdateUserInfo.php")
    Observable<APIResponse<User>> updateUserInfo(@Body RequestBody user);

    @GET("Product/GetSlider.php")
    Observable<APIResponse<List<Slider>>> getSlider(@Query("amount") int amount);

    @GET("Product/GetGroupProduct.php?amount={amount}&option={option}")
    Observable<APIResponse<List<Product>>> getGroupProduct(@Path("option") String option, @Path("amount") int amount);

    @GET("Product/GetCategoryProduct.php?categoryID={categoryID}")
    Observable<APIResponse<List<Product>>> getCategoryProduct(@Path("categoryID") String categoryID);

    @GET("Product/GetCollectionsProduct.php?collectionsID={collectionsID}")
    Observable<APIResponse<List<Product>>> getCollectionsProduct(@Path("collectionsID") String collectionsID);

    @GET("Product/SearchProduct.php?keyword={keyword}")
    Observable<APIResponse<List<Product>>> searchProduct(@Path("keyword") String keyword);

    @GET("/Category/GetCategory.php")
    Observable<APIResponse<List<Category>>> getCategory();
}
