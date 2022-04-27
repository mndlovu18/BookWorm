package edu.brynmawr.myapplication_worm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        submit = findViewById(R.id.submit);

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_create_post); //finds the drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close); //creates
        // a new action bar drawer toggle
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

        //onclick listener for submit button
        View.OnClickListener submitButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //get the title from the title text field
                EditText editTitle = (EditText) findViewById(R.id.title);
                String title = editTitle.getText().toString();

                //get the author from the author text field
                EditText editAuthor = (EditText) findViewById(R.id.author);
                String author = editAuthor.getText().toString();

                //get the content from the content text field
                EditText editContent = (EditText) findViewById(R.id.content);
                String content = editContent.getText().toString();

                //create random id
                Long id = (long) (Math.random() * 1000000);

                //generate time stamp
                String created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                //create a new post
                Post post = new Post(id, title, author, content, created);
                //print post to console
                Log.d("CreatePostActivity", "Post: " + post.toString());
                //toast the post
                Toast.makeText(CreatePostActivity.this, post.toString(), Toast.LENGTH_LONG).show();

                //add the post to the database
                Client client = new Client();

                client.savePost(post);
                //Database.addPost(post);

                //go back to the main activity
                finish();
            }
        };

        //set the onclick listener for the submit button
        submit.setOnClickListener(submitButtonListener);
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


}