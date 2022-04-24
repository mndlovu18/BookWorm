package edu.brynmawr.myapplication_worm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        Toast.makeText(MainActivity.this, "Fetching posts",Toast.LENGTH_SHORT).show();

        new MyTask().execute();
    }

    private class MyTask
            extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(Void... voids) {
            Client client = new Client();
            return client.getPosts();
        }
        @Override
        protected void onPostExecute(List<Post> result)
        {
            super.onPostExecute(result);
            // do something with the result
            ListView list = (ListView) findViewById(R.id.theList);
            ArrayList<Post> posts = new ArrayList<Post>();
            posts.addAll(result);
            System.out.println(posts);
            Postadapter adapter = new Postadapter(MainActivity.this, R.layout.adapter_view,posts);
            list.setAdapter(adapter);
        }
    }
}