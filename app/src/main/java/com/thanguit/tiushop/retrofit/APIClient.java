package com.thanguit.tiushop.retrofit;

public class APIClient {
    private static final String url = "http://192.168.1.8/Code/TiuShop/API/Controller";

    public static DataClient getData() {
        return RetrofitClient.getClient(url).create(DataClient.class);
    }
}
