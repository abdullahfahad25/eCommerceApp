package com.example.fahad.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private static final String CATEGORY = "category";

    private String categoryName;
    private Button addNewProductButton;
    private ImageView inputProductImage;
    private EditText inputProductName;
    private EditText inputProductDescription;
    private EditText inputProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get(CATEGORY).toString();
//        Toast.makeText(this, "Welcome Admin...", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        addNewProductButton = (Button) findViewById(R.id.add_new_product);
        inputProductImage = (ImageView) findViewById(R.id.select_product_image);
        inputProductName = (EditText) findViewById(R.id.product_name);
        inputProductDescription = (EditText) findViewById(R.id.product_description);
        inputProductPrice = (EditText) findViewById(R.id.product_price);
    }
}