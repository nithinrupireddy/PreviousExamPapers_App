package com.nithin.finalproject.Branches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nithin.finalproject.R;
import com.nithin.finalproject.cse.Cseactivity;

public class AllYears extends AppCompatActivity {

    TextView firstyearcse,secondyearcse,thirdyearcse,fourthyearcse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_years);

        firstyearcse = (TextView) findViewById(R.id.firstyearcse);
        secondyearcse = (TextView) findViewById(R.id.secondyearcse);
        thirdyearcse = (TextView) findViewById(R.id.thirdyearcse);
        fourthyearcse = (TextView) findViewById(R.id.fourthyearcse);

        firstyearcse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cseactivity.class));
            }
        });

        secondyearcse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"comming soon",Toast.LENGTH_SHORT);
            }
        });

        thirdyearcse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Comming soon",Toast.LENGTH_SHORT);
            }
        });
        fourthyearcse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Comming soon",Toast.LENGTH_SHORT);
            }
        });
    }
}
