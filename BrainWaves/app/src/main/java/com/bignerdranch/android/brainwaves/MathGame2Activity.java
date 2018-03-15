package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class MathGame2Activity extends AppCompatActivity {
    private EditText mUser;
    private Button mNextButton;
    private TextView enter;
    private TextView mQuestionTextView;
    private int mUserLevel = 10;
    private int mNumberOfQuestions= 3;
    private MathQuestionGenerator2 mProblemBank = new MathQuestionGenerator2(mNumberOfQuestions, mUserLevel);
    private  int mCurrentIndex = 0;
    private int mUserAnswer;
    int finalScore;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game0);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mUser = (EditText) findViewById(R.id.userInput);
        mUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)){
                    mUserAnswer = Integer.parseInt(mUser.getText().toString());
                    mProblemBank.checkAnswer(mCurrentIndex, mUserAnswer);
                    mCurrentIndex ++;
                    if(mCurrentIndex < mNumberOfQuestions) {
                        mUser.setText("");
                        updateQuestion();
                    }else {
                        finalScore = mProblemBank.getFinalScore();
                        Intent intent = new Intent(MathGame2Activity.this, EndOfMathGame2Activity.class);
                        intent.putExtra("userScore", finalScore);
                        intent.putExtra("numberOfQuestion", mNumberOfQuestions);
                        startActivity(intent);
                    }
                    Log.i(TAG, "ENTER PRESSED");
            }
            return false;
            }
        });

       /* mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserAnswer = Integer.parseInt(mUser.getText().toString());
                mProblemBank.checkAnswer(mCurrentIndex, mUserAnswer) ;
                mCurrentIndex = (mCurrentIndex + 1);
                if(mCurrentIndex < mNumberOfQuestions) {
                    mUser.setText("");
                    updateQuestion();
                }else{
                    finalScore = mProblemBank.getFinalScore();
                    Intent intent = new Intent(MathGame1Activity.this, EndOfMathGame2Activity.class);
                    intent.putExtra("userScore", finalScore);
                    intent.putExtra("numberOfQuestion", mNumberOfQuestions);
                    startActivity(intent);
                }
            }
        });*/


    }
    private void updateQuestion(){
        mQuestionTextView.setText(mProblemBank.getQuestion(mCurrentIndex));
    }



}
