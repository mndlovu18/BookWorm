package edu.brynmawr.myapplication_worm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //runs code your code + existing code in the parent class
        setContentView(R.layout.activity_main); //sets the layout to the activity_main.xml file
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main); //finds the drawer layout
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