package com.example.fahad.ecommerce.utils;

import static com.example.fahad.ecommerce.utils.Constants.REGISTRATION;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.example.fahad.ecommerce.R;

public class ProgressDialogUtil {
    private static ProgressDialog loadingBar;

    private ProgressDialogUtil() {}

    public static void showRegistrationLoadingBar(Context context, String type) {
        loadingBar = new ProgressDialog(context);

        if (TextUtils.equals(type, REGISTRATION)) {
            loadingBar.setTitle(context.getResources().getString(R.string.msg_register_loadingbar_title));
        } else {
            loadingBar.setTitle(context.getResources().getString(R.string.msg_login_loadingbar_title));
        }
        loadingBar.setMessage(context.getResources().getString(R.string.msg_register_loadingbar_wait_msg));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    public static void dismissLoadingBar() {
        if (loadingBar != null) {
            loadingBar.dismiss();
            loadingBar = null;
        }
    }
}
