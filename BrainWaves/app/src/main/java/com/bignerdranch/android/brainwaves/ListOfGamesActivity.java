package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ListOfGamesActivity extends AppCompatActivity {
    private Button mMathGame1;
    private Button mLogicGame1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_games);

        // connecting to mathGame1
        mMathGame1= (Button) findViewById(R.id.buttonGame1);
        mMathGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, MathGame1Activity.class);
                startActivity(intent);
            }
        });
        mLogicGame1= (Button) findViewById(R.id.buttonGame2);
        mLogicGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, LogicGame1.class);
                startActivity(intent);
            }
        });

    }

}
