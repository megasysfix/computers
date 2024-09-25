package com.cesar.computers.manager;

import com.cesar.computers.objects.Computer;

import java.util.ArrayList;

// CartManager.java
public class CartManager {
    private static CartManager instance;
    private ArrayList<Computer> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Computer computer) {
        cartItems.add(computer);
    }

    public void removeFromCart(Computer computer) {
        cartItems.remove(computer);
    }

    public ArrayList<Computer> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Computer computer : cartItems) {
            total += computer.getPrice();
        }
        return total;
    }
}