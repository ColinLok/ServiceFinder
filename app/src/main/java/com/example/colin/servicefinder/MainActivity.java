package com.example.colin.servicefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在onClick()中启动另一个Activity
                Intent page2 = new Intent(MainActivity.this,Page2List.class);
                        startActivity(page2);

            }
        });
        findViewById(R.id.btn_find_house).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在onClick()中启动另一个Activity
                Intent page3 = new Intent(MainActivity.this,Page3Detail.class);
                startActivity(page3);
            }
        });



    }
    public void toSecondActivity(View v){
        Intent page2 = new Intent(this,Page2List.class);
        startActivity(page2);
    }

}
