package com.bignerdranch.android.brainwaves.ReferenceClasses;

import android.util.Log;

import java.util.Random;
import android.widget.Button;
import android.widget.TextView;
import static android.content.ContentValues.TAG;

/**
 * Created by Liz on 3/10/18.
 */

public class MathQuestionGenerator2 {
    private char[] operations = new char[]{'-','+','/','*'};
    private int[] mAnswer;
    private String[] questions;
    private char operation;
    private int mFirst,mSecond;
    private int mLevel;
    private int mScore;
    private int mNumberOfQuestions;



    public MathQuestionGenerator2(int numberOfQuestions, int level){
        this.mLevel = level;
        this.mScore = 0;
        this.mNumberOfQuestions= numberOfQuestions;
        this.mAnswer = new int[numberOfQuestions];
        this.questions = new String[numberOfQuestions];
        setQuestions(this.mNumberOfQuestions);
    }
    private void setQuestions(int numberOfQuestions){
        for(int i = 0; i < numberOfQuestions; i++) {
          this.mFirst = generatorRandomNumber(mLevel-1 ) + 2;
          this.mSecond = generatorRandomNumber(mLevel-1) + 2;
          this.operation = operations[generatorRandomNumber(operations.length)];
          setAnswer(i);
          this.questions[i] = this.mFirst +" "+ this.operation+" "+this.mSecond+" =";
        }
    }
    public int getFinalScore(){
        return this.mScore;
    }
    public String getQuestion( int index){
        return this.questions[index];
    }
    public void checkAnswer(int index, int userInput){
        if(this.mAnswer[index] == userInput){
            this.mScore++;
        }
    }

    private void setAnswer(int index){
        switch (this.operation){
            case '+':
                this.mAnswer[index] = mFirst + mSecond;
                break;
            case '-':
                this.mAnswer[index] = mFirst + mSecond;
                break;
            case '*':
                this.mAnswer[index] = mFirst * mSecond;
                break;
            case '/':
                if(this.mFirst % this.mSecond != 0){
                    makeNumbersDivisable();
                }
                this.mAnswer[index] = mFirst/ mSecond;
                break;
            default:
                Log.d(TAG, "Something wrong in check method in class mathQuestiongenerator",new Exception());
                //System.out.printf("Something wrong in check method in class mathQuestiongenerator");
                break;
        }
    }
    private int generatorRandomNumber(int bound) {
        Random random = new Random();
        int randomInt = random.nextInt(bound);
        return randomInt;
    }
    private void makeNumbersDivisable(){
        // figure out how to divide numbers
        this.mSecond = 3;
        this.mFirst = 12;

    }
}
