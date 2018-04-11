package com.example.shayng.flash;

import android.graphics.Color;
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





        final TextView TimerField = findViewById(R.id.textView);
        final TextView CurrScore = findViewById(R.id.score);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int timeLeft = (int)millisUntilFinished / 1000;
                TimerField.setText("Seconds remaining: " + timeLeft);
                CurrScore.setText("SCORE:    " + score);

                if(!isRed)
                {
                    setActivityBackgroundColor( Color.GREEN);
                     if(rand.nextBoolean())
                     {
                        isRed=true;
                         setActivityBackgroundColor(Color.RED);
                    }
                    else{

                         if(rand.nextBoolean()) {
                             setActivityBackgroundColor(0xffffff00);
                         }
                     }


               }



                //here you can have your logic to set text to edittext

            }

            public void onFinish() {
                CurrScore.setText("FINAL SCORE:   " + score);
                TimerField.setText("Game Over");
            }

        }.start();
    }

    public void onFlash(View view){



        if(isRed)
        {
            isRed=false;
            setActivityBackgroundColor( Color.GREEN);

            score+=100;

        }
        else
        {
            score-=100;
        }



   }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}
