package com.example.fahad.ecommerce.utils;

import static com.example.fahad.ecommerce.utils.Constants.NAME;
import static com.example.fahad.ecommerce.utils.Constants.PASSWORD;
import static com.example.fahad.ecommerce.utils.Constants.PHONE_NUMBER;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.fahad.ecommerce.R;

public class RegistrationToast {
    private static final String TAG = "RegistrationToast";

    private RegistrationToast() {}

    public static void showToast(Context context, String data) {
        String msg = "";

        switch (data) {
            case NAME:
                msg = NAME;
                Toast.makeText(context, context.getResources().getString(R.string.msg_register_name), Toast.LENGTH_SHORT).show();
                break;
            case PHONE_NUMBER:
                msg = PHONE_NUMBER;
                Toast.makeText(context, context.getResources().getString(R.string.msg_register_phone_number), Toast.LENGTH_SHORT).show();
                break;
            case PASSWORD:
                msg = PASSWORD;
                Toast.makeText(context, context.getResources().getString(R.string.msg_register_password), Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.e(TAG, "Unknown msg type!!");
        }
    }
}
