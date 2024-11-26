package com.example.recyclerviewaddandfetchusingfirestore.products;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewaddandfetchusingfirestore.R;
import com.example.recyclerviewaddandfetchusingfirestore.adapter.ProductAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends AppCompatActivity {

    //Products Page, all are running smoothly
    EditText etName, etPrice, etCategory;
    Button btnAddItem, btnGetItem;

    // RecyclerView setup
    RecyclerView rvProduct;
    ProductAdapter productAdapter;
    List<Product> productList = new ArrayList<>();

    // Firestore instance
    FirebaseFirestore firestore;
    Product productObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);  // Ensure this is the correct layout

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize product object
        productObject = new Product();

        // Initialize UI components
        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        etCategory = findViewById(R.id.et_category);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);
        rvProduct = findViewById(R.id.rv_product);

        // Set up RecyclerView and Adapter
        productAdapter = new ProductAdapter(productList);
        rvProduct.setAdapter(productAdapter);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));

        // Set click listeners for buttons
        btnAddItem.setOnClickListener(v -> addFunction());
        btnGetItem.setOnClickListener(v -> getFunction());
    }

    // Function to add a new product to Firestore
    private void addFunction() {
        // Validate inputs
        if (etName.getText().toString().isEmpty() || etPrice.getText().toString().isEmpty() || etCategory.getText().toString().isEmpty()) {
            Log.d("MAIN", "Fields cannot be empty!");
            return;
        }

        int price = 0;
        try {
            price = Integer.parseInt(etPrice.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("MAIN", "Invalid price format!");
            return;
        }

        // Set product details
        productObject.setName(etName.getText().toString());
        productObject.setPrice(price);
        productObject.setCategory(etCategory.getText().toString());

        Log.d("MAIN", "name: " + productObject.getName() + ", price: " + productObject.getPrice() + ", category: " + productObject.getCategory());

        // Add product to Firestore
        firestore.collection("products")
                .add(productObject)  // Add the product object to Firestore
                .addOnSuccessListener(documentReference -> {
                    // On success, add to product list and update RecyclerView
                    productList.add(new Product(productObject.getPrice(), productObject.getName(), productObject.getCategory()));
                    productAdapter.notifyItemInserted(productList.size() - 1);
                    Log.d("MAIN", "Product added to Firestore");
                })
                .addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }

    // Function to get all products from Firestore
    private void getFunction() {
        // Clear existing products
        productList.clear();
        productAdapter.notifyDataSetChanged();

        // Fetch all products from Firestore
        firestore.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Loop through the results and add each product to the list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            productList.add(new Product(product.getPrice(), product.getName(), product.getCategory()));
                        }
                        // Notify adapter that data has changed
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}
