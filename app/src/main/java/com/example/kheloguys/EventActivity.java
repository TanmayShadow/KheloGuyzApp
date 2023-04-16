package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    String city,date,games;
    Spinner dynamicSpinner;
    CheckBox cricket,football,badminton,kabaddi,khokho,chess,swimming,baseball;
    EditText dateEdit;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        cricket = (CheckBox)findViewById(R.id.CRICKET);
        football = (CheckBox)findViewById(R.id.FOOTBALL);
        badminton = (CheckBox)findViewById(R.id.BADMINTON);
        kabaddi = (CheckBox)findViewById(R.id.KABADDI);
        khokho = (CheckBox)findViewById(R.id.KHO_KHO);
        chess = (CheckBox)findViewById(R.id.CHESS);
        swimming = (CheckBox)findViewById(R.id.SWIMMING);
        baseball = (CheckBox)findViewById(R.id.BASEBALL);
        dateEdit = (EditText)findViewById(R.id.event_date);

        Spinner s = (Spinner) findViewById(R.id.autoCompleteTextView);
        dynamicSpinner = (Spinner) findViewById(R.id.autoCompleteTextView);

        String[] items = new String[] { "PUNE", "MUMBAI", "LATUR","UDGIR","NAGPUR" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

    }

    public void goToHome(View view) {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();

    }

    public void addEventFunction(View view) {
        sharedPreferences = getSharedPreferences("AccountDetails",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","NULL");
        String email = sharedPreferences.getString("email","NULL");
        String phone = sharedPreferences.getString("phone","NULL");
        games="";
        city= dynamicSpinner.getSelectedItem().toString();
        date=dateEdit.getText().toString();
        if(cricket.isChecked())
            games=games+"cricket ";
        if(football.isChecked())
            games=games+"football ";
        if(badminton.isChecked())
            games=games+"badminton ";
        if(kabaddi.isChecked())
            games=games+"kabaddi ";
        if(khokho.isChecked())
            games=games+"khokho ";
        if(chess.isChecked())
            games=games+"chess ";
        if(swimming.isChecked())
            games=games+"swimming ";
        if(baseball.isChecked())
            games=games+"baseball ";

        if(!games.equals("") && !date.equals("")) {
            insertDataToCloud(name,email,phone,games,date,city);
        }

        else
            ((EditText)findViewById(R.id.event_date)).setError("Fill all the fields");
    }

    void insertDataToCloud(String u_name,String u_email,String u_phone,String games,String city,String date1)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://34.145.214.255/event.php";
//        pg.setVisibility(ProgressBar.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Getting JSON response
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("success");
                            String msg = jsonResponse.getString("register");

//                            After successful registration user will be redirected to login page
                            Intent i = new Intent(getApplicationContext(),EventBookActivity.class);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Response:"+error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                Log.d("Response_Error",error.getLocalizedMessage());
                Intent i = new Intent(getApplicationContext(),EventBookActivity.class);
                startActivity(i);
                finish();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("name", u_name.toString());
                paramV.put("email",u_email.toString());
                paramV.put("phone",u_phone.toString());
                paramV.put("games",games.toString());
                paramV.put("city",city.toString());
                paramV.put("date",date1.toString());
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}