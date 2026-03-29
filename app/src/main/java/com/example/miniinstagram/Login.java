package com.example.miniinstagram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity { //inheritance

    EditText editLoginUsername, editLoginPassword; //references
    Button btnLogin;
    TextView tvGoRegister;

    @Override //overriding a method from a parent class.
    protected void onCreate(Bundle savedInstanceState) { //polymorphism
        super.onCreate(savedInstanceState); //android's initial setup
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login); //the xml file for the ui

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE); //the stored data

        if (prefs.getBoolean("loggedIn", false)) {
            //if the user is already logged in, mainActivity will be opened instead.
            startActivity(new Intent(Login.this, MainActivity.class));
            //open the mainActivity to prevent continuous logging in
            finish();
            return;
        }

        editLoginUsername = findViewById(R.id.editLoginUsername); //connect the xml components with the variables
        editLoginPassword = findViewById(R.id.editLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoRegister = findViewById(R.id.tvGoRegister);

        btnLogin.setOnClickListener(v -> loginUser()); //when the button is clicked, it calls the method.
        tvGoRegister.setOnClickListener(v -> goRegister());
    }

    private void loginUser() { //encapsulation
        String username = editLoginUsername.getText().toString().trim(); //read user input. trim: delete excess spaces
        String password = editLoginPassword.getText().toString().trim();

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE); //load stored data

        String savedUser = prefs.getString("username", ""); //get stored data
        String savedPass = prefs.getString("password", "");

        if (username.equals(savedUser) && password.equals(savedPass)) { //if the inputs match the loaded data, log in.

            prefs.edit().putBoolean("loggedIn", true).apply();

            startActivity(new Intent(Login.this, MainActivity.class)); //start mainActivity
            finish(); //finish log in so user won't be able to go back to the login screen without logging out
        } else {
            Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show(); //inform the user
        }
    }

    private void goRegister() { //encapsulation
        startActivity(new Intent(Login.this, Register.class)); //move to the registerActivity
    }
}
