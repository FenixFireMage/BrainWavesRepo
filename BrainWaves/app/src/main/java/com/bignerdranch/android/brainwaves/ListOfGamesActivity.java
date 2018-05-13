package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.brainwaves.ColorPhun.Activities.EasyGameColorPhunActivity;
import com.bignerdranch.android.brainwaves.MathQuiz.MathQuizActivity;
import com.bignerdranch.android.brainwaves.ReferenceClasses.MathGame1Activity;
import com.bignerdranch.android.brainwaves.ReferenceClasses.MathGame2Activity;
import com.bignerdranch.android.brainwaves.simon.SimonGameActivity;
import com.bignerdranch.android.brainwaves.simon.SimonMainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ListOfGamesActivity extends AppCompatActivity {
    private Button Game1;
    private Button Game2;
    private Button Game3;
    private Button Game4;
    private Button Game5;
    private Button Game6;
    private Button Game7;
    private Button Game8;
    private Button Game9;
    private Button Game10;
    private Button Game11;
    private Button Game12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_games);

        // connecting to mathGame1
        Game1 = (Button) findViewById(R.id.buttonGame1);
        Game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, MathGame1Activity.class);
                startActivity(intent);
            }
        });
        Game2 = (Button) findViewById(R.id.buttonGame2);
        Game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, MathGame2Activity.class);
                startActivity(intent);
            }
        });
        Game3 = (Button) findViewById(R.id.buttonGame3);
        Game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, LogicGame1.class);
                startActivity(intent);
            }
        });

        Game4 = (Button) findViewById(R.id.buttonGame4);
        Game4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, ReactionGame1.class);
                startActivity(intent);
            }
        });
        Game5 = (Button) findViewById(R.id.buttonGame5);
        Game5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, ReactionGame2.class);
                startActivity(intent);
            }
        });
        Game6 = (Button) findViewById(R.id.buttonGame6);
        Game6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, FlashGame.class);
                startActivity(intent);
            }
        });
        Game7 = (Button) findViewById(R.id.buttonGame7);
        Game7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, SimonGameActivity.class);
                startActivity(intent);
            }
        });
        Game8 = (Button) findViewById(R.id.buttonGame8);
        Game8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, SimonMainActivity.class);
                startActivity(intent);
            }
        });
        Game9 = (Button) findViewById(R.id.buttonGame9);
        Game9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, MathQuizActivity.class);
                startActivity(intent);
            }
        });
        Game10 = (Button) findViewById(R.id.buttonGame10);
        Game10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Start ListOfGamesActivity
                Intent intent = new Intent(ListOfGamesActivity.this, EasyGameColorPhunActivity.class);
                startActivity(intent);
            }
        });
        Game11 = (Button) findViewById(R.id.buttonGame11);
        Game11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
//                Intent intent = new Intent(ListOfGamesActivity.this, SimonMainActivity.class);
//                startActivity(intent);
            }
        });
        Game12 = (Button) findViewById(R.id.buttonGame12);
        Game12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
//                Intent intent = new Intent(ListOfGamesActivity.this, SimonMainActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent main = new Intent(ListOfGamesActivity.this, MainMenuActivity.class);
        startActivity(main);
    }
    // Attempt to add menu selector in top right corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            //    startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
