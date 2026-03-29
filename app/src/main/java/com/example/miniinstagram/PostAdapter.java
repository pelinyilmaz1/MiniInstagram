package com.example.miniinstagram;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> { //inheritance

    private final List<Post> postList; //encapsulation

    public PostAdapter(List<Post> postList) { //constructor
        this.postList = postList;
    }

    @NonNull //the value will never be null
    @Override //overriding a method from a parent class.
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //polymorphism
        // when the recyclerview needs a new item view that doesn’t already exist in memory.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_post, parent, false);
        //to turn the xml into a seeable object on screen. (in this case, photo posts)

        return new PostViewHolder(view); //wrap it in a ViewHolder so RecyclerView can manage it efficiently.
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) { //polymorphism
        Post post = postList.get(position);

        holder.txtUsername.setText(post.getUsername());
        holder.txtCaption.setText(post.getCaption());
        //to show the username and the caption both properly when an account makes a post

        Uri imageUri = post.getImageUri();
        /*Uniform Resource Identifier: works like a pointer for images.
        shows the app where the file is without needing the full path.*/
        if (imageUri != null) {
            holder.imgPost.setVisibility(View.VISIBLE); //if there is an image, show. if not, gone.
            holder.imgPost.setImageURI(imageUri);
        } else {
            holder.imgPost.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() { //polymorphism + RecyclerView calls this method internally to know how many items to render.
        return postList.size(); //returns the number of posts
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        //static nested class. + logically belongs to the outer class but doesn’t need to access its instance variables directly.
        TextView txtUsername, txtCaption; //the fields of this class
        ImageView imgPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            //Calls the constructor of the parent class (RecyclerView.ViewHolder) so it can manage the view internally.
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtCaption = itemView.findViewById(R.id.txtCaption);
            imgPost = itemView.findViewById(R.id.imgPost);
            /*finds the actual ui components in itemView and assigns them to the fields.
            efficiency: by storing references, there is no need to call findViewById repeatedly while scrolling.*/
        }
    }
}

/* ViewHolder classes are tightly coupled with the adapter.
They are only used inside the adapter, so putting them inside the adapter:
Keeps the code organized
Makes it clear that the ViewHolder is related to this adapter only */
