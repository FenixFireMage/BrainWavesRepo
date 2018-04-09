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





        final TextView TextField = findViewById(R.id.textView);
        TextView CurrScore = findViewById(R.id.score);
        //CurrScore.setText("SCORE: " + 0);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int timeLeft = (int)millisUntilFinished / 1000;
                TextField.setText("Seconds remaining: " + timeLeft);


               // if(!isRed)
               // {
                    //setActivityBackgroundColor(0xfff00000);
                     if(rand.nextBoolean())
                     {
                        isRed=true;
                        setActivityBackgroundColor(0xffffffff);
                    }
                    else{

                         setActivityBackgroundColor(0xfff00000);


                     }


             //  }



                //here you can have your logic to set text to edittext

            }

            public void onFinish() {
                TextField.setText("Game Over");
            }

        }.start();
    }

    public void onFlash(View view){


       // CurrScore.setText("SCORE: " + 0);
       // if(isRed)
       // {
           // isRed=false;
        setActivityBackgroundColor(0xfff00000);
           // score+=100;
       // }
       // else
       // {
           // score-=100;
       // }



   }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}
