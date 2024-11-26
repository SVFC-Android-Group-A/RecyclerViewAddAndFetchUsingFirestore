package com.example.recyclerviewaddandfetchusingfirestore.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.recyclerviewaddandfetchusingfirestore.R;
import com.example.recyclerviewaddandfetchusingfirestore.adapter.RestaurantAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPage extends AppCompatActivity {

    RecyclerView rvRestaurant;
    List<Restaurant> restaurantList = new ArrayList<>();
    List<Restaurant> restaurantList1 = new ArrayList<>();
    RestaurantAdapter restaurantAdapter;
    EditText etName, etType, etLocation;
    Button btnAddItem, btnGetItem;

    Restaurant restaurantObject;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the correct layout
        setContentView(R.layout.restaurant);

        // Initialize restaurant object and Firestore instance
        restaurantObject = new Restaurant();
        firestore = FirebaseFirestore.getInstance();

        // Initialize adapter
        restaurantAdapter = new RestaurantAdapter(restaurantList);

        // Bind views
        rvRestaurant = findViewById(R.id.rv_restaurant);
        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        etLocation = findViewById(R.id.et_location);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        // Set button listeners
        btnAddItem.setOnClickListener(v -> addFunction());
        btnGetItem.setOnClickListener(v -> getFunction());

        // Set up RecyclerView
        rvRestaurant.setLayoutManager(new LinearLayoutManager(this));
        rvRestaurant.setAdapter(restaurantAdapter);
    }

    private void addFunction() {
        String name = etName.getText().toString();
        String type = etType.getText().toString();
        String location = etLocation.getText().toString();

        if (!name.isEmpty() && !type.isEmpty() && !location.isEmpty()) {
            // Set restaurant object properties
            restaurantObject.setName(name);
            restaurantObject.setType(type);
            restaurantObject.setLocation(location);

            Log.d("MAIN", "name: " + restaurantObject.getName() + ", type: " + restaurantObject.getType() + ", location: " + restaurantObject.getLocation());

            // Add restaurant data to Firestore
            firestore.collection("restaurant")
                    .add(restaurantObject)
                    .addOnSuccessListener(documentReference -> {
                        restaurantList.add(new Restaurant(restaurantObject.getName(), restaurantObject.getType(), restaurantObject.getLocation()));
                        restaurantAdapter.notifyItemInserted(restaurantList.size() - 1);
                    })
                    .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
        }
    }

    private void getFunction() {
        restaurantList1.clear();
        RestaurantAdapter restaurantAdapter1 = new RestaurantAdapter(restaurantList1);

        firestore.collection("restaurant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Restaurant restaurant = document.toObject(Restaurant.class);
                            restaurantList1.add(new Restaurant(restaurant.getName(), restaurant.getType(), restaurant.getLocation()));
                        }
                        rvRestaurant.setAdapter(restaurantAdapter1);
                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
