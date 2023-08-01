package com.example.fahad.ecommerce;

import static com.example.fahad.ecommerce.utils.Constants.LOGIN;
import static com.example.fahad.ecommerce.utils.Constants.NO_EMPTY;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fahad.ecommerce.data.Database;
import com.example.fahad.ecommerce.utils.LoginToast;
import com.example.fahad.ecommerce.utils.ProgressDialogUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText inputPhoneNumber;
    private EditText inputPassword;
    private Button loginBtn;

    private Validator validator;

    private Database db;

    private ProgressDialog loadingBar;

    private RegisterActivity.onCompleteListener completeListener = new RegisterActivity.onCompleteListener() {
        @Override
        public void onSuccess() {
            Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
            ProgressDialogUtil.dismissLoadingBar();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFailed() {
            ProgressDialogUtil.dismissLoadingBar();
            Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onExistance(String phone) {
            Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
            ProgressDialogUtil.dismissLoadingBar();
        }

        @Override
        public void onCancelled(String msg) {}
    };

    private View.OnClickListener loginAccOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginAccount();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputPhoneNumber = findViewById(R.id.login_phone_number_input);
        inputPassword = findViewById(R.id.login_password_input);
        loginBtn = findViewById(R.id.login_btn);

        loadingBar = new ProgressDialog(this);

        validator = new Validator(this);
        db = Database.getInstance();

        loginBtn.setOnClickListener(loginAccOnClickListener);
    }

    private void loginAccount() {
        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        String response = validator.validateLogin(phone, password);

        if (!TextUtils.equals(response, NO_EMPTY)) {
            LoginToast.showToast(LoginActivity.this, response);
        } else {
            ProgressDialogUtil.showRegistrationLoadingBar(LoginActivity.this, LOGIN);
            db.login(completeListener, phone, password);
        }
    }
}