package com.example.fahad.ecommerce;

import static com.example.fahad.ecommerce.utils.Constants.NO_EMPTY;
import static com.example.fahad.ecommerce.utils.Constants.REGISTRATION;

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
import com.example.fahad.ecommerce.utils.ProgressDialogUtil;
import com.example.fahad.ecommerce.utils.RegistrationToast;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;

    private EditText inputName;
    private EditText inputPhoneNumber;
    private EditText inputPassword;

    private Validator validator;

    private Database db;

    private ProgressDialog loadingBar;

    public interface onCompleteListener {
        void onSuccess();
        void onFailed();
        void onExistance(String phone);

        void onCancelled(String msg);
    }

    private onCompleteListener completeListener = new onCompleteListener() {
        @Override
        public void onSuccess() {
            Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
            ProgressDialogUtil.dismissLoadingBar();

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFailed() {
            ProgressDialogUtil.dismissLoadingBar();
            Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onExistance(String phone) {
            Toast.makeText(RegisterActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
            ProgressDialogUtil.dismissLoadingBar();
            Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onCancelled(String msg) {
            ProgressDialogUtil.dismissLoadingBar();
            Toast.makeText(RegisterActivity.this, "onCancelled!! error: " + msg, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener createAccOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createAccount();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = (Button) findViewById(R.id.register_btn);
        inputName = (EditText) findViewById(R.id.register_username_input);
        inputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        inputPassword = (EditText) findViewById(R.id.register_password_input);

        validator = new Validator(this);
        db = Database.getInstance();

        loadingBar = new ProgressDialog(this);

        createAccountButton.setOnClickListener(createAccOnClickListener);
    }

    private void createAccount() {
        String name = inputName.getText().toString();
        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();

        String response = validator.validateRegistration(name, phone, password);
        if (!TextUtils.equals(response, NO_EMPTY)) {
            RegistrationToast.showToast(getApplicationContext(), response);
        } else {
            ProgressDialogUtil.showRegistrationLoadingBar(RegisterActivity.this, REGISTRATION);
            db.register(completeListener, name, phone, password);
        }
    }
}