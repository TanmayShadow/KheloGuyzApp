package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView profileName,profileEmail,profilePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        profileName=(TextView) findViewById(R.id.profile_name);
        profileEmail=(TextView)findViewById(R.id.profile_email);
        profilePhone=(TextView)findViewById(R.id.profile_phone);

        sharedPreferences = getSharedPreferences("AccountDetails",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","NULL");
        String email = sharedPreferences.getString("email","NULL");
        String phone = sharedPreferences.getString("phone","NULL");

        profileName.setText(name);
        profileEmail.setText(email);
        profilePhone.setText(phone);
    }

    public void goToHomePage(View view) {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void logoutFunction(View view) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("logged","false");
            editor.putString("u_id","");
            editor.putString("email","");
            editor.putString("name","");
            editor.putString("phone","");
            editor.apply();
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
    }

    public void goToEventPage(View view) {
        Intent i = new Intent(this,EventActivity.class);
        startActivity(i);
        finish();
    }
}