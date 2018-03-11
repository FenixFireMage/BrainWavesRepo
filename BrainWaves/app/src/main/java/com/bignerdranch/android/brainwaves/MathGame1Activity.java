package com.bignerdranch.android.brainwaves;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MathGame1Activity extends AppCompatActivity {
    private Button mNextButton;
    private TextView mQuestionTextView;

    private MathQuestions[] mproblemBank = new MathQuestions[]{
            new MathQuestions(R.string.problem1,8),
            new MathQuestions(R.string.problem2, 15),
            new MathQuestions(R.string.problem3, 4),
            new MathQuestions(R.string.problem4, 2),
    };
    private  int mCurrentIndex = 0;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game1);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        // get input and do stuff
            //
        mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex +1) % mproblemBank.length;
                updateQuestion();
            }
        });

    }
    private void updateQuestion(){
        int question = mproblemBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

}
