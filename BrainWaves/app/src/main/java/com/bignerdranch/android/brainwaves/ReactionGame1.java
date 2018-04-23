package com.bignerdranch.android.brainwaves;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;


public class ReactionGame1 extends AppCompatActivity {
    int clickcount=0;
    int timeLeft;
    TextView showValue;
    TextView currTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_game1);

        currTime = findViewById(R.id.timeLeft);
        showValue = findViewById(R.id.counter);

        new CountDownTimer(15000, 100) {

            public void onTick(long millisUntilFinished) {
                timeLeft = (int)millisUntilFinished / 1000;
                int stimeleft = (int)millisUntilFinished - (timeLeft*1000);
                currTime.setText("Time Left:" + timeLeft + "." + stimeleft/100);

            }

            public void onFinish() {

                currTime.setText("Game Over");
            }

        }.start();
    }

    public void counter(View view){
        if(timeLeft!=0)
        clickcount++;

        showValue.setText("Score:" + clickcount);

    }
}
