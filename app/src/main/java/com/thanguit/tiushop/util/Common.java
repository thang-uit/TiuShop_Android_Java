package com.thanguit.tiushop.util;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;

public class Common {
    //    public static final String ipUrl = "http://192.168.1.8";
    public static final String ipUrl = "http://192.168.1.42";

    // https://mkyong.com/regular-expressions/how-to-validate-username-with-regular-expression/
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){5,50}";
    public static final String REGEX_PASSWORD = "^(?!.* )(?=.*\\d)(?=.*[A-Z]).{6,100}$";

    public static final String STATUS_SUCCESS = "Success";
    public static final String STATUS_FAIL = "Fail";

    public static final String LAYOUT_SIGNUP = "SIGNUP";
    public static final String LAYOUT_ORDER = "ORDER";

    public static final String NEW_PRODUCT = "new";
    public static final String DISCOUNT_PRODUCT = "discount";

    public static final String PRODUCT_ID = "PRODUCT_ID";

    public static final String MAN_PRODUCT = "man";
    public static final String WOMAN_PRODUCT = "woman";
    public static final String BOTH_PRODUCT = "both";
    public static final String ALL_PRODUCT = "all";

    public static final int WAITING_CONFIRM = 0;
    public static final int WAITING_GOOD = 1;
    public static final int DELIVERING = 2;
    public static final int ORDER_SUCCESS = 3;
    public static final int ORDER_CANCEL = 4;

    public static final String TRUE = "True";
    public static final String FALSE = "False";

    public static RequestBody getRequestBody(HashMap<String, Object> jsonBody) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonBody)).toString());
    }
}
