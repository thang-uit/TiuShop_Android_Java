package com.thanguit.tiushop.retrofit;

import com.thanguit.tiushop.model.APIResponse;
import com.thanguit.tiushop.model.repository.Account;
import com.thanguit.tiushop.model.repository.Cart;
import com.thanguit.tiushop.model.repository.Category;
import com.thanguit.tiushop.model.repository.Collection;
import com.thanguit.tiushop.model.repository.Product;
import com.thanguit.tiushop.model.repository.Slider;
import com.thanguit.tiushop.model.repository.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("Product/GetGroupProduct.php")
    Observable<APIResponse<List<Product>>> getGroupProduct(@Query("amount") int amount, @Query("option") String option);

    @GET("Product/GetCategoryProduct.php")
    Observable<APIResponse<List<Product>>> getCategoryProduct(@Query("categoryID") int categoryID);

    @GET("Product/GetCollectionsProduct.php")
    Observable<APIResponse<List<Product>>> getCollectionsProduct(@Query("collectionsID") int collectionsID);

    @GET("Product/SearchProduct.php")
    Observable<APIResponse<List<Product>>> searchProduct(@Query("keyword") String keyword);

    @GET("Category/GetCategory.php")
    Observable<APIResponse<List<Category>>> getCategory();

    @GET("Collections/GetCollections.php")
    Observable<APIResponse<List<Collection>>> getCollections();

    @POST("Product/GetProductDetail.php")
    Observable<APIResponse<Product>> getProductDetail(@Body RequestBody requestBody);

    @POST("Cart/GetWishList.php")
    Observable<APIResponse<List<Cart>>> getWishList(@Body RequestBody requestBody);

    @POST("Cart/HandleWishList.php")
    Observable<APIResponse<Cart>> handleWishList(@Body RequestBody requestBody);

    @POST("Cart/MoveToCart.php")
    Observable<APIResponse<Cart>> moveToCart(@Body RequestBody requestBody);

    @POST("Cart/GetCart.php")
    Observable<APIResponse<List<Cart>>> getCart(@Body RequestBody requestBody);

    @POST("Cart/AddToCart.php")
    Observable<APIResponse<Cart>> addToCart(@Body RequestBody requestBody);

    @POST("Cart/DeleteCart.php")
    Observable<APIResponse<Cart>> deleteCart(@Body RequestBody requestBody);

    @POST("Cart/UpdateCart.php")
    Observable<APIResponse<Cart>> updateCart(@Body RequestBody requestBody);
}
