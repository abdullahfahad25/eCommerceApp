package com.example.fahad.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private static final String CATEGORY = "category";

    private String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get(CATEGORY).toString();
//        Toast.makeText(this, "Welcome Admin...", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();
    }
}