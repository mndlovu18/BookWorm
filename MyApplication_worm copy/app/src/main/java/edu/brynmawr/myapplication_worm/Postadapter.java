package edu.brynmawr.myapplication_worm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Postadapter extends ArrayAdapter<Post> {


    private Context mContext; //a reference to the activity that called this adapter
    private int mResource; //the resource id for the layout file

    /**
     * Holds variables in a View
     */
    private class ViewHolder { //holds the variables in a View, the view is the row in the list
        TextView id;
        TextView title;
        TextView name;
        TextView content;
        TextView created;

    }

    public Postadapter(Context context, int resource, ArrayList<Post> objects) { //constructor
        super(context, resource, objects); //call the constructor of the superclass which is ArrayAdapter
        mContext = context; //save the context which is the activity
        mResource = resource; //save the resource id which is the row layout
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //get the view of the row
        //get the post information
        Long id = getItem(position).getId();
        String title = getItem(position).getTitle();
        String name = getItem(position).getName();
        String content = getItem(position).getContent();
        String created = getItem(position).getCreated();



        //Create the person object with the information
        Post post = new Post(id,  title, name, content, created);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();

            holder.id = (TextView) convertView.findViewById(R.id.textView2);
            holder.title = (TextView) convertView.findViewById(R.id.textView3);
            holder.name = (TextView) convertView.findViewById(R.id.textView4);
            holder.content = (TextView) convertView.findViewById(R.id.textView5);
            holder.created = (TextView) convertView.findViewById(R.id.textView6);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        holder.id.setText(post.getId().toString());
        holder.title.setText(post.getTitle());
        holder.name.setText(post.getName());
        holder.content.setText(post.getContent());
        holder.created.setText(post.getCreated());


        return convertView; //return the view with the information set to the row
    }
}