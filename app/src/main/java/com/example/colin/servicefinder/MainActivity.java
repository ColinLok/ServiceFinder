package com.example.colin.servicefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity{
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在onClick()中启动另一个Activity
                Intent page2 = new Intent(MainActivity.this,Page2List.class);
                Intent page3 = new Intent(MainActivity.this,Page3Detail.class);
                switch (v.getId()) {
                    case R.id.btn_find_house:
                        startActivity(page2);
                        break;
                    case R.id.btn_about:
                        startActivity(page3);
                        break;
                    default:
                        break;
                }
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
