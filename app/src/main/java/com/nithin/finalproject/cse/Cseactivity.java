package com.nithin.finalproject.cse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nithin.finalproject.R;

public class Cseactivity extends AppCompatActivity implements View.OnClickListener{

    private TextView em,c,physis,english,bel,bme,em2,oop,chemistry,drawing,bee,bem;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cseactivity);

        em = (TextView) findViewById(R.id.em);
        c = (TextView) findViewById(R.id.c);
        physis = (TextView) findViewById(R.id.physics);
        english = (TextView) findViewById(R.id.english);
        bel= (TextView) findViewById(R.id.bel);
        bme = (TextView) findViewById(R.id.bme);
        em2 = (TextView) findViewById(R.id.em2);
        oop= (TextView) findViewById(R.id.oop);
        chemistry = (TextView) findViewById(R.id.chemistry);
        drawing = (TextView) findViewById(R.id.drawing);
        bee = (TextView) findViewById(R.id.bee);
        bem = (TextView) findViewById(R.id.bem);



        em.setOnClickListener(this);
        c.setOnClickListener(this);
        physis.setOnClickListener(this);
        english.setOnClickListener(this);
        bel.setOnClickListener(this);
        bme.setOnClickListener(this);
        em2.setOnClickListener(this);
        oop.setOnClickListener(this);
        chemistry.setOnClickListener(this);
        drawing.setOnClickListener(this);
        bee.setOnClickListener(this);
        bem.setOnClickListener(this);

        //https://api.myjson.com/bins/1ans7h
        //https://api.myjson.com/bins/l8p07

    }

    @Override
    public void onClick(View v) {
        Intent i;
        if(v.getId()==R.id.em){
            i=new Intent(this,Loadingactivity.class);
            i.putExtra("button","l8p07");
            startActivity(i);
        }else if (v.getId()==R.id.c){
            i=new Intent(this,Loadingactivity.class);
            i.putExtra("button","l8p07");
            startActivity(i);
        }

    }
}
