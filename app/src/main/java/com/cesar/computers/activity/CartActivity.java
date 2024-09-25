package com.cesar.computers.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cesar.computers.R;
import com.cesar.computers.adapter.CartAdapter;
import com.cesar.computers.listener.ICartUpdateListener;
import com.cesar.computers.manager.CartManager;
import com.cesar.computers.objects.Computer;

import java.util.ArrayList;
public class CartActivity extends AppCompatActivity implements ICartUpdateListener {

    private ListView listCartItems;
    private ArrayList<Computer> cartItems;
    private CartAdapter cartAdapter;
    private TextView tvTotalPrice;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle back button in toolbar
        toolbar.setNavigationOnClickListener(v -> finish());

        listCartItems = findViewById(R.id.listCartItems);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Get cart items from CartManager
        cartItems = CartManager.getInstance().getCartItems();

        // Create an adapter to display the cart items
        cartAdapter = new CartAdapter(this, cartItems, this);

        listCartItems.setAdapter(cartAdapter);

        // Calculate and display the total price
        updateTotalPrice();

        // Set up the checkout button
        btnCheckout.setOnClickListener(v -> {
            // Handle checkout process
            Toast.makeText(this, "Proceeding to checkout", Toast.LENGTH_SHORT).show();
            // Implement checkout logic here
        });
    }

    @Override
    public void onCartUpdated() {
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = CartManager.getInstance().getTotalPrice();
        tvTotalPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the total price and refresh the list
        updateTotalPrice();
        cartAdapter.notifyDataSetChanged();
    }
}
