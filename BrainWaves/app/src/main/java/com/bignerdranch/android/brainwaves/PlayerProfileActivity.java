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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayerProfileActivity extends BaseActivity {


    String PlayerName,PlayerEmailText,AdressText;
    int AgePlayerText;
    private FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);
        //ButterKnife.inject(this);

//        if (savedInstanceState == null) {
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.fragment_container_profile_player, new MainFragment(), "MainFragment");
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            fragmentTransaction.commit();
//        }
        ref = FirebaseDatabase.getInstance().getReference();
        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        ref.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
//                UserInfo userInfos = dataSnapshot.getValue(UserInfo.class);

//                PlayerEmailText=user.getEmail().toString();
                PlayerName= mUsername;//userInfos.DisplayNamePlayer().toString();
                AdressText="";//userInfos.DisplayCountryPlayer().toString();
                AgePlayerText=0;//userInfos.DisplayAgePlayer();


                TextView tvnom = (TextView) findViewById(R.id.tvNamePlayer);
                tvnom.setVisibility(View.VISIBLE);
                tvnom.setText(PlayerName);

//                TextView tvmail = (TextView) findViewById(R.id.tvPEmail);
//                tvmail.setVisibility(View.VISIBLE);
//                tvmail.setText(PlayerEmailText);

                TextView tvCountry = (TextView) findViewById(R.id.tvAddressP);
                tvCountry.setVisibility(View.VISIBLE);
                tvCountry.setText(AdressText);

                TextView tvAge = (TextView) findViewById(R.id.tvAgeP);
                tvAge.setText(String.valueOf(AgePlayerText));

                TextView tvscores = (TextView) findViewById(R.id.tvSummaryP);
                SharedPreferences preferences = getApplicationContext().getSharedPreferences( "MyPrefs", Context.MODE_PRIVATE);
                int highScore_phun = preferences.getInt("HIGHSCORE_COLORPHUN", 0);
                tvscores.setText("High Score Color phun: "+ String.valueOf(highScore_phun));
                Button logout = (Button) findViewById(R.id.btn_logoutProfileplayer);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                });
                //tvscores.setText("High Score Color phun: 0");
                //System.out.println(userInfos);
//                Log.d("myTag",  userInfos.toString());
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.d("myTag", "The read failed: " + databaseError.getCode());
                System.out.println("The read failed: " + databaseError.getCode());
                //Toast.makeText(this, "Ereur de fetch...", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void logout(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        //Intent main = new Intent(PlayerProfileActivity.this, LoginActivity.class);
        SharedPreferences prefs = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LOGIN_ID", "");
        editor.putString("LOGIN_PWD", "");
        editor.commit();
        //startActivity(main);
    }


}
