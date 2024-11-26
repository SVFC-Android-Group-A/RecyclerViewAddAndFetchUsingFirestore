package com.example.recyclerviewaddandfetchusingfirestore.university;

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
import com.example.recyclerviewaddandfetchusingfirestore.adapter.UniversityAdapter;

import java.util.ArrayList;
import java.util.List;

public class UniversityPage extends AppCompatActivity {

    RecyclerView rvUniversity;
    List<University> universityList = new ArrayList<>();
    List<University> universityList1 = new ArrayList<>();
    UniversityAdapter universityAdapter;
    EditText etName, etType, etWebsite;
    Button btnAddItem, btnGetItem;

    University universityObject;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university);

        // Initialize Firestore and Objects
        universityObject = new University();
        firestore = FirebaseFirestore.getInstance();
        universityAdapter = new UniversityAdapter(universityList);

        // Initialize Views
        rvUniversity = findViewById(R.id.rv_university);
        etName = findViewById(R.id.et_name);
        etType = findViewById(R.id.et_type);
        etWebsite = findViewById(R.id.et_website);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        // Set RecyclerView Properties
        rvUniversity.setLayoutManager(new LinearLayoutManager(this));
        rvUniversity.setAdapter(universityAdapter);

        // Set Button Listeners
        btnAddItem.setOnClickListener(v -> addFunction());
        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction() {
        // Get input data from EditText fields
        String name = etName.getText().toString();
        String type = etType.getText().toString();
        String website = etWebsite.getText().toString();

        // Ensure all fields are filled
        if (!name.isEmpty() && !type.isEmpty() && !website.isEmpty()) {
            // Create a new university object
            universityObject.setName(name);
            universityObject.setType(type);
            universityObject.setWebsite(website);

            Log.d("MAIN", "name: " + universityObject.getName() + ", type: " + universityObject.getType() + ", website: " + universityObject.getWebsite());

            // Add the university to Firestore
            firestore.collection("university")
                    .add(universityObject)
                    .addOnSuccessListener(documentReference -> {
                        // Update the RecyclerView with the new data
                        universityList.add(new University(universityObject.getName(), universityObject.getType(), universityObject.getWebsite()));
                        universityAdapter.notifyItemInserted(universityList.size() - 1);
                    })
                    .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
        }
    }

    private void getFunction() {
        // Clear the existing list before fetching new data
        universityList1.clear();
        UniversityAdapter universityAdapter1 = new UniversityAdapter(universityList1);

        // Fetch data from Firestore
        firestore.collection("university")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Map each document to a University object
                            University university = document.toObject(University.class);
                            universityList1.add(new University(university.getName(), university.getType(), university.getWebsite()));
                        }
                        // Update the RecyclerView with the fetched data
                        rvUniversity.setAdapter(universityAdapter1);
                        rvUniversity.setLayoutManager(new LinearLayoutManager(this));
                    } else {
                        Log.e("MAIN", task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> Log.e("MAIN", e.getMessage()));
    }
}
