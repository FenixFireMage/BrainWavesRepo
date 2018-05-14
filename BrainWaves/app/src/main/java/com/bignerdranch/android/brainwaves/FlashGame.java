package com.bignerdranch.android.brainwaves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.media.MediaPlayer;
import com.bignerdranch.android.brainwaves.R;
import java.util.Random;

public class FlashGame extends AppCompatActivity {

    private Button flashB;
    private int level, points, highScore;
    private boolean gameStart = false;
    private Runnable runnable;
    private int timer;

    public enum GameMode { EASY, HARD }
    private GameMode gameMode;

    private int POINT_INCREMENT;
    private int TIMER_BUMP;
    private static int TIMER_DELTA = -1;
    private static final int START_TIMER = 200;
    private static final int FPS = 100;
    private static final int LEVEL = 25;

    private Handler handler;

    boolean isRed = false;
    Random rand = new Random();
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_game);
        final MediaPlayer redSound = MediaPlayer.create(this, R.raw.horn);
        final MediaPlayer greenSound = MediaPlayer.create(this, R.raw.green);
        final TextView TimerField = findViewById(R.id.textView);
        final TextView CurrScore = findViewById(R.id.score);
        final TextView flashHighScore =findViewById(R.id.FlashHighScoreView);
        flashHighScore.setVisibility(View.INVISIBLE);
        flashB = (Button) findViewById(R.id.button);
        flashB.setVisibility(View.VISIBLE);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int timeLeft = (int) millisUntilFinished / 1000;
                TimerField.setText("Seconds remaining: " + timeLeft);
                CurrScore.setText("SCORE:    " + score);

                if (!isRed) {
                    if (redSound.isPlaying()) {
                        redSound.stop();
                    }

                    //greenSound.start();
                    setActivityBackgroundColor(Color.GREEN);
                    if (rand.nextBoolean()) {
                        redSound.start();
                        isRed = true;
                        setActivityBackgroundColor(Color.RED);
                    } else {
                        if (rand.nextBoolean()) {
                            setActivityBackgroundColor(0xffffff00);
                        }
                    }
                }
            }

            public void onFinish() {
                isRed = false;
                CurrScore.setText("FINAL SCORE:   " + score);
                flashB.setVisibility(View.GONE);
                TimerField.setText("Game Over");
                flashHighScore.setVisibility(View.VISIBLE);
                highScore = saveAndGetHighScore();
                flashHighScore.setText("High Score!  "+highScore);
            }

        }.start();
    }

    private int saveAndGetHighScore() {
        SharedPreferences preferences = getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE);

        points=score;
        int highScore = preferences.getInt("HIGHSCORE_FLASH", 0);

        if (points > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("HIGHSCORE_FLASH", points);
            editor.apply();
            highScore = points;
        }
        return highScore;
    }

    public void onFlash(View view) {
        final MediaPlayer greenSound = MediaPlayer.create(this, R.raw.green);
        if (isRed) {
            isRed = false;
            greenSound.start();
            setActivityBackgroundColor(Color.GREEN);
            score += 100;
        } else {
            score -= 100;
        }

    }

    // A Merge error had the following two methods set as potentially deleted
// If they are not used recommendation is for simply commenting them out
    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    //
    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}
