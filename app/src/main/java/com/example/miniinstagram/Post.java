package com.example.miniinstagram;

import android.net.Uri;

public class Post {

    private String username; //encapsulation, all three of them
    private String caption;
    private Uri imageUri;

    public Post(String username, String caption, Uri imageUri) {
        //constructor to initialize values when a new Post object is created
        this.username = username;
        this.caption = caption;
        this.imageUri = imageUri;
    }

    public String getUsername() { //get methods for the fields.
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}