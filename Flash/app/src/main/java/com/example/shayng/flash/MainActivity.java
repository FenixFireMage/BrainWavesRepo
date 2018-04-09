package com.example.shayng.flash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.concurrent.TimeUnit;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    boolean isRed = false;
    Random rand = new Random();
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        final TextView currScore = findViewById(R.id.score);
        final TextView TextField = findViewById(R.id.textView);


        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int timeLeft = (int)millisUntilFinished / 1000;
                TextField.setText("Seconds remaining: " + timeLeft);
                currScore.setText("SCORE: " + score);

                if(!isRed)
                {
                    setActivityBackgroundColor(0x08ffffff);
                    if(rand.nextBoolean()) {
                        isRed=true;
                        setActivityBackgroundColor(0xfff00000);
                    }

                }



                //here you can have your logic to set text to edittext

            }

            public void onFinish() {
                TextField.setText("Game Over");
            }

        }.start();
    }

    public void onClick(View view){

        if(isRed)
        {
            isRed=false;
            setActivityBackgroundColor(0x08ffffff);
            score+=100;
        }
        else
        {
            score-=100;
        }

        // Toast myToast = Toast.makeText(this, message, duration);

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}
