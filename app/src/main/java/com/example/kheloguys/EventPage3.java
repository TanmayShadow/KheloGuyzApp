package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EventPage3 extends AppCompatActivity {

    EditText editext;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page3);
    }
    public void nextPage(View view) {
        if (editext.getText().toString().isEmpty()) {
            editext.setError("Enter the No. of Teams");
        }
        else {
            Intent i = new Intent(EventPage3.this,EventPage4.class);
            startActivity(i);
        }
    }

}