package com.bignerdranch.android.brainwaves;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.Random;
import android.widget.TextView;
public class LogicGame1 extends AppCompatActivity {

    public int score = 0;
    int level = 1;
   //public enum answers{1,2,3,4};
    int norepeat=0;
    public int rightAnswer=-1;
    private Button mOne;
    private Button mTwo;
    private Button mThree;
    private Button mFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logic_game1);

        generateQuestion(1);
        mOne = (Button) findViewById(R.id.button);
        mOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                generateQuestion(1);
            }
        });
        mTwo = (Button) findViewById(R.id.button3);
        mTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                generateQuestion(2);
            }
        });
        mThree = (Button) findViewById(R.id.button4);
        mThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                generateQuestion(3);
            }
        });
        mFour = (Button) findViewById(R.id.button5);
        mFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                generateQuestion(4);
            }
        });
    }

    public void generateQuestion(int answer){


        if(rightAnswer==answer){
            score++;
        }
        level++;

        if(level<=6) {
            final TextView CurrScore = findViewById(R.id.logicScore);
            CurrScore.setText("Score:" + score);

            ImageView shape = findViewById(R.id.shape);
            //CurrScore.setText(rightAnswer + "Score:" + score);

            Random r = new Random();
            rightAnswer = r.nextInt(4) + 1;
            if (rightAnswer == norepeat) {
                rightAnswer = (rightAnswer + 1) % 4;
            }
            norepeat = rightAnswer;

            switch (rightAnswer) {
                case 1:
                    shape.setImageResource(R.drawable.shape3);
                    break;
                case 2:
                    shape.setImageResource(R.drawable.shape4);
                    break;
                case 3:
                    shape.setImageResource(R.drawable.shape5);
                    break;
                case 4:
                    shape.setImageResource(R.drawable.shape6);
                    break;
            }
        }
        else{

            endLogic(score);
        }
    }

    public void endLogic(int score){
        final TextView CurrScore = findViewById(R.id.logicScore);
        CurrScore.setText("Final Score:"+score);
        saveAndGetHighScore(score);
    }
    private void saveAndGetHighScore(int finalScore) {
        SharedPreferences preferences = this.getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE);

        int highScore = preferences.getInt("LOGIC", 0);

        if (finalScore > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("LOGIC", finalScore);
            editor.apply();

        }

    }

}
