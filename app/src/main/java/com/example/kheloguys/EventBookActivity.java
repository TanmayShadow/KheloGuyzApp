package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EventBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_book);
    }

    public void goToHome(View view) {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }
}