package com.cesar.computers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cesar.computers.activity.CartActivity;
import com.cesar.computers.adapter.ComputerAdapter;
import com.cesar.computers.objects.Computer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// MainActivity.java
public class MainActivity extends AppCompatActivity {

    private ListView listProductosDisponibles;
    private ArrayList<Computer> computerList;
    private ComputerAdapter adapter;
    private FloatingActionButton btnCarrito;

    private EditText movieInputBox;

    // Replace with your actual Bearer token
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZWEzNGRlNTE3YTQ0MTQzNjZkYjY1MjBjYWZkYTYwOCIsIm5iZiI6MTcyNzMwNDE5My4xNzY1ODUsInN1YiI6IjViNjQ2YmRlOTI1MTQxNDA2MDA1MGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dO4qG45TDcSpzz4SpI6dqqicWdxAXa50jQTc5er-tPY";


    private Button btnSearch;

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

        movieInputBox = findViewById(R.id.movieSearchInput);


        btnSearch = findViewById(R.id.searchMovies);

        btnSearch.setOnClickListener(v -> {
            String query = movieInputBox.getText().toString();
            if (query.isEmpty()) {
                movieInputBox.setError("Please enter a movie title");
                return;
            }

            searchMovies(query);
        });



        // Initialize the ListView
        listProductosDisponibles = findViewById(R.id.listProductosDisponibles);

        // Create a list of computers
        computerList = new ArrayList<>();

        // Create an instance of the custom adapter
        adapter = new ComputerAdapter(this, computerList);

        // Set the adapter to the ListView
        listProductosDisponibles.setAdapter(adapter);
    }

    private void searchMovies(String query) {
        OkHttpClient client = new OkHttpClient();

        // Encode the query to handle spaces and special characters
        String encodedQuery = query.replace(" ", "%20");

        String url = "https://api.themoviedb.org/3/search/movie?query=" + encodedQuery + "&include_adult=false&language=en-US&page=1";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", BEARER_TOKEN)
                .build();

        // Perform the network request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Handle network failure
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Network Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // Handle unsuccessful response
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "API Error: " + response.message(), Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                // Parse the JSON response
                String responseBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                    ArrayList<Computer> tempList = new ArrayList<>();

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject movieObj = resultsArray.getJSONObject(i);
                        String title = movieObj.getString("title");
                        String overview = movieObj.optString("overview", "No description available.");
                        String posterPath = movieObj.optString("poster_path", "");
                        double popularity = movieObj.getDouble("popularity");

                        tempList.add(new Computer(posterPath, title, popularity, overview));
                    }

                    // Update the UI on the main thread
                    runOnUiThread(() -> {
                        computerList.clear();
                        computerList.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}