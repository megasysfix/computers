package com.cesar.computers.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.cesar.computers.R;
import com.cesar.computers.activity.ProductDetailsActivity;
import com.cesar.computers.manager.CartManager;
import com.cesar.computers.objects.Computer;

import java.util.ArrayList;

// ComputerAdapter.java
public class ComputerAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Computer> computerList;

    public ComputerAdapter(Context context, ArrayList<Computer> computerList) {
        this.context = context;
        this.computerList = computerList;
    }

    @Override
    public int getCount() {
        return computerList.size();
    }

    @Override
    public Object getItem(int position) {
        return computerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Or computerList.get(position).getId() if you have an ID field
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            // Inflate the layout
            convertView = LayoutInflater.from(context).inflate(R.layout.item_computer, parent, false);

            // Set up the ViewHolder
            holder = new ViewHolder();
            holder.imgComputer = convertView.findViewById(R.id.imgComputer);
            holder.tvName = convertView.findViewById(R.id.tvComputerName);
            holder.tvDesc = convertView.findViewById(R.id.tvComputerDesc);
            holder.tvPrice = convertView.findViewById(R.id.tvComputerPrice);
            holder.btnAddToCart = convertView.findViewById(R.id.btnAddToCart);

            convertView.setTag(holder);
        } else {
            // Use the ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the current computer
        Computer computer = computerList.get(position);

        // Set the data
        holder.tvName.setText(computer.getName());
        holder.tvDesc.setText(computer.getDescription());
        holder.tvPrice.setText("Popularity: " + computer.getPrice());
        // Inside getView method
        ImageView posterImageView = convertView.findViewById(R.id.imgComputer);
        String posterUrl = computer.getImage();

        Glide.with(context)
                .load(posterUrl)
                .placeholder(R.drawable.macbookair)
                .into(posterImageView);


        // Set the onClickListener for the Add to Cart button
        holder.btnAddToCart.setOnClickListener(v -> {
            // Handle adding to cart
            CartManager.getInstance().addToCart(computer);
            Toast.makeText(context, computer.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });

        // Set the onClickListener for the item container
        holder.imgComputer.setOnClickListener(v -> {
            // Open the product details activity
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            // Pass the computer object (or its data)
            intent.putExtra("computer", computer);
            context.startActivity(intent);
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgComputer;
        TextView tvName;
        TextView tvDesc;
        TextView tvPrice;
        ImageButton btnAddToCart;
    }
}