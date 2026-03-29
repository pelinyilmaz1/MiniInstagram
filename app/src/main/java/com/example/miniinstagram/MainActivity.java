package com.example.miniinstagram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity { //inheritance

    Button btnLogout, btnAddPhoto, btnProfile; //declarations
    RecyclerView recyclerView;

    ArrayList<Post> postList;
    PostAdapter adapter;
    String username;
    ActivityResultLauncher<String> imagePicker = //to let the user pick a file from their device to post it in the app
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(), //getContent = pick a file
                    uri -> {
                        if (uri != null) { //if the user picked a post-
                            showCaptionDialog(uri); //-and the caption method got called.
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //android's initial setup
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE); //loading the current username
        username = prefs.getString("username", "user");

        btnLogout = findViewById(R.id.btnLogout); //connect the xml components with the variables
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnProfile = findViewById(R.id.btnProfile);
        recyclerView = findViewById(R.id.recyclerView);

        btnLogout.setOnClickListener(v -> logout()); //when the button is clicked, it calls the method.
        btnProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        //clicking profile button calls profileActivity

        postList = new ArrayList<>(); //initialize
        adapter = new PostAdapter(postList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //to show the posts in a vertical order. scrolling down the posts.
        recyclerView.setAdapter(adapter); //using the adapter to get the data and display it.

        btnAddPhoto.setOnClickListener(v -> imagePicker.launch("image/*"));
        //when the button is clicked, it opens the image picker
    }

    private void logout() { //encapsulation
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE); //to get the userdata
        prefs.edit().putBoolean("loggedIn", false).apply(); //changing the login status

        Intent intent = new Intent(MainActivity.this, Login.class);
        //to move from the main screen to the login activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        /*to ensure that user cannot go back to the mainActivity after logging out,
        which also means deleting the previous activities. */
        startActivity(intent); //go to the loginActivity
    }

    private void showCaptionDialog(Uri imageUri) { //encapsulation
        EditText captionInput = new EditText(this); //create a text input box.
        captionInput.setHint("Write a caption..."); //guide for the user

        new AlertDialog.Builder(this) //build a popup
                .setTitle("New Post")
                .setView(captionInput) //show the place where the text would be written in.
                .setPositiveButton("Post", (dialog, which) -> {
                    //click listener. when the user presses the Post button, run the code inside.
                    String caption = captionInput.getText().toString(); //store user's input text

                    Post post = new Post(username, caption, imageUri); //create a Post object
                    postList.add(0, post); //show the newly uploaded post at the top of the screen.
                    adapter.notifyDataSetChanged();
                    //notify the recyclerView that the data changed, so the new post can appear properly.
                })
                .setNegativeButton("Cancel", null) //close the action, nothing happens.
                .show(); //display the popup
    }
}