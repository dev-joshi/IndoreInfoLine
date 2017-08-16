package com.example.devansh.indoreinfoline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView places,restaurants,scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        places=(ImageView)findViewById(R.id.View1);
        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I= new Intent(MainActivity.this, Places.class);
                startActivity(I);
            }
        });

        restaurants=(ImageView)findViewById(R.id.View2);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I= new Intent(MainActivity.this, Restaurants.class);
                startActivity(I);
            }
        });

        //View 3 missing----------------------------------------------
        //------------------------------------------------------------

        scroll=(ImageView)findViewById(R.id.View4);
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I=new Intent(MainActivity.this,Contacts.class);
                startActivity(I);
            }
        });
    }
}
