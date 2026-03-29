package com.example.miniinstagram;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity { //inheritance

    EditText editUsername, editBio;
    Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Calls the constructor of the parent class (AppCompatActivity). android's initial setup
        setContentView(R.layout.activity_profile); //loading xml.

        editUsername = findViewById(R.id.editUsername); //connecting the xml views to java variables.
        editBio = findViewById(R.id.editBio);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        if (editUsername == null || editBio == null || btnSaveProfile == null) { //error check with toast.
            Toast.makeText(this, "Profile layout error", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        loadProfile();

        btnSaveProfile.setOnClickListener(v -> saveProfile()); //when the button is clicked, it calls the method.
    }

    private void saveProfile() { //encapsulation: hidden from other classes
        SharedPreferences prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
        //stored data
        prefs.edit()
                .putString("username", editUsername.getText().toString())
                .putString("bio", editBio.getText().toString())
                .apply();

        Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show(); //for error check.
    }

    private void loadProfile() { //encapsulation: hidden from other classes
        SharedPreferences prefs = getSharedPreferences("ProfileData", MODE_PRIVATE); //load stored data
        editUsername.setText(prefs.getString("username", ""));
        editBio.setText(prefs.getString("bio", ""));  //to retrieve the saved profile data.
    }
}

/* SharedPreferences is an Android class used to store small amounts of data persistently.
The data is stored as key-value pairs, like a dictionary or map.
Examples of what you can store:
Username
User preferences
Settings like dark mode on/off */