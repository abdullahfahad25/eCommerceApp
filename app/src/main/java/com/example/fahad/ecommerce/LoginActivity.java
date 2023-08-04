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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fahad.ecommerce.data.Database;
import com.example.fahad.ecommerce.prevalent.Prevalent;
import com.example.fahad.ecommerce.utils.LoginToast;
import com.example.fahad.ecommerce.utils.ProgressDialogUtil;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputPhoneNumber;
    private EditText inputPassword;
    private Button loginBtn;

    private Validator validator;

    private Database db;

    private ProgressDialog loadingBar;

    private CheckBox chkBoxRememberMe;

    private TextView adminLink;
    private TextView notAdminLink;

    private RegisterActivity.onCompleteListener userOnCompleteListener = new RegisterActivity.onCompleteListener() {
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

    private RegisterActivity.onCompleteListener adminOnCompleteListener = new RegisterActivity.onCompleteListener() {
        @Override
        public void onSuccess() {
            Toast.makeText(LoginActivity.this, "Welcome Admin, You are logged in Successfully...", Toast.LENGTH_SHORT).show();
            ProgressDialogUtil.dismissLoadingBar();

            Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFailed() {
            ProgressDialogUtil.dismissLoadingBar();
            Toast.makeText(LoginActivity.this, "Admin Password is incorrect.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onExistance(String phone) {
            Toast.makeText(LoginActivity.this, "Admin Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
            ProgressDialogUtil.dismissLoadingBar();
        }

        @Override
        public void onCancelled(String msg) {}
    };

    private View.OnClickListener loginUserAccOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginUserAccount();
        }
    };

    private View.OnClickListener loginAdminAccOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginAdminAccount();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputPhoneNumber = findViewById(R.id.login_phone_number_input);
        inputPassword = findViewById(R.id.login_password_input);
        loginBtn = findViewById(R.id.login_btn);
        adminLink = findViewById(R.id.admin_panel_link);
        notAdminLink = findViewById(R.id.not_admin_panel_link);

        chkBoxRememberMe = findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        loadingBar = new ProgressDialog(this);

        validator = new Validator(this);
        db = Database.getInstance();

        loginBtn.setOnClickListener(loginUserAccOnClickListener);

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);

                loginBtn.setOnClickListener(loginAdminAccOnClickListener);
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);

                loginBtn.setOnClickListener(loginUserAccOnClickListener);
            }
        });
    }

    private void loginUserAccount() {
        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        String response = validator.validateLogin(phone, password);

        if (!TextUtils.equals(response, NO_EMPTY)) {
            LoginToast.showToast(LoginActivity.this, response);
        } else {
            ProgressDialogUtil.showLoadingBar(LoginActivity.this, LOGIN);

            if(chkBoxRememberMe.isChecked()) {
                Paper.book().write(Prevalent.UserPhoneKey, phone);
                Paper.book().write(Prevalent.UserPasswordKey, password);
            }

            db.loginUser(userOnCompleteListener, phone, password);
        }
    }

    private void loginAdminAccount() {
        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        String response = validator.validateLogin(phone, password);

        if (!TextUtils.equals(response, NO_EMPTY)) {
            LoginToast.showToast(LoginActivity.this, response);
        } else {
            ProgressDialogUtil.showLoadingBar(LoginActivity.this, LOGIN);

            if(chkBoxRememberMe.isChecked()) {
                Paper.book().write(Prevalent.UserPhoneKey, phone);
                Paper.book().write(Prevalent.UserPasswordKey, password);
            }

            db.loginAdmin(adminOnCompleteListener, phone, password);
        }
    }
}