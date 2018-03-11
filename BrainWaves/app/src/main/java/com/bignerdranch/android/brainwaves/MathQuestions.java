package com.bignerdranch.android.brainwaves;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Liz on 3/10/18.
 */

public class MathQuestions {
    private  int mTextResId;
    private  int mAnswer;
    private int mScore;

    public MathQuestions(int textResId,int answer){
        this.mAnswer = answer;
        this.mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int answer) {
        mAnswer = answer;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
