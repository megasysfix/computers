package com.cesar.computers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cesar.computers.activity.CartActivity;
import com.cesar.computers.adapter.ComputerAdapter;
import com.cesar.computers.obj.Computer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// MainActivity.java
public class MainActivity extends AppCompatActivity {

    private ListView listProductosDisponibles;
    private ArrayList<Computer> computerList;
    private ComputerAdapter adapter;
    private FloatingActionButton btnCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the FloatingActionButton
        btnCarrito = findViewById(R.id.btnCarrito);
        btnCarrito.setOnClickListener(v -> {
            // Navigate to CartActivity
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Initialize the ListView
        listProductosDisponibles = findViewById(R.id.listProductosDisponibles);

        // Create a list of computers
        computerList = new ArrayList<>();
        computerList.add(new Computer("Dell XPS 13", 999.9, "Compact and powerful ultrabook"));
        computerList.add(new Computer("Apple MacBook Air", 1199.99, "Lightweight and sleek design"));
        computerList.add(new Computer("Lenovo ThinkPad X1", 1299.99, "Business-class performance"));
        // Add more computers as needed

        // Create an instance of the custom adapter
        adapter = new ComputerAdapter(this, computerList);

        // Set the adapter to the ListView
        listProductosDisponibles.setAdapter(adapter);
    }
}