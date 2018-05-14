package com.bignerdranch.android.brainwaves;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PlayerProfileActivity extends BaseActivity {

    String PlayerName,PlayerEmailText,AdressText;
    int AgePlayerText,brainLevelPlayerText;
//    private FirebaseAuth mAuth;
//    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);
        //ButterKnife.inject(this);



        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container_profile_player, new MainFragment(), "MainFragment");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user==null) {
            signInAnonymously();
        }
        // Add value event listener to the post
        // [START post_value_event_listener]
        mDatabase.child("users").child(Objects.requireNonNull(user).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                UserInfo userInfos = dataSnapshot.getValue(UserInfo.class);

//                PlayerEmailText=user.getEmail().toString();
                PlayerName= userInfos.DisplayNamePlayer();
                AdressText= userInfos.DisplayCountryPlayer();
                AgePlayerText=userInfos.DisplayAgePlayer();
//                if(userInfos.BrainLevelPlayer == null){
//
//                }
                brainLevelPlayerText=userInfos.DisplayBrainLevelPlayer();

                TextView tvNom = (TextView) findViewById(R.id.tvNamePlayer);
                tvNom.setVisibility(View.VISIBLE);
                tvNom.setText(PlayerName);

                TextView tvmail = (TextView) findViewById(R.id.tvPEmail);
                tvmail.setVisibility(View.VISIBLE);
                tvmail.setText(PlayerEmailText);

                TextView tvCountry = (TextView) findViewById(R.id.tvAddressP);
                tvCountry.setVisibility(View.VISIBLE);
                tvCountry.setText(AdressText);

                TextView tvAge = (TextView) findViewById(R.id.tvAgeP);
//                tvAge.setText(String.valueOf(AgePlayerText));
                tvAge.setText("Age: " + String.valueOf(AgePlayerText));


//                TextView tvBrainLev = (TextView) findViewById(R.id.tvBrainLevel);
//                tvBrainLev.setText("Brain Level: " + String.valueOf(brainLevelPlayerText));

                SharedPreferences preferences = getApplicationContext().getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE);


                int highScore_LvD = preferences.getInt("HIGHSCORE_COLORPHUN", 0);
                int high_fast = preferences.getInt("HIGHSCORE_FAST_TAP",0);
                int math_score = preferences.getInt("MathScore",0);
                int flash_score = preferences.getInt("HIGHSCORE_FLASH",0);
                int simon_score = preferences.getInt("HIGHSCORE_SIMON",0);
                int logic_score = preferences.getInt("LOGIC",0);

//                TextView tvFlashscores = (TextView) findViewById(R.id.tvFlash);
//                tvFlashscores.setText("High Score Flash: " + String.valueOf(flash_score));
//
//                TextView tvSimonScores = (TextView) findViewById(R.id.tvSimon);
//                tvSimonScores.setText("High Score Simon: " + String.valueOf(simon_score));
//
//                TextView tvMathscores = (TextView) findViewById(R.id.tvMathQuiz);
//                tvMathscores.setText("High Score Math Quiz: " + String.valueOf(math_score));
//
//                TextView tvLvDscores = (TextView) findViewById(R.id.tvLightVsDark);
//                tvLvDscores.setText("High Score Light V Dark: " + String.valueOf(highScore_LvD));

                TextView tvscores = (TextView) findViewById(R.id.tvSummaryP);

                int brainwavefreq = logic_score+math_score+flash_score+high_fast+simon_score;
                tvscores.setText("High Score Light V Dark: "+ String.valueOf(highScore_LvD)
                        +"\nHigh Score Logic: " + logic_score
                        + "\nHigh Score Math: " + math_score
                        + "\nHigh Score Flash: " + + flash_score
                        + "\nHigh Score Fast Tap: " + high_fast
                        + "\nHigh Score Simon: " + simon_score
                        + "\n\nBrainWave Frequency:" + brainwavefreq);


                Button mEdit = (Button) findViewById(R.id.editProfileButton);
                mEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Start ListOfGamesActivity
                        Intent intent = new Intent(PlayerProfileActivity.this, SignupActivity.class);
                        startActivity(intent);
                    }
                });

                Button logout = (Button) findViewById(R.id.btn_logoutProfileplayer);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                });
                //tvscores.setText("High Score Color phun: 0");
                //System.out.println(userInfos);
                Log.d("myTag",  userInfos.toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("myTag", "The read failed: " + databaseError.getCode());
                System.out.println("The read failed: " + databaseError.getCode());
                // [START_EXCLUDE]
                Toast.makeText(PlayerProfileActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        });

    }

    public void logout(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent main = new Intent(PlayerProfileActivity.this, LoginActivity.class);
        SharedPreferences prefs = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LOGIN_ID", "");
        editor.putString("LOGIN_PWD", "");
        editor.apply();
        startActivity(main);
    }


}
