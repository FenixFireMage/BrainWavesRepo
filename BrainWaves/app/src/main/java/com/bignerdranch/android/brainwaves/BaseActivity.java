package com.bignerdranch.android.brainwaves;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "AnonymousAuth";
    protected TextView mStatusTextView;
    protected TextView mDetailTextView;
    protected EditText mEmailField;
    protected EditText mPasswordField;

    protected DatabaseReference mDatabase;
    // [START declare_auth]
//    protected FirebaseAuth mAuth;
    private FirebaseAuth mAuth;
    // [END declare_auth]
    protected FirebaseAuth.AuthStateListener mAuthListener;
    SharedPreferences prefs;

    public static final String ANONYMOUS = "anonymous";
    protected String mUsername = ANONYMOUS;
    protected String mCountry = "The Universe";
    protected String mAge = "42";

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean validate(String email, String password) {
        boolean valid = true;

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

        return valid;
    }
    public boolean validate() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        valid=validate(email, password);

        return valid;
    }

    protected void updateUI(FirebaseUser user) {
        hideProgressDialog();

        if (user != null) {
            // Signed in
            //withUI
            //mStatusTextView.setText(getString(R.string.firebaseui_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.id_fmt, user.getUid()));

            //withsdk
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

//            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
//            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);

            //findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
            //onLoginSuccess();
        } else {

            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

//            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
            //onLoginFailed();
        }
    }


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
                            onSignupSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            onSignupFailed();
                        }
                        new android.os.Handler().postDelayed(new Runnable() {public void run() {onSignupSuccess();mProgressDialog.dismiss();}}, 3000);
//                        // [START_EXCLUDE]
//                        hideProgressDialog();
//                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }

    public void onSignupSuccess() {
        //String nom= _nomText.getText().toString();
       // String country= _countryText.getText().toString();
        int age= Integer.parseInt(mAge);

        Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();

        //_signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent main = new Intent(this, MainMenuActivity.class);
        main.putExtra("user_nom", mUsername);
        main.putExtra("user_country",mCountry);
        main.putExtra("user_age",age);
        startActivity(main);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        //_signupButton.setEnabled(true);
    }

    protected void linkAccount() {
        // Make sure form is valid
        if (!validate()) {
            return;
        }

        // Get email and password from form
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        // Create EmailAuthCredential with email and password
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Link the anonymous user to the email credential
        showProgressDialog();

        // [START link_credential]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END link_credential]
    }

    protected void signOut() {
        mAuth.signOut();
        updateUI(null);
    }


    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void CheckUserData(){
        String user_name = Objects.requireNonNull(getIntent().getExtras()).getString("user_nom");
        String user_country= getIntent().getExtras().getString("user_country").toString();
        int user_age= getIntent().getExtras().getInt("user_age");
        UserInfo userInformations= new UserInfo(user_name,user_age,user_country);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //if the user is not logged in that means current user will return null
        if (user == null) {
            Toast.makeText(this, "No user connected...", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this, MainMenuActivity.class));
            finish();
        }
        DatabaseReference usersRef = mDatabase.child("users").child(Objects.requireNonNull(user).getUid());
//        DatabaseReference usersRef = ref.child("users").child(user.getUid());
        usersRef.setValue(userInformations);
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
        getIntent().getExtras().remove("user_nom");
        getIntent().getExtras().remove("user_country");
        getIntent().getExtras().remove("user_age");
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
