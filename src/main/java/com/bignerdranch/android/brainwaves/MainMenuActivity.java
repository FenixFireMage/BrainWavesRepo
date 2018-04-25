package com.bignerdranch.android.brainwaves;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainMenuActivity extends Activity {
    private Button mProfile;
    private Button mListOfGames;
    private Button mDaily;
    private Button mPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        // connecting Game button to list of game activity
        mListOfGames = (Button) findViewById(R.id.list_of_games_button);
        mListOfGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(MainMenuActivity.this, ListOfGamesActivity.class);
                startActivity(intent);
            }
        });
        mPlay = (Button) findViewById(R.id.play_button);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(MainMenuActivity.this, MathGame1Activity.class);
                startActivity(intent);
            }
        });
        mDaily = (Button) findViewById(R.id.daily_button);
        mDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(MainMenuActivity.this, MathGame1Activity.class);
                startActivity(intent);
            }
        });
        mProfile = (Button) findViewById(R.id.profile_button);
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(MainMenuActivity.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}