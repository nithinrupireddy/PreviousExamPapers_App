package com.nithin.finalproject.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nithin.finalproject.Branches.Allbranches;
import com.nithin.finalproject.Dialogues.Passwordresetdialog;
import com.nithin.finalproject.Dialogues.Resendverification;
import com.nithin.finalproject.R;
import com.nithin.finalproject.Splash.MainActivity;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "hi";
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private EditText enteremail;
    private EditText enterpassword;
    private Button signinbutton;
    private Button signupbutton;
    private SignInButton googlebutton;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthl;
    private TextView forgotpassword, resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mauth = FirebaseAuth.getInstance();
        mauthl = new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null)
                {
                    startActivity( new Intent(LoginActivity.this,Allbranches.class));
                    finish();
                }
            }
        };

        enteremail = (EditText) findViewById(R.id.enteremail);
        enterpassword = (EditText) findViewById(R.id.enterpassword);
        signinbutton = (Button) findViewById(R.id.signin);
        signupbutton = (Button) findViewById(R.id.signup);
        googlebutton = (SignInButton) findViewById(R.id.googlebutton);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this,"You got an error",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        googlebutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 signIn();

             }
         });

        FirebaseUser userb = mauth.getCurrentUser();
        try {
            if (userb.isEmailVerified()) {
                checkuser(userb);
            }
        } catch (NullPointerException e) {
            mauth.signOut();
        }

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getemail = enteremail.getText().toString().trim();
                String getpassword = enterpassword.getText().toString().trim();
                callsignin(getemail, getpassword);

            }
        });


        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signup = new Intent(LoginActivity.this, Signup.class);
                startActivity(signup);
            }
        });

        forgotpassword = (TextView) findViewById(R.id.forgotpassword);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Passwordresetdialog dialog = new Passwordresetdialog();
                dialog.show(getSupportFragmentManager(), "dialog reset");
            }
        });

        resend = (TextView) findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resendverification dialog = new Resendverification();
                dialog.show(getSupportFragmentManager(), "dialog resend");

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthl);
    }

    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                //Google signin failed
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mauth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                         Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    private void checkuser(FirebaseUser usera) {
        if (usera == null) {
            Toast.makeText(LoginActivity.this, " Welcome,  Signin and Enjoy ", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent ia = new Intent(LoginActivity.this, Allbranches.class);
            startActivity(ia);
            finish();

        }
    }


    private void callsignin(String email, String password) {
        if (!isEmpty(enteremail.getText().toString())
                && !isEmpty(enterpassword.getText().toString()))

        {

            mauth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "Signed Sucessfully" + task.isSuccessful());

                            FirebaseUser user = mauth.getCurrentUser();

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication Failed",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (user.isEmailVerified()) {
                                        Log.d(TAG, " completed verification");
                                        Intent i = new Intent(LoginActivity.this, Allbranches.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "email not verified", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (NullPointerException e) {
                                    Log.e(TAG, "On complete Nullpointer Exception" + e.getMessage());
                                    mauth.signOut();

                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }
    }
}
