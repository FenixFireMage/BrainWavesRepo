package com.bignerdranch.android.brainwaves.MathQuiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.brainwaves.R;


public class ScoreScreen extends Activity {

    private Button homeButton;
    private Button playButton;

    private TextView rightText;
    private TextView wrongText;
    private Intent homeIntent;
    private Intent quizIntent;

    private String rightMessage;
    private String wrongMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
         homeButton = findViewById(R.id.button2);
         playButton = findViewById(R.id.button);

        rightText = findViewById(R.id.textView2);
        wrongText = findViewById(R.id.textView3);
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        rightMessage = extras.getString("myRightMessage");
        wrongMessage = extras.getString("myWrongMessage");


        String rightPoints = "Questions Answered Right: " + rightMessage;
        String wrongPoints = "Questions Answered Wrong: " + wrongMessage;

        rightText.setText(rightPoints);
        wrongText.setText(wrongPoints);

        saveAndGetHighScore(Integer.parseInt(rightMessage));



        homeIntent = new Intent(this,MathQuizActivity.class);
          quizIntent = new Intent(this,BrainChallenge.class);

        homeButton.setOnClickListener(new View.OnClickListener() {
            //Onclick button function
            public void onClick(View view) {
                startActivity(homeIntent);

            }
        } );

        playButton.setOnClickListener(new View.OnClickListener() {
            //Onclick button function
            public void onClick(View view) {
                startActivity(quizIntent);

            }
        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.score_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent main = new Intent(ScoreScreen.this, MathQuizActivity.class);
        startActivity(main);
    }

    private void saveAndGetHighScore(int finalScore) {
        SharedPreferences preferences = this.getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE);

        int highScore = preferences.getInt("MATH", 0);

        if (finalScore > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("MATH", finalScore);
            editor.apply();
        }

    }
}
