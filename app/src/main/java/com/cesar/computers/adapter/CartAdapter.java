package com.cesar.computers.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import android.widget.TextView; // Add this line
import android.widget.Button;   // Add this line
import android.view.View.OnClickListener; // Add this line if needed

import com.cesar.computers.R;
import com.cesar.computers.listener.CartUpdateListener;
import com.cesar.computers.manager.CartManager;
import com.cesar.computers.obj.Computer;

import java.util.ArrayList;
// CartAdapter.java
public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Computer> cartItems;
    private CartUpdateListener cartUpdateListener;

    public CartAdapter(Context context, ArrayList<Computer> cartItems, CartUpdateListener cartUpdateListener) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartUpdateListener = cartUpdateListener;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Or cartItems.get(position).getId() if you have an ID field
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            // Inflate the layout
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);

            // Set up the ViewHolder
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvComputerName);
            holder.tvPrice = convertView.findViewById(R.id.tvComputerPrice);
            holder.btnRemoveFromCart = convertView.findViewById(R.id.btnRemoveFromCart);

            convertView.setTag(holder);
        } else {
            // Use the ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the current computer
        Computer computer = cartItems.get(position);

        // Set the data
        holder.tvName.setText(computer.getName());
        holder.tvPrice.setText(String.format("$%.2f", computer.getPrice()));

        // Set the onClickListener for the Remove from Cart button
        holder.btnRemoveFromCart.setOnClickListener(v -> {
            // Handle removing from cart
            CartManager.getInstance().removeFromCart(computer);
            // Update the adapter
            cartItems.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, computer.getName() + " removed from cart", Toast.LENGTH_SHORT).show();

            // Notify the CartActivity to update the total price
            if (cartUpdateListener != null) {
                cartUpdateListener.onCartUpdated();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvPrice;
        Button btnRemoveFromCart;
    }
}
