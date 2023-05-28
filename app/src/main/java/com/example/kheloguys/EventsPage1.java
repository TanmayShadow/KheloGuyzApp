package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EventsPage1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page1);
    }

    public void football(View view) {
        sendToPage2("football");
    }

    public void basketball(View view) {
        sendToPage2("basketball");
    }

    public void cricket(View view) {
        sendToPage2("cricket");
    }

    public void kabaddi(View view) {
        sendToPage2("kabaddi");
    }

    public void kusti(View view) {
        sendToPage2("kusti");
    }

    public void Scuba(View view) {
        sendToPage2("Scuba Divining");
    }

    public void badmitton(View view) {
        sendToPage2("badmintton");
    }

    public void Swimming(View view) {
        sendToPage2("swimming");
    }

    public void TT(View view) {
        sendToPage2("Table Tennis");
    }

    void sendToPage2(String game)
    {
        Intent i = new Intent(this,EventPage2.class);
        startActivity(i);
    }
}