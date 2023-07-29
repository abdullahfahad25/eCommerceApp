package com.example.fahad.ecommerce.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.fahad.ecommerce.R;

public class ProgressDialogUtil {
    private static ProgressDialog loadingBar;

    private ProgressDialogUtil() {}

    public static void showRegistrationLoadingBar(Context context) {
        loadingBar = new ProgressDialog(context);

        loadingBar.setTitle(context.getResources().getString(R.string.msg_register_loadingbar_title));
        loadingBar.setMessage(context.getResources().getString(R.string.msg_register_loadingbar_wait_msg));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    public static void dismissRegistrationLoadingBar() {
        if (loadingBar != null) {
            loadingBar.dismiss();
            loadingBar = null;
        }
    }
}
