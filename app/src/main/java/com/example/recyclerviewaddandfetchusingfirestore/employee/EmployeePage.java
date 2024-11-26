package com.example.recyclerviewaddandfetchusingfirestore.employee;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewaddandfetchusingfirestore.R;
import com.example.recyclerviewaddandfetchusingfirestore.adapter.EmployeesAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeePage extends AppCompatActivity {

    RecyclerView rvEmployees;
    List<Employee> employeeList = new ArrayList<>();
    EmployeesAdapter employeesAdapter;
    EditText etName, etDepartment, etEmail;
    Button btnAddItem, btnGetItem;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore

        // Initialize views
        rvEmployees = findViewById(R.id.rv_employees);
        etName = findViewById(R.id.et_name);
        etDepartment = findViewById(R.id.et_department);
        etEmail = findViewById(R.id.et_email);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        // Initialize adapter
        employeesAdapter = new EmployeesAdapter(employeeList);
        rvEmployees.setAdapter(employeesAdapter);
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));

        // Set listeners for the buttons
        btnAddItem.setOnClickListener(v -> addEmployee());
        btnGetItem.setOnClickListener(v -> getEmployees());
    }

    private void addEmployee() {
        // Validate inputs
        String name = etName.getText().toString();
        String department = etDepartment.getText().toString();
        String email = etEmail.getText().toString();

        if (name.isEmpty() || department.isEmpty() || email.isEmpty()) {
            Log.d("EmployeePage", "All fields are required!");
            return;
        }

        // Create a new employee object
        Employee employee = new Employee(name, department, email);
        Log.d("EmployeePage", "Name: " + name + ", Department: " + department + ", Email: " + email);

        // Add employee to Firestore
        firestore.collection("employees")
                .add(employee)  // Adds employee object to Firestore
                .addOnSuccessListener(documentReference -> {
                    // On success, add the employee to the list and notify adapter
                    employeeList.add(employee);
                    employeesAdapter.notifyItemInserted(employeeList.size() - 1);
                    Log.d("EmployeePage", "Employee added to Firestore");
                })
                .addOnFailureListener(e -> {
                    Log.e("EmployeePage", e.getMessage());
                });
    }

    private void getEmployees() {
        // Clear the list to fetch fresh data
        employeeList.clear();
        employeesAdapter.notifyDataSetChanged();

        // Fetch employees from Firestore
        firestore.collection("employees")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to an Employee object and add it to the list
                            Employee employee = document.toObject(Employee.class);
                            employeeList.add(employee);
                        }
                        // Notify adapter of the new data
                        employeesAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("EmployeePage", "Error getting documents: " + task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("EmployeePage", e.getMessage());
                });
    }
}
