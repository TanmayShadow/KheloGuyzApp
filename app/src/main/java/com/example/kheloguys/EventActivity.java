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
        games="sbc";
        city= dynamicSpinner.getSelectedItem().toString();
        date=((EditText)findViewById(R.id.event_date)).toString();
//        if(cricket.isSelected())
//            games=games+"cricket ";
//        if(football.isSelected())
//            games=games+"football ";
//        if(badminton.isSelected())
//            games=games+"badminton ";
//        if(kabaddi.isSelected())
//            games=games+"kabaddi ";
//        if(khokho.isSelected())
//            games=games+"khokho ";
//        if(chess.isSelected())
//            games=games+"chess ";
//        if(swimming.isSelected())
//            games=games+"swimming ";
//        if(baseball.isSelected())
//            games=games+"baseball ";
        if(!games.equals("") && !date.equals("")) {
           try {
               Intent i = new Intent(getApplicationContext(),EventBookActivity.class);
               startActivity(i);
               finish();
//               insertDataToCloud(name, email, phone, games, date, city);
           }
           catch (Exception e)
           {
               Log.d("Server_Error_500",e.getLocalizedMessage());
               Intent i = new Intent(getApplicationContext(),EventBookActivity.class);
               startActivity(i);
               finish();
           }
//            Intent i = new Intent(getApplicationContext(), EventBookActivity.class);
//            startActivity(i);
//            finish();
        }
//        insertDataToCloud(name,email,phone,games,date,city);
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
                            Toast.makeText(getApplicationContext(),"Registration "+msg,Toast.LENGTH_LONG).show();

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