package com.example.kheloguys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText loginEmail,loginPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar loginProgress;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = (EditText) findViewById(R.id.email);
        loginPassword = (EditText) findViewById(R.id.password);
        loginProgress = (ProgressBar) findViewById(R.id.loginProgressbar);

        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("AccountDetails",MODE_PRIVATE);
//        check whether user is already logged in or not
        if(sharedPreferences.getString("logged","false").equals("true"))
        {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
            finish();
        }



        loginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginEmail.setError(null);
            }
        });

        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPassword.setError(null);
            }
        });

        loginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                {
                    if(motionEvent.getRawX()>=loginPassword.getRight()-loginPassword.getCompoundDrawables()[right].getBounds().width()-30)
                    {
                        if(loginPassword.getTag().equals("invisible"))
                        {
                            loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            loginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dark_lock,0,R.drawable.baseline_visibility_24,0);
                            loginPassword.setTag("visible");
                        }
                        else
                        {
                            loginPassword.setInputType(129);
                            loginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dark_lock,0,R.drawable.baseline_visibility_off_24,0);
                            loginPassword.setTag("invisible");
                        }
                    }
                }

                return false;
            }
        });
    }

    public void sendToRegister(View view) {
        Intent i = new Intent(Login.this,RegisterActivity.class);
        startActivity(i);
    }

    public void loginFunction(View view) {
        String useremail = String.valueOf(loginEmail.getText());
        String userpassword = String.valueOf(loginPassword.getText());
        useremail.trim();
        userpassword.trim();
//      Check email pattern
        String Expn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        if(!useremail.matches(Expn) || useremail.equals(""))
            loginEmail.setError("Invalid Email");
//      check password length
        if(userpassword.length()>=8)
        {
            //login user
            loginProgress.setVisibility(ProgressBar.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(useremail,userpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() 
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) 
                        {

                            if (!task.isSuccessful()) 
                            {
                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            } 
                            else 
                            {
                                getDataFromCloud(useremail,userpassword);
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                            loginProgress.setVisibility(ProgressBar.INVISIBLE);
                        }
                    });
        }
        else
        {
            loginPassword.setError("Password length must be greater than 8 characters");
        }
    }

    public void goToForgotPassword(View view)
    {
        Intent i = new Intent(Login.this,ForgotPasswordActivity.class);
        startActivity(i);
    }

    void getDataFromCloud(String u_email,String u_password)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://34.145.214.255/login.php";
//        pg.setVisibility(ProgressBar.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Getting JSON response

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String u_id = jsonResponse.getString("id");
                            String email = jsonResponse.getString("email");
                            String name = jsonResponse.getString("name");
                            String phone = jsonResponse.getString("phone");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("logged","true");
                            editor.putString("u_id",u_id);
                            editor.putString("email",email);
                            editor.putString("name",name);
                            editor.putString("phone",phone);
                            editor.apply();

//                            transfer user to home page
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                            finish();

                        } catch (JSONException e) {
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
                paramV.put("email",u_email);
                paramV.put("password",u_password);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}