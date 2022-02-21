package com.thanguit.tiushop.retrofit;

public class APIClient {
    private static final String url = "http://10.0.148.182/Code/TiuShop/API/Controller/";
//    private static final String url = "http://192.168.1.36/Code/TiuShop/API/Controller/";

    public static DataClient getData() {
        return RetrofitClient.getClient(url).create(DataClient.class);
    }
}
