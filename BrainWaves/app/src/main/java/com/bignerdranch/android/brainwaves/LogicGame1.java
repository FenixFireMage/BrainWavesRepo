package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LogicGame1 extends AppCompatActivity {
    private Button mOne;
    private Button mTwo;
    private Button mThree;
    private Button mFour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logic_game1);
        mOne = (Button) findViewById(R.id.button);
        mOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(LogicGame1.this, ListOfGamesActivity.class);
                startActivity(intent);
            }
        });
        mTwo = (Button) findViewById(R.id.button3);
        mTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(LogicGame1.this, ListOfGamesActivity.class);
                startActivity(intent);
            }
        });
        mThree = (Button) findViewById(R.id.button4);
        mThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(LogicGame1.this, ListOfGamesActivity.class);
                startActivity(intent);
            }
        });
        mFour = (Button) findViewById(R.id.button5);
        mFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(LogicGame1.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

}
