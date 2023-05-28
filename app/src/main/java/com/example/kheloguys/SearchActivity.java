package com.example.kheloguys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    GroundAdapter groundAdapter;
    SearchView searchView,searchSport;
    String searchLocation,searchGame;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.listview);
        searchView =(SearchView) findViewById(R.id.Search);
        searchSport=(SearchView) findViewById(R.id.SearchGame);
        Intent in = getIntent();
        String search = in.getStringExtra("search");
        searchLocation="";
        searchGame=search;

        getDataFromCloud(searchLocation,search);


//        arrayList.add(new Ground(R.drawable.img,"PCMC","Open","1000/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"PWD","Closed","500/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"Nehru Stadium","Open","1500/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"PYC","Open","2000/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"Poona Club Ground","Closed","1200/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"Deccan Gymkhana","Closed","750/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"Army Ground","Open","1050/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"Gandhi Stadium","Open","500/- Hour"));
//        arrayList.add(new Ground(R.drawable.img,"Shivaji Maidan","Closed","1700/- Hour"));

//        groundAdapter = new GroundAdapter(this, R.layout.list_item,arrayList);
//        listView.setAdapter(groundAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                groundAdapter.getFilter().filter(s);
                searchLocation=s;
                getDataFromCloud(searchLocation,searchGame);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                getDataFromCloud(s);
                return false;
            }
        });

        searchSport.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchGame = query;
                getDataFromCloud(searchLocation,searchGame);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }

    void getDataFromCloud(String search,String game)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://34.145.214.255/searchground.php?search="+search+"&game="+game;
//        pg.setVisibility(ProgressBar.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {
//                        Getting JSON response

                        try {
                            ArrayList<Ground> arrayList = new ArrayList<>();
                                for(int i=0;i<response.length();i++)
                                {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String ground_name = jsonObject.getString("ground_name");
                                    String location = jsonObject.getString("location");
                                    String time = jsonObject.getString("open_time")+"\n"+jsonObject.getString("close_time");
                                    String phone = jsonObject.getString("phone");
                                    String email = jsonObject.getString("email");
                                    String image = jsonObject.getString("image");
                                    String games = jsonObject.getString("games");
                                    int price = jsonObject.getInt("price");
                                    games = games+"\n"+price;



                                    arrayList.add(new Ground(R.drawable.img,ground_name,time,"100",location,phone,email,image,games));

                                }
                            groundAdapter = new GroundAdapter(getApplicationContext(), R.layout.list_item,arrayList);
                            listView.setAdapter(groundAdapter);
                        } catch (JSONException e) {
                            Log.d("Tanmay",e.getLocalizedMessage());
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Response:"+error,Toast.LENGTH_LONG).show();
                Log.d("Response_Error",error.getLocalizedMessage());
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("search",search);
                paramV.put("game",game);
                return paramV;
            }
        };
        queue.add(jsonArrayRequest);
    }

}