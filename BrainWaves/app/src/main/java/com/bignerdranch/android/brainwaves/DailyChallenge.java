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
import android.widget.TextView;

import com.bignerdranch.android.brainwaves.ColorPhun.Activities.EasyGameColorPhunActivity;
import com.bignerdranch.android.brainwaves.MathQuiz.MathQuizActivity;
import com.bignerdranch.android.brainwaves.simon.SimonGameActivity;

import java.util.Calendar;
import java.util.Random;

public class DailyChallenge extends AppCompatActivity {

    private Button mDaily;
    int seed = Calendar.DAY_OF_MONTH;
    Random random = new Random(seed);
    int r = random.nextInt(5)+1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge);

        mDaily = findViewById(R.id.button7);
        mDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity

                randomGame(r);
            }
        });
        SharedPreferences preferences = getApplicationContext().getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE);

        int randomInt = r;
        if (randomInt == 6) {
            int highScore_phun = preferences.getInt("HIGHSCORE_COLORPHUN", 0);
                setText("Light V Dark",highScore_phun);
        } else if (randomInt == 5) {
            int high_fast = preferences.getInt("HIGHSCORE_FAST_TAP",0);
                setText("Fast Tap",high_fast);
        } else if(randomInt == 1){
            int math_score = preferences.getInt("MATH",0);
                setText("Math",math_score);
        } else if(randomInt == 2){
            int flash_score = preferences.getInt("HIGHSCORE_FLASH",0);
                setText("Flash",flash_score);
        } else if(randomInt == 3){
            int simon_score = preferences.getInt("SIMON",0);
                setText("Simon",simon_score);
        }else {
            int logic_score = preferences.getInt("LOGIC",0);
                setText("Logic",logic_score);

        }
  }

  public void setText(String game,int toBeat){
      final TextView dailyScore = findViewById(R.id.textView13);
            dailyScore.setVisibility(View.VISIBLE);
           dailyScore.setText("Current highest score in "+game+":"+toBeat);

  }






    private void randomGame(int randomInt) {

        Intent intent;
        if (randomInt == 1) {
            intent = new Intent(DailyChallenge.this, MathQuizActivity.class);
        } else if (randomInt == 2) {
            intent = new Intent(DailyChallenge.this, FlashGame.class);
        } else if(randomInt == 3){
            intent = new Intent(DailyChallenge.this, SimonGameActivity.class);
        } else if(randomInt == 4){
            intent = new Intent(DailyChallenge.this, LogicGame1.class);
        } else if(randomInt == 5){
            intent = new Intent(DailyChallenge.this, ReactionGame2.class);
        }else {
            intent = new Intent(DailyChallenge.this, EasyGameColorPhunActivity.class);
        }
        startActivity(intent);
    }


}
