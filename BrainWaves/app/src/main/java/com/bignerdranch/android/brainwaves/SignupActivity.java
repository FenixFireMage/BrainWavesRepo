package com.bignerdranch.android.brainwaves;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";

    public static final String ANONYMOUS = "anonymous";

//    @InjectView(R.id.input_email) EditText mEmailField;
//    @InjectView(R.id.input_password) EditText mPasswordField;

    EditText _reEnterPasswordText;
    Button _signupButton;
    TextView _updateButton;
    EditText _countryText;
    EditText _nomText;
    EditText _AgeText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //ButterKnife.inject(this);

        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        _signupButton = findViewById(R.id.btn_signup);
        _updateButton = findViewById(R.id.btn_update);
        _countryText = findViewById(R.id.input_Country);
        _nomText = findViewById(R.id.input_nom);
        _AgeText = findViewById(R.id.input_Naiss);

        mUsername = _nomText.getText().toString();
//        mCountry = _countryText.getText().toString();
        mAge = _AgeText.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_password);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkAccount();//signup();
            }
        });

        _updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                CheckUserData();
                user.updateEmail(mEmailField.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User email address updated.");
                                }
                            }
                        });
                Intent intent = new Intent(getApplicationContext(), PlayerProfileActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

//    public void signup() {
//        Log.d(TAG, "Signup");
//
//        if (!validate()) {
//            onSignupFailed();
//            return;
//        }
//
//        _signupButton.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();
//        String email = mEmailField.getText().toString();
//        String password = mPasswordField.getText().toString();
//
//        //creating a new user
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());
//                        if (!task.isSuccessful()) {
//                            onSignupFailed();
//
//                        } else {
//                            onSignupSuccess();
//                        }
//                    }
//                });
//
//
//
//        new android.os.Handler().postDelayed(new Runnable() {public void run() {onSignupSuccess();progressDialog.dismiss();}}, 3000);
//    }


//    public void onSignupSuccess() {
//        String nom= _nomText.getText().toString();
//        String country= _countryText.getText().toString();
//        int age= Integer.parseInt(_AgeText.getText().toString());
//
//        Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
//
//        _signupButton.setEnabled(true);
//        setResult(RESULT_OK, null);
//        Intent main = new Intent(SignupActivity.this, MainMenuActivity.class);
//        main.putExtra("user_nom", nom);
//        main.putExtra("user_country",country);
//        main.putExtra("user_age",age);
//        startActivity(main);
//    }
//
//    public void onSignupFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//        _signupButton.setEnabled(true);
//    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Check auth on Activity start

        onAuthSuccess(currentUser);

    }

    private void onAuthSuccess(FirebaseUser user) {

        if (user == null) {
            signInAnonymously();
        } else if (user.isAnonymous()) {
//                mEmailField.setText(loginID);
//                mPasswordField.setText(loginPWD);
//            signIn("test1@test.com", "tester");
            _signupButton.setVisibility(View.VISIBLE);
            _updateButton.setVisibility(View.GONE);
        } else {
            _signupButton.setVisibility(View.GONE);
            _updateButton.setVisibility(View.VISIBLE);

            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            String uid = user.getUid();// The user's ID, unique to the Firebase project.

//            if (user.isAnonymous() || (name == null) || (name.equals("")) || (email == null) || (email.equals(""))) {
//                CheckUserData();
//            }

            //            CheckUserData();
            // Write new user
//            writeNewUser(user.getUid(), mUsername, user.getEmail());

            // Go to MainActivity
            //startActivity(new Intent(this, MainMenuActivity.class));
            //finish();
        }

    }

    @Override
    public boolean validate() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailField.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            mPasswordField.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        if (!(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}