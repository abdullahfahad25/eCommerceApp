package com.example.fahad.ecommerce;

import static com.example.fahad.ecommerce.utils.Constants.NAME;
import static com.example.fahad.ecommerce.utils.Constants.NO_EMPTY;
import static com.example.fahad.ecommerce.utils.Constants.PASSWORD;
import static com.example.fahad.ecommerce.utils.Constants.PHONE_NUMBER;

import android.content.Context;
import android.text.TextUtils;


public class Validator {

    private Context context;

    public Validator(Context context) {
        this.context = context;
    }

    public String validateRegistration(String name, String phone, String password) {
        if (TextUtils.isEmpty(name)) {
            return NAME;
        } else if (TextUtils.isEmpty(phone)) {
            return PHONE_NUMBER;
        } else if (TextUtils.isEmpty(password)) {
            return PASSWORD;
        } else {
            return NO_EMPTY;
        }
    }

    public String validateLogin(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            return PHONE_NUMBER;
        } else if (TextUtils.isEmpty(password)) {
            return PASSWORD;
        } else {
            return NO_EMPTY;
        }
    }
}
