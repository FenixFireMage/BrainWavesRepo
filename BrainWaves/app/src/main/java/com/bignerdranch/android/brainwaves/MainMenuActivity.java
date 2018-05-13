package com.bignerdranch.android.brainwaves;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bignerdranch.android.brainwaves.ColorPhun.Activities.EasyGameColorPhunActivity;
import com.bignerdranch.android.brainwaves.MathQuiz.MathQuizActivity;
import com.bignerdranch.android.brainwaves.simon.SimonGameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


public class MainMenuActivity extends BaseActivity {
    private static final String TAG = "MainMenu";
    private Button mProfile;
    private Button mListOfGames;
    private Button mDaily;
    private Button mPlay;

//    private int randomInt;

//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        Intent intent;

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        if (getIntent().getExtras() != null) {CheckUserData();}

        // connecting Game button to list of game activity
        mListOfGames = findViewById(R.id.list_of_games_button);
        mListOfGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(MainMenuActivity.this, ListOfGamesActivity.class);
                startActivity(intent);
            }
        });
        mPlay = findViewById(R.id.play_button);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Random random = new Random();
                randomGame(random);
            }
        });
        mDaily = findViewById(R.id.daily_button);
        mDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                int seed = Calendar.DAY_OF_MONTH;
                Random random = new Random(seed);
                randomGame(random);
            }
        });
        mProfile = findViewById(R.id.profile_button);
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start ListOfGamesActivity
                Intent intent = new Intent(MainMenuActivity.this, PlayerProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Check auth on Activity start

        onAuthSuccess(currentUser);

    }

    private void randomGame(Random random) {
        int randomInt = random.nextInt(5) + 1;
        Intent intent;
        if (randomInt == 1) {
            intent = new Intent(MainMenuActivity.this, MathQuizActivity.class);
        } else if (randomInt == 2) {
            intent = new Intent(MainMenuActivity.this, FlashGame.class);
        } else if(randomInt == 3){
            intent = new Intent(MainMenuActivity.this, SimonGameActivity.class);
        } else if(randomInt == 4){
            intent = new Intent(MainMenuActivity.this, LogicGame1.class);
        } else {
        intent = new Intent(MainMenuActivity.this, EasyGameColorPhunActivity.class);
    }
        startActivity(intent);
    }

    private void onAuthSuccess(FirebaseUser user) {

        if (user == null) {
            signInAnonymously();
        } else if (user.isAnonymous()) {
//                mEmailField.setText(loginID);
//                mPasswordField.setText(loginPWD);
            signIn("test1@test.com", "tester");
        } else {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();// The user's ID, unique to the Firebase project.

            if (user.isAnonymous() || (name == null) || (name.equals("")) || (email == null) || (email.equals(""))) {
                CheckUserData();
            }

            //            CheckUserData();
            // Write new user
//            writeNewUser(user.getUid(), mUsername, user.getEmail());

            // Go to MainActivity
            //startActivity(new Intent(this, MainMenuActivity.class));
            //finish();
        }

    }

    // [START basic_write]
//    private void writeNewUser(String userId, String name, String email) {
//        UserInfo user = new UserInfo();
//
//        mDatabase.child("users").child(userId).setValue(user);
//    }
    // [END basic_write]
/*

    protected void signInAnonymously() {
        showProgressDialog();
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }
*/

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