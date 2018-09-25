package com.haris.savongroceries;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    TextView txtStore, txtProductSku;
    EditText txtProductPrice;
    ImageView imgProduct;

    SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        txtStore = findViewById(R.id.txtStore);
        txtStore.setText(prefs.getString("store_id", ""));

        txtProductSku = findViewById(R.id.txtSku);
        txtProductSku.setText(prefs.getString("product_sku", ""));

        imgProduct = findViewById(R.id.imgProduct);
        //imgProduct.setImageURI();
    }

    public void onSubmitClick(View view) {

    }
}
