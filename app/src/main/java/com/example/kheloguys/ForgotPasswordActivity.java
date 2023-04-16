package com.example.kheloguys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

   private EditText forgotPasswordEmail;
   private ProgressBar pb;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordEmail = (EditText) findViewById(R.id.forgotPasswordEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        pb = (ProgressBar) findViewById(R.id.forgotPasswordProgress);

        forgotPasswordEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                forgotPasswordEmail.setError(null);
            }
        });

    }

    public void setToLogin(View view)
    {
        Intent i = new Intent(ForgotPasswordActivity.this,Login.class);
        startActivity(i);
        finish();
    }

    public void forgotPasswordFunction(View view)
    {
        String userEmail = String.valueOf(forgotPasswordEmail.getText());
        String Expn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        if(!userEmail.matches(Expn) || userEmail.equals(""))
            forgotPasswordEmail.setError("Invalid Email");
        else
        {
            pb.setVisibility(ProgressBar.VISIBLE);
            //reset password you will get a mail
            firebaseAuth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }
                            pb.setVisibility(ProgressBar.GONE);
                        }
                    });
        }
    }
}