package com.bignerdranch.android.brainwaves;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    public static final int RC_SIGN_IN = 1;

    private static final int REQUEST_SIGNUP = 0;

//    private TextView mStatusTextView;
//    private TextView mDetailTextView;
//    private EditText mEmailField;
//    private EditText mPasswordField;

    Button _loginButton;
    Button _signupLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.inject(this);

        mUsername = ANONYMOUS;
        // Views

//        mStatusTextView = findViewById(R.id.status);
//        mDetailTextView = findViewById(R.id.detail);
//        mEmailField = findViewById(R.id.input_email);
//        mPasswordField = findViewById(R.id.input_password);

        // Buttons
        _loginButton= findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);

        //findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        //_loginButton.setOnClickListener(this);

        // findViewById(R.id.email_create_account_button).setOnClickListener(this);
        //_signupLink.setOnClickListener(this);

        //findViewById(R.id.sign_out_button).setOnClickListener(this);
        //findViewById(R.id.verify_email_button).setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        prefs = this.getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        String loginID = prefs.getString("LOGIN_ID", "");
        String loginPWD = prefs.getString("LOGIN_PWD", "");
        if (loginID.length() > 0 && loginPWD.length() > 0) {
            mEmailField.setText(loginID);
            mPasswordField.setText(loginPWD);
            signIn(loginID, loginPWD);
        }

        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                v.startAnimation(animTranslate);
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]
    {
 /*   private void saveFacebookLoginData(String provider, AccessToken accessToken){
        String token=accessToken.getToken();
        if( token != null ){

           // Firebase mRef=new Firebase("https://androidbashfirebaseupdat-bd094.firebaseio.com/users/");
            Firebase myFirebaseRef;
            myFirebaseRef = new Firebase("https://androidbashfirebaseupdat-bd094.firebaseio.com/users/");


            myFirebaseRef.authWithOAuthToken(
                    provider,
                    token,
                    new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            String uid=authData.getUid();
                            String name=authData.getProviderData().get("displayName").toString();
                            String user_country=authData.getProviderData().get("country").toString();
                            UserInfo userInformations= new UserInfo(name,20,user_country);
                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

                            DatabaseReference usersRef = ref.child("users").child(uid);
                            usersRef.setValue(userInformations);
                            //Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
*/

/*

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }



                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(main);
                    }
                });
    }
*/
}

    public void login(String mail, String pwd) {
        Log.d(TAG, "Login");

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        //authenticate user
        mAuth.signInWithEmailAndPassword(mail, pwd)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, Log a message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            // there was an error
                            onLoginFailed();

                        } else {
                            onLoginSuccess();
                        }
                    }
                });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }//end login(mail,pwd)

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validate()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            onLoginSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            onLoginFailed();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, Log a message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            // there was an error
                            onLoginFailed();

                        } else {
                            onLoginSuccess();
                        }
                    }
                });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }//end login()

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LOGIN_ID", email);
        editor.putString("LOGIN_PWD", password);
        editor.commit();

        Intent main = new Intent(LoginActivity.this, MainMenuActivity.class);
        startActivity(main);
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

//    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//
//        if (user != null) {
//            // Signed in
//            //withUI
//            //mStatusTextView.setText(getString(R.string.firebaseui_status_fmt, user.getEmail()));
//            //mDetailTextView.setText(getString(R.string.id_fmt, user.getUid()));
//
//            //withsdk
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
////            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
////            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
//            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//
//            //findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
////            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
////
////            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
//            //onLoginSuccess();
//        } else {
//
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
////            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
////            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
////            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//
////            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
////            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
//            //onLoginFailed();
//        }
//    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
//        if (i == R.id.email_create_account_button) {
//            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.email_sign_in_button) {
//            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.sign_out_button) {
//            signOut();
//        } else if (i == R.id.verify_email_button) {
//            sendEmailVerification();
//        }
    }

}
