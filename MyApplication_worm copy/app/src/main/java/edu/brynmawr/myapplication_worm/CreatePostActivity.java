package edu.brynmawr.myapplication_worm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        submit = findViewById(R.id.submit);

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

}