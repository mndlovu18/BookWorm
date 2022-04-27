package edu.brynmawr.myapplication_worm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private static final String TAG = "PostsActivity"; //TAG is a constant that is used to identify
    // the class in the logcat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls the superclass onCreate method
        setContentView(R.layout.activity_posts); //sets the content view to the activity_main layout
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_posts); //finds the drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open,
                R.string.Close); //creates a new action bar drawer toggle
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //adds the action bar drawer toggle
        // to the drawer layout
        actionBarDrawerToggle.syncState(); //syncs the state of the action bar drawer toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //sets the action bar to display the
        // home button

        navigationView = (NavigationView)findViewById(R.id.nv); //finds the navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { //sets the navigation view to listen for clicks
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId(); //gets the id of the item that was clicked
                switch(id) //switch statement to determine which item was clicked
                {
                    case R.id.home:
                        switchActivities(MainActivity.class); //switches to the home activity
                        break; //breaks out of the switch statement
                    case R.id.viewposts:
                        switchActivities(PostsActivity.class); //switches to the posts activity
                        break; //breaks out of the switch statement
                    case R.id.createpost:
                        switchActivities(CreatePostActivity.class); //switches to the create post activity
                        break; //breaks out of the switch statement
                    case R.id.deletepost:
                        switchActivities(DeletePostActivity.class); //switches to the posts activity
                        break; //breaks out of the switch statement
                    default:
                        return true; //returns true to indicate that the item was selected
                }
                return true;
            }
        });

        new MyTask().execute(); //creates a new instance of the MyTask class and executes it, the
        // methods in MyTask are executed in the background

    }

    private void switchActivities(Class<?> cls){
        Intent switchActivityIntent = new Intent(this, cls); //creates a new intent to switch activities
        startActivity(switchActivityIntent); //starts the activity
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    /** Create a Post class called when user clicks the button to create a post */
    public void createPost(View view){
        Intent intent = new Intent(this, CreatePostActivity.class); //creates an intent to start the CreatePostActivity
        startActivity(intent); //starts the CreatePostActivity
    }

    /** Delete a Post class called when user clicks the button */
    public void deletePost(View view){
        Intent intent = new Intent(this, DeletePostActivity.class); //creates an intent to start the DeletePostActivity
        startActivity(intent); //starts the DeletePostActivity
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
            Postadapter adapter = new Postadapter(PostsActivity.this, R.layout.adapter_view,posts); //creates a new instance of the Postadapter class and assigns it to the adapter variable, which is used to display the posts
            list.setAdapter(adapter); //sets the adapter for the list view which displays the posts
        }
    }
}