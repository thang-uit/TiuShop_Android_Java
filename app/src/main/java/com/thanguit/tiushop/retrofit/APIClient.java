package com.thanguit.tiushop.retrofit;

public class APIClient {
    private static final String url = "https://rec.recicp.vn/";

    public static DataClient getData() {
        return RetrofitClient.getClient(url).create(DataClient.class);
    }
}
