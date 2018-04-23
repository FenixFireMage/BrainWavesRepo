package com.bignerdranch.android.brainwaves.simon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bignerdranch.android.brainwaves.R;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimonMainActivity extends Activity {
    static public final String SCORE_FILENAME = "simon_score.txt";
    static public boolean soundOn = true;
    static public int maxScore;

    static public final String GAME_MODE_REGULAR = "regular";
    static public final String GAME_MODE_DOUBLE_TROUBLE = "speedy_spencer";
    static public final String GAME_MODE_TOPSY_TURVY = "topsy_turvy";
    static public String gameMode = "regular";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_main);
        loadScore();
        ((TextView) findViewById(R.id.textview_maxScore)).setText("High Score: " + maxScore);

        // regular game
        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change game mode
                gameMode = GAME_MODE_REGULAR;
//                SimonMakeToast.toast(getApplicationContext(), "Simon: Press the buttons in order one-by-one");
                startActivity(new Intent(getApplicationContext(), SimonGameActivity.class));
            }
        });

        // Speedy Spencer!
        findViewById(R.id.button_playMode2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SimonMakeToast.toast(getApplicationContext(), "mode 2 button");
                // change game mode
                gameMode = GAME_MODE_DOUBLE_TROUBLE;
//                SimonMakeToast.toast(getApplicationContext(), "Double Trouble: Press the buttons in order by pairs");
                startActivity(new Intent(getApplicationContext(), SimonGameActivity.class));
            }
        });

        // Tipsy Tina!
        findViewById(R.id.button_playMode3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SimonMakeToast.toast(getApplicationContext(), "mode 3 button");
                // change game mode
                gameMode = GAME_MODE_TOPSY_TURVY;
//                SimonMakeToast.toast(getApplicationContext(), "Tipsy Tina: Press the buttons in REVERSE!");
                startActivity(new Intent(getApplicationContext(), SimonGameActivity.class));
            }
        });

        findViewById(R.id.button_howToPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SimonHowToPlayActivity.class));
            }
        });

        findViewById(R.id.button_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SimonAboutActivity.class));
            }
        });

        CompoundButton soundSwitch = findViewById(R.id.switch_sound);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                soundOn = b;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadScore();
        ((TextView) findViewById(R.id.textview_maxScore)).setText("High Score: " + maxScore);
    }

    public void loadScore(){
        try{
            BufferedReader br = new BufferedReader((new InputStreamReader(getApplicationContext().openFileInput(SCORE_FILENAME))));
            maxScore = br.read();
        }
        catch (IOException e){
            Log.e("FILES", "Error reading file: " + e);
            maxScore = 0;
        }
    }

    public static void saveScore(Context context, int score){
        try {
            FileOutputStream fos = context.openFileOutput(SCORE_FILENAME, Context.MODE_PRIVATE);
            fos.write(score);
            fos.close();
        }
        catch (IOException e) {
            Log.e("FILES", "Error writing file: " + e);
        }

    }
}
