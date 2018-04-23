package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.brainwaves.simon.SimonGameActivity;
import com.bignerdranch.android.brainwaves.simon.SimonMainActivity;

public class ListOfGamesActivity extends AppCompatActivity {
    private Button mMathGame1;
    private Button mMathGame2;
    private Button mLogicGame1;
    private Button mSpeedGame1;
    private Button mSpeedGame2;


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
        mMathGame2= (Button) findViewById(R.id.buttonGame2);
        mMathGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, MathGame2Activity.class);
                startActivity(intent);
            }
        });
        mLogicGame1= (Button) findViewById(R.id.buttonGame3);
        mLogicGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, LogicGame1.class);
                startActivity(intent);
            }
        });

        mSpeedGame1= (Button) findViewById(R.id.buttonGame4);
        mSpeedGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, SpeedGame1.class);
                startActivity(intent);
            }
        });
        mSpeedGame2= (Button) findViewById(R.id.buttonGame5);
        mSpeedGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, ReactionGame1.class);
                startActivity(intent);
            }
        });
        mSpeedGame2= (Button) findViewById(R.id.buttonGame6);
        mSpeedGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, FlashGame.class);
                startActivity(intent);
            }
        });
        mSpeedGame2= (Button) findViewById(R.id.buttonGame7);
        mSpeedGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, SimonGameActivity.class);
                startActivity(intent);
            }
        });

    }

}
