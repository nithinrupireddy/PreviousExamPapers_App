package com.nithin.finalproject.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nithin.finalproject.R;

import static android.text.TextUtils.isEmpty;

public class Signup extends AppCompatActivity {


    private static final String TAG = "HI";
    private FirebaseAuth mauth;
    private EditText emailregister,passwordregister,nameregister;
    private Button registerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mauth = FirebaseAuth.getInstance();

        emailregister = (EditText) findViewById(R.id.emailregister);
        passwordregister = (EditText) findViewById(R.id.passwordregister);
        registerbutton = (Button) findViewById(R.id.registerbutton);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = emailregister.getText().toString().trim();
                String getpassword = passwordregister.getText().toString().trim();
                callsignup(getemail,getpassword);
            }
        });

    }

    private void callsignup(String email, String password)
    {
        if(!isEmpty(emailregister.getText().toString())
                && !isEmpty(passwordregister.getText().toString())) {
            mauth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "Signup sucessful" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Signup.this, "Signup Failed ! Invalid format/Already Account Exists",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Signup.this, "Creating Account", Toast.LENGTH_SHORT).show();
                                sendverificationemail();
                                Intent ia = new Intent(Signup.this, LoginActivity.class);
                                startActivity(ia);
                            }

                            // ...
                        }
                    });
        }
        else
        {
            Toast.makeText(Signup.this,"Please fill out all fields",Toast.LENGTH_LONG).show();
        }

    }

    public void sendverificationemail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Signup.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    }
