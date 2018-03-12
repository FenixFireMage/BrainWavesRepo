package com.bignerdranch.android.brainwaves;

import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Liz on 3/10/18.
 */

public class MathQuestionGenerator {
    private char[] operations = new char[]{'-','+','/','*'};
    private int[] mAnswer;
    private String[] questions;
    private char operation;
    private int mFirst,mSecond;
    private int mLevel;

    private int mNumberOfQuestions;



    public MathQuestionGenerator(int numberOfQuestions,int level){
        this.mLevel = level;
        this.mNumberOfQuestions= numberOfQuestions;
        this.mAnswer = new int[numberOfQuestions];
        this.questions = new String[numberOfQuestions];
        setQuestions(this.mNumberOfQuestions);
    }
    private void setQuestions(int numberOfQuestions){
        for(int i = 0; i < numberOfQuestions; i++) {
            setFirst(generatorRandomNumber(mLevel + 1));
            setSecond(generatorRandomNumber(mLevel + 1));
            setOperation(generatorRandomNumber(operations.length));
            setAnswer(i);
            setQuestion(i);
        }
    }
    public int getAnswer(int index){
        return this.mAnswer[index];
    }
    public String getQuestion( int index){
        return this.questions[index];
    }
    private void setFirst(int first){
        this.mFirst = first;
    }
    private void setSecond(int second){
        this.mSecond = second;
    }
    private void setOperation(int index){
        this.operation = operations[index];
    }
    private void setQuestion(int index){
        this.questions[index] = this.mFirst +" "+ this.operation+" "+this.mSecond+" =";
    }
    public void setAnswer(int index){
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
                    makeNumbersDivisable(this.mFirst, this.mSecond);
                }
                this.mAnswer[index] = mFirst/ mSecond;
                break;
            default:
                System.out.printf("Something wrong in check method in class mathQuestiongenerator");
                break;
        }
    }
    private int generatorRandomNumber(int bound) {
        Random random = new Random();
        int randomInt = random.nextInt(bound);
        return randomInt;
    }
    private void makeNumbersDivisable(int firstValue, int secondValue){
        // figure out how to divide numbers
        setFirst(6);
        setSecond(2);
    }

}
