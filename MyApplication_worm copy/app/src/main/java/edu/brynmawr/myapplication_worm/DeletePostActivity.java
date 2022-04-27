package edu.brynmawr.myapplication_worm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

public class DeletePostActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    Button delete_submit, cancel_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        delete_submit = findViewById(R.id.delete_submit);
        cancel_delete = findViewById(R.id.cancel_delete);

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_delete); //finds the drawer layout
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
                //get the post_id_input from the EditText
                EditText postId = (EditText) findViewById(R.id.post_id_input);
                //create a dialogue box to confirm deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(DeletePostActivity.this);
                builder.setMessage("Are you sure you want to delete this post?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Client client = new Client();
                        client.deletePost(Long.parseLong(postId.getText().toString()));
                        Intent intent = new Intent(DeletePostActivity.this, PostsActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(DeletePostActivity.this, PostsActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };
        //onclick listener for cancel button
        View.OnClickListener cancelButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //go back to the main activity
                Intent intent = new Intent(DeletePostActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        };

        //set the onclick listener for the submit button
        delete_submit.setOnClickListener(submitButtonListener);
        //set the onclick listener for the cancel button
        cancel_delete.setOnClickListener(cancelButtonListener);
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