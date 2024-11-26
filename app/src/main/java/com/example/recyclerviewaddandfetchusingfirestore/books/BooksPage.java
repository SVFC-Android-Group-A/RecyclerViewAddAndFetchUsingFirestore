package com.example.recyclerviewaddandfetchusingfirestore.books;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewaddandfetchusingfirestore.adapter.BooksAdapter;
import com.example.recyclerviewaddandfetchusingfirestore.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BooksPage extends AppCompatActivity {

    // Views and Firestore reference
    RecyclerView rvBook;
    List<Books> bookList = new ArrayList<>();
    BooksAdapter booksAdapter;

    EditText etTitle, etAuthor, etGenre;
    Button btnAddItem, btnGetItem;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        rvBook = findViewById(R.id.rv_book);
        etTitle = findViewById(R.id.et_title);
        etAuthor = findViewById(R.id.et_author);
        etGenre = findViewById(R.id.et_genre);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnGetItem = findViewById(R.id.btn_get_item);

        // Set up RecyclerView and adapter
        booksAdapter = new BooksAdapter(bookList);
        rvBook.setAdapter(booksAdapter);
        rvBook.setLayoutManager(new LinearLayoutManager(this));

        // Button listeners
        btnAddItem.setOnClickListener(v -> addFunction());
        btnGetItem.setOnClickListener(v -> getFunction());
    }

    private void addFunction() {
        // Validate inputs
        if (etTitle.getText().toString().isEmpty() ||
                etAuthor.getText().toString().isEmpty() ||
                etGenre.getText().toString().isEmpty()) {
            Log.d("MAIN", "All fields are required!");
            return;
        }

        // Create a new book object
        Books book = new Books(
                etTitle.getText().toString(),
                etAuthor.getText().toString(),
                etGenre.getText().toString()
        );

        // Add book to Firestore
        firestore.collection("books")
                .add(book)  // Adds book object to Firestore
                .addOnSuccessListener(documentReference -> {
                    // On success, add to the list and notify adapter
                    bookList.add(book);
                    booksAdapter.notifyItemInserted(bookList.size() - 1);
                    Log.d("MAIN", "Added book: " + book.getTitle() + " by " + book.getAuthor());
                })
                .addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }

    private void getFunction() {
        // Clear existing book list and prepare to fetch data
        bookList.clear();
        booksAdapter.notifyDataSetChanged();

        // Fetch books from Firestore
        firestore.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Loop through all documents returned and convert to Book object
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Books book = document.toObject(Books.class);  // Convert document to Book object
                            bookList.add(book);  // Add book to list
                        }
                        // Notify adapter of data change
                        booksAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("MAIN", "Error getting documents: " + task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MAIN", e.getMessage());
                });
    }
}
