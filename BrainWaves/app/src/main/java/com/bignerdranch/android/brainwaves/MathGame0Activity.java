package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MathGame0Activity extends AppCompatActivity {
    private EditText mUser;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private int mUserLevel = 10;
    private int mNumberOfQuestions= 10;
    private MathQuestionGenerator0 mProblemBank = new MathQuestionGenerator0(mNumberOfQuestions, mUserLevel);
    private  int mCurrentIndex = 0;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game0);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mUser = (EditText) findViewById(R.id.userInput);
        mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mUserAnswer = Integer.parseInt(mUser.getText().toString());
                mCurrentIndex = (mCurrentIndex + 1);
                if(mCurrentIndex < mNumberOfQuestions) {
                    updateQuestion();
                }else{
                    Intent intent = new Intent(MathGame0Activity.this, EndOfGameActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
    private void updateQuestion(){
        mQuestionTextView.setText(mProblemBank.getQuestion(mCurrentIndex));
    }



}
