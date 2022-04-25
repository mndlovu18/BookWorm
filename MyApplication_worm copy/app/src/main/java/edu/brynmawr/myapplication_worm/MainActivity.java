package edu.brynmawr.myapplication_worm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; //TAG is a constant that is used to identify the class in the logcat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls the superclass onCreate method
        setContentView(R.layout.activity_main); //sets the content view to the activity_main layout
        Log.d(TAG, "onCreate: Started."); //logs the creation of the activity

        Toast.makeText(MainActivity.this, "Fetching posts",Toast.LENGTH_SHORT).show(); //creates a toast to notify the user that the posts are being fetched

        new MyTask().execute(); //creates a new instance of the MyTask class and executes it, the methods in MyTask are executed in the background
    }
    
    /** Create a Post class called when user clicks the button to create a post */
    public void createPost(View view){
        Intent intent = new Intent(this, CreatePostActivity.class); //creates an intent to start the CreatePostActivity
        startActivity(intent); //starts the CreatePostActivity
    }

    private class MyTask
            extends AsyncTask<Void, Void, List<Post>> { //creates a new class that extends AsyncTask

        @Override
        protected List<Post> doInBackground(Void... voids) { //overrides the doInBackground method
            Client client = new Client(); //creates a new instance of the Client class
            return client.getPosts(); //returns the list of posts
        }

        @Override
        protected void onPostExecute(List<Post> result) 
        { //overrides the onPostExecute method which is executed after the doInBackground method to display the posts
            super.onPostExecute(result); //calls the superclass onPostExecute method to execute the code in the method
            // do something with the result
            ListView list = (ListView) findViewById(R.id.theList); //creates a new instance of the ListView class and assigns it to the list variable
            ArrayList<Post> posts = new ArrayList<Post>(); //creates a new instance of the ArrayList class and assigns it to the posts variable
            posts.addAll(result); //adds the list of posts to the posts ArrayList
            System.out.println(posts); //prints the list of posts to the console
            Postadapter adapter = new Postadapter(MainActivity.this, R.layout.adapter_view,posts); //creates a new instance of the Postadapter class and assigns it to the adapter variable, which is used to display the posts
            list.setAdapter(adapter); //sets the adapter for the list view which displays the posts
        }
    }
}