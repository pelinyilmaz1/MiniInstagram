package com.example.miniinstagram;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity { //inheritance

    EditText editUsername, editPassword; //references
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //android's initial setup
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register); //the xml file for the ui

        editUsername = findViewById(R.id.editUsername); //connect the xml components with the variables
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser()); //when the button is clicked, it calls the method.
    }

    private void registerUser() { //encapsulation
        String username = editUsername.getText().toString().trim(); //read user input. trim: delete excess spaces
        String password = editPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            //if the input is empty, show an informing message
            return;
        }

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE); //stored data
        prefs.edit()
                .putString("username", username) //save the input
                .putString("password", password)
                .putBoolean("loggedIn", false) // user must login manually
                .apply(); //save

        Toast.makeText(this, "Registered successfully. Please login.", Toast.LENGTH_SHORT).show(); //error check.

        finish(); // go back to login
    }
}