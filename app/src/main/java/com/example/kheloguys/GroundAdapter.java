package com.example.kheloguys;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroundAdapter extends ArrayAdapter<Ground> {
    private Context context;
    private int Resources;
    public GroundAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ground> objects) {
        super(context, resource, objects);
        this.context = context;
        this.Resources= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(Resources,parent,false);
        CircleImageView circleImageView = convertView.findViewById(R.id.action_image);
        TextView textView = convertView.findViewById(R.id.GroundName);
        TextView textView1 = convertView.findViewById(R.id.Status);
        TextView textView2 = convertView.findViewById(R.id.price);

        circleImageView.setImageResource(getItem(position).getImage());
        textView.setText(getItem(position).getName());
        textView1.setText(getItem(position).getStatus());
        textView2.setText(getItem(position).getPrice());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling goToGroundBook function to redirect on GroundBookActivity
                Intent i = new Intent(context.getApplicationContext(),GroundBookActivity.class);
                i.putExtra("name",textView.getText().toString());
                i.putExtra("status",textView1.getText().toString());
                i.putExtra("price",textView2.getText().toString());
                i.putExtra("imgurl",getItem(position).getImageUrl());
                i.putExtra("location",getItem(position).getLocation());
                i.putExtra("email",getItem(position).getEmail());
                i.putExtra("phone",getItem(position).getPhone());

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
