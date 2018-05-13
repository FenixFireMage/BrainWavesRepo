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


        showValue = (TextView) findViewById(R.id.counter);
    }

    public void counter(View view){
        clickcount++;
        showValue.setText(Integer.toString(clickcount));

    }
}
