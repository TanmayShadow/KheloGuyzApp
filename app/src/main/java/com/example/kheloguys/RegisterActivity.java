package com.example.kheloguys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName,email,phone,password,confirmPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName=(EditText) findViewById(R.id.editTextTextPersonName);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        phone = (EditText) findViewById(R.id.editTextPhone);
        password=(EditText) findViewById(R.id.editTextTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextTextPassword2);

        firebaseAuth = FirebaseAuth.getInstance();

        pg = (ProgressBar) findViewById(R.id.progressBar);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showPassword(motionEvent,password);
                return false;
            }
        });

        confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showPassword(motionEvent,confirmPassword);
                return false;
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password.setError(null);
                password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dark_lock,0,R.drawable.baseline_visibility_24,0);

            }
        });

    }

    private void showPassword(MotionEvent motionEvent, EditText pass)
    {
        final int right=2;
        if(motionEvent.getAction()==MotionEvent.ACTION_UP)
        {
            if(motionEvent.getRawX()>=pass.getRight()-pass.getCompoundDrawables()[right].getBounds().width())
            {
                if(pass.getTag().equals("invisible"))
                {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dark_lock,0,R.drawable.baseline_visibility_24,0);
                    pass.setTag("visible");
                }
                else
                {
                    pass.setInputType(129);
                    pass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dark_lock,0,R.drawable.baseline_visibility_off_24,0);
                    pass.setTag("invisible");
                }
            }
        }
    }

    public void sendToLogin(View view) {
        Intent i = new Intent(RegisterActivity.this,Login.class);
        startActivity(i);
    }

    public void registerFunction(View view) {
        String userFullName = String.valueOf(fullName.getText());
        String userEmail = String.valueOf(email.getText());
        String userPhone = String.valueOf(phone.getText());
        String userPassword = String.valueOf(password.getText());
        String userConfirmPassword = String.valueOf(confirmPassword.getText());

        userFullName.trim();
        userEmail.trim();
        userPhone.trim();
        userPassword.trim();
        userConfirmPassword.trim();


//        Check whether any field is empty or not
        if(userFullName.equals(""))
            fullName.setError("Enter Full Name");
        if(userPhone.equals(""))
            phone.setError("Enter Phone no,");
        if(userPassword.equals(""))
            password.setError("Enter Password");
        if(userConfirmPassword.equals(""))
            confirmPassword.setError("Re-enter your password");
        if(userPhone.length()!=10)
            phone.setError("Invalid phone number");
        String Expn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        if(!userEmail.matches(Expn) || userEmail.equals(""))
            email.setError("Invalid Email");


//        Check the password length is greater than 8 characters or not
        if(userPassword.length()>=8)
        {
//            Check password and confirm password matches or not
            if(userPassword.equals(userConfirmPassword))
            {
//                Show progress bar until registration completes
                pg.setVisibility(ProgressBar.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {

                                if (!task.isSuccessful())
                                {

                                    Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //user registered on google firebase
                                    insertDataToCloud(userFullName,userEmail,userPhone,userPassword);
                                }
//                              Hide Progress bar after registration completes
//                                pg.setVisibility(ProgressBar.INVISIBLE);
                            }
                        });
            }
//            if password not matches show error
            else
            {
                confirmPassword.setError("Password Not matched");
            }
        }
//        if password length is less than 8 character then show error
        else
        {
            password.setError("Password length must be greater than 8 characters");
        }


    }

    void insertDataToCloud(String u_name,String u_email,String u_phone,String u_password)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://34.145.214.255/register.php";
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
                            pg.setVisibility(ProgressBar.GONE);
                            Intent i = new Intent(getApplicationContext(),Login.class);
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
                pg.setVisibility(ProgressBar.GONE);
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("name", u_name);
                paramV.put("email",u_email);
                paramV.put("phone",u_phone);
                paramV.put("password",u_password);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}