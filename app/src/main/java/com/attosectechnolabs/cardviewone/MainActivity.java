package com.attosectechnolabs.cardviewone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ITI Preview App");

        Button ThreadBtn = (Button)findViewById(R.id.Thread_Button);
        Button QBBtn = (Button) findViewById(R.id.QB_Button);

        ThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,forum.class);
                // start activity
                startActivity(intent);

            }
        });

        QBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,QuizActivity.class);
                // start activity
                startActivity(intent);

            }
        });



    }
}
