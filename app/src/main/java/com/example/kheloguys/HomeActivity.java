package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    GridView gridView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int images[]={R.drawable.basketball,R.drawable.football,R.drawable.cricket,R.drawable.swimming,
                        R.drawable.badminton,R.drawable.kabaddi,R.drawable.skydiving,R.drawable.khokho,R.drawable.wrestling};
        String gameName[]={"basketball","football","cricket","swimming","badminton","kabaddi","skydiving","khokho","wrestling"};
        gridView = findViewById(R.id.gridview);
        MyGridAdapter myGridAdapter = new MyGridAdapter(HomeActivity.this,gameName,images);
        gridView.setAdapter(myGridAdapter);
        sharedPreferences = getSharedPreferences("AccountDetails",MODE_PRIVATE);
//        check whether user is already logged in or not
        if(!sharedPreferences.getString("logged","false").equals("true"))
        {
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            finish();
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                intent.putExtra("search",gameName[i]);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("AccountDetails",MODE_PRIVATE);
//        Toast.makeText(getApplicationContext(),sharedPreferences.getString("name","NULL"),Toast.LENGTH_LONG).show();

    }

    public void searchGroundFunction(View view) {
        Intent i = new Intent(getApplicationContext(),SearchActivity.class);
        i.putExtra("search","");
        startActivity(i);
    }

    public void goToAccountPage(View view) {
        Intent i = new Intent(getApplicationContext(),AccountActivity.class);
        startActivity(i);
    }

    public void goToEventPage(View view) {
        Intent i = new Intent(getApplicationContext(),EventActivity.class);
        startActivity(i);
    }
}