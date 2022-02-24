package com.thanguit.tiushop.retrofit;

public class APIClient {
    private static final String url = "http://10.0.152.201/Code/TiuShop/API/Controller/";

    public static DataClient getData() {
        return RetrofitClient.getClient(url).create(DataClient.class);
    }
}
