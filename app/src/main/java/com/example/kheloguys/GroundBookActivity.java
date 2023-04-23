package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GroundBookActivity extends AppCompatActivity {

    Intent i;
    String st_name,st_status,st_price,st_location,st_email,st_phone,st_image;
    TextView name,status,price,location,email;
    ImageView image;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_book);

        i = getIntent();
        st_name=i.getStringExtra("name");
        st_status=i.getStringExtra("status");
        st_price = i.getStringExtra("price");
        st_location=i.getStringExtra("location");
        st_email=i.getStringExtra("email");
        st_phone = i.getStringExtra("phone");
        st_image = i.getStringExtra("imgurl");



        name = (TextView)findViewById(R.id.stadium_name);
        status = (TextView)findViewById(R.id.stadium_time);
        price = (TextView) findViewById(R.id.stadium_price);
        location = (TextView)findViewById(R.id.stadium_location);
        email = (TextView)findViewById(R.id.stadium_email);

        name.setText(st_name);
        status.setText(st_status);
        price.setText(st_price);
        location.setText(st_location);
        email.setText(st_email);

        image = (ImageView)findViewById(R.id.reactanglea);
        Log.d("ImageURL",st_image);
        new ImageLoadTask(st_image, image).execute();


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri mapUri = Uri.parse("geo:0,0?q="+Uri.encode(location.getText().toString()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    public void goBackToSearch(View view) {
        Intent i = new Intent(getApplicationContext(),SearchActivity.class);
        i.putExtra("search","");
        startActivity(i);
        finish();
    }

    public void callToBook(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+st_phone));
        startActivity(callIntent);
    }

}