package edu.brynmawr.bookworm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main2);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
//                        Toast.makeText(MainActivity.this, "Home",Toast.LENGTH_SHORT).show();
                        switchActivities(HomeActivity.class);
                        break;
                    case R.id.mybooks:
//                        Toast.makeText(MainActivity.this, "My Books",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.posts:
//                        Toast.makeText(MainActivity.this, "Created Posts",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });





//        switchToHomeActivity = findViewById(R.id.home);
//        switchToHomeActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                switchActivities();
//            }
//        });
    }
    private void switchActivities(Class<?> cls){
        Intent switchActivityIntent = new Intent(this, cls);
        startActivity(switchActivityIntent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}