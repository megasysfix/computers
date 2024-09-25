package com.cesar.computers.activity;


import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cesar.computers.R;
import com.cesar.computers.manager.CartManager;
import com.cesar.computers.objects.Computer;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView imgComputer;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvPrice;
    private Button btnAddToCart;
    private Computer computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        // Set up the toolbar if needed

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
        // Initialize views
        imgComputer = findViewById(R.id.imgComputerDetails);
        tvName = findViewById(R.id.tvComputerName);
        tvDesc = findViewById(R.id.tvComputerDesc);
        tvPrice = findViewById(R.id.tvComputerPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Get the passed Computer object
        computer = (Computer) getIntent().getSerializableExtra("computer");

        // Set the data
        tvName.setText(computer.getName());
        tvDesc.setText(computer.getDescription());
        tvPrice.setText(String.format("$%.2f", computer.getPrice()));
        String posterUrl = computer.getImage();

        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.macbookair)
                .into(imgComputer);

        // Handle Add to Cart button
        btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(computer);
            Toast.makeText(this, computer.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });
    }
}
