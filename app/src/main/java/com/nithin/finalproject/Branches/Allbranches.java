package com.nithin.finalproject.Branches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nithin.finalproject.cse.Cseactivity;
import com.nithin.finalproject.Login.LoginActivity;
import com.nithin.finalproject.R;

public class Allbranches extends AppCompatActivity {

    private FirebaseAuth mauth;
    private TextView username,cse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbranches);


        mauth = FirebaseAuth.getInstance(); //important call

    //  username = (TextView) findViewById(R.id.user);

       // Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mtoolbar);

        //check whether user is logged in or not

        if(mauth == null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

      /* FirebaseUser user = mauth.getCurrentUser();
        if(user!=null)
        {
            username.setText("welcome ");
        }*/

        cse = (TextView) findViewById(R.id.cse);
        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AllYears.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id)
        {
            case R.id.sign_out:
                mauth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;
            case R.id.Account_settings:
                Toast.makeText(Allbranches.this, " Account settings", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
