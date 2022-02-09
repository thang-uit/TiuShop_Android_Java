package com.thanguit.tiushop.util;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;

public class Common {
    public static final String ipUrl = "192.168.1.8";

    // https://mkyong.com/regular-expressions/how-to-validate-username-with-regular-expression/
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){5,50}";
    public static final String REGEX_PASSWORD = "^(?!.* )(?=.*\\d)(?=.*[A-Z]).{6,100}$";

    public static RequestBody getRequestBody(HashMap<String, Object> jsonBody) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonBody)).toString());
    }
}
