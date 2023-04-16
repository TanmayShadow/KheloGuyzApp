package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("AccountDetails",MODE_PRIVATE);
    }

    public void sendToLogin()
    {
        Intent intent = new Intent(MainActivity.this,Login.class);
        intent.putExtra("From","Main Page");
        startActivity(intent);
//        finish();
    }

    public void sendToHome()
    {
        Intent i = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }


    public void goToNextPage(View view) {
        if(sharedPreferences.getString("logged","false").equals("true"))
        {
            sendToHome();
        }
        else
            sendToLogin();
    }
}