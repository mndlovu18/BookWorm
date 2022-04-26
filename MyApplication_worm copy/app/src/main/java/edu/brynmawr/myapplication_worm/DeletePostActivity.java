package edu.brynmawr.myapplication_worm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeletePostActivity extends AppCompatActivity {
    Button delete_submit, cancel_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        delete_submit = findViewById(R.id.delete_submit);
        cancel_delete = findViewById(R.id.cancel_delete);

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
                        //go back to the main activity
                        Intent intent = new Intent(DeletePostActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(DeletePostActivity.this, MainActivity.class);
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
                Intent intent = new Intent(DeletePostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        //set the onclick listener for the submit button
        delete_submit.setOnClickListener(submitButtonListener);
        //set the onclick listener for the cancel button
        cancel_delete.setOnClickListener(cancelButtonListener);
    }
}