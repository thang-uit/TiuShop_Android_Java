package com.thanguit.tiushop.retrofit;

public class APIClient {
    private static final String url = "http://192.168.1.26/Code/TiuShop/API/Controller/";
//    private static final String url = "http://192.168.0.212/Code/TiuShop/API/Controller/";

    public static DataClient getData() {
        return RetrofitClient.getClient(url).create(DataClient.class);
    }
}
