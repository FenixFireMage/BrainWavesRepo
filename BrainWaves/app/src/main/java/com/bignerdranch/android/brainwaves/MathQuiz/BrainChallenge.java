package com.bignerdranch.android.brainwaves.MathQuiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.bignerdranch.android.brainwaves.R;


public class BrainChallenge extends Activity {

    //Create all the private button fields
    private Button submitButton;
    private EditText inputText;
    private TextView viewText;
    private TextView timers;
    private TextView rightText;
    public static String myRightMessage;
    public static String myWrongMessage;
    private TextView wrongText;

    private int number1;
    private int wrong;
    private int right;
    private int number2;
    private TextView titleText;
    private TextView textView4;
    private int inputValue;
    private int totalValue;
    private int currentNumber;
    private String numberString;
    private ArrayList runningTotal;
    private Intent scoreIntent;
    private String myTextIsCool;
    private List<String> operation;
    private int indexop;
    CounterClass timer = new CounterClass(30000, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        operation = new ArrayList<String>();
        operation.add("+");
        operation.add("-");
        operation.add("*");
        operation.add("/");

        Button submit = findViewById(R.id.button);

        viewText = findViewById(R.id.myNumbers);

        timers = findViewById(R.id.timers);

        inputText = findViewById(R.id.numberInputed);

        titleText = findViewById(R.id.textView);
        textView4 = findViewById(R.id.textView4);

        rightText = findViewById(R.id.right);
        wrongText = findViewById(R.id.wrong);
        textView4.startAnimation(AnimationUtils.loadAnimation(BrainChallenge.this, android.R.anim.slide_out_right));


        wrong = 0;
        right = 0;
        rightText.setText("Number Right: " );
        wrongText.setText("Number Wrong: " );

        currentNumber = 1;
        titleText.setText("Question " + currentNumber);

//        timers.setText("00:02:00");
        timers.setText("30");
        // A timer of 60 seconds to play for, with an interval of 1 second (1000 milliseconds)
//        CounterClass timer = new CounterClass(60000, 1000);
        timer.start();

        runningTotal = new ArrayList();
//        boolean hello = mathString();
        newProblem();

        submit.setOnClickListener(new View.OnClickListener() {
            //Onclick button function
            public void onClick(View view) {
                if(inputText.getText().toString().isEmpty())
                    inputText.setText("0");

                if (currentNumber < 6) {

                    if (checkAnswer()) {
                        //showRightAlert();
                        currentNumber++;
//                        showRightAlert();
                        right++;
                        inputText.setText("");
                        titleText.setText("Question " + currentNumber);
                        rightText.setText("Number Right: " + right);
//                        newProblem();

                    } else {
                        //showWrongAlert();
                        wrong++;
                        currentNumber++;
                        titleText.setText("Question " + currentNumber);
                        wrongText.setText("Number Wrong: " + wrong);
                        inputText.setText("");
//                        newProblem();
                    }
                    if (currentNumber < 6){
                        newProblem();
                    }else {

                        scoreIntent = new Intent(getApplicationContext(), ScoreScreen.class);
                        Bundle extras = new Bundle();
                        extras.putString("myRightMessage", Integer.toString(right));
                        extras.putString("myWrongMessage", Integer.toString(wrong));
                        scoreIntent.putExtras(extras);

                        startActivity(scoreIntent);
                    }
                } else {
                    //String myWrong = wrong+ "";
                    //String myRight = right + "";

                    if (checkAnswer()) {
                        //showRightAlert();
                        currentNumber++;
//                        showRightAlert();
                        right++;
                        inputText.setText("");
                        titleText.setText("Question " + currentNumber);
                        rightText.setText("Number Right: " + right);

                    } else {
                        //showWrongAlert();
                        wrong++;
                        currentNumber++;
                        titleText.setText("Question " + currentNumber);
                        wrongText.setText("Number Wrong: " + wrong);
                        inputText.setText("");
                    }

                    scoreIntent = new Intent(getApplicationContext(), ScoreScreen.class);
                    Bundle extras = new Bundle();
                    extras.putString("myRightMessage", Integer.toString(right));
                    extras.putString("myWrongMessage", Integer.toString(wrong));
                    scoreIntent.putExtras(extras);

                    startActivity(scoreIntent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newProblem() {
        Random rand = new Random();
        indexop = rand.nextInt(operation.size());
//        number1 = (int) (Math.random() * 15 + 1);
//        number2 = (int) (Math.random() * 15 + 1);
        //indexop = (int)Math.random()*operation.size();
        number1 = (rand.nextInt(12)+1);
        number2 = (rand.nextInt(12)+1);

//        if(number1<number2)
//            number1+=(number2*2+1);
//if(timer.isFinished())
//{
        //   currentNumber = 5;
//}

//        int tempNum=(number1/number2)*number2;
//        if(number1!=tempNum)
//            number1=tempNum;

        if (operation.get(indexop).equals("+")) {
            totalValue = number1 + number2;
        } else if (operation.get(indexop).equals("-")) {
            while(number1<number2) {
                number1 = (rand.nextInt(13));
                number2 = (rand.nextInt(13));
            }
            totalValue = number1 - number2;
        } else if (operation.get(indexop).equals("*")) {
            totalValue = number1 * number2;
        } else {
            number1 = number2 * (rand.nextInt(4)+1);
            totalValue = number1 / number2;
        }


        String numberString = number1 + operation.get(indexop) + number2;
        viewText.setText(numberString);


    }

   /* private void showWrongAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your answer is wrong  ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

   /* private void showRightAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You are right!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

    //Check the answer submitted
    private boolean checkAnswer() {

        if (operation.get(indexop).equals("+")) {
            if (number1 + number2 == Integer.parseInt(inputText.getText().toString())) {
                runningTotal.add("true");
                return true;
            } else {
                runningTotal.add("false");
                return false;
            }
        } else if (operation.get(indexop).equals("-")) {
            if (number1 - number2 == Integer.parseInt(inputText.getText().toString())) {
                runningTotal.add("true");
                return true;
            } else {
                runningTotal.add("false");
                return false;
            }
        } else if (operation.get(indexop).equals("*")) {
            if (number1 * number2 == Integer.parseInt(inputText.getText().toString())) {
                runningTotal.add("true");
                return true;
            } else {
                runningTotal.add("false");
                return false;
            }
        } else {
            if (number1 / number2 == Integer.parseInt(inputText.getText().toString())) {
                runningTotal.add("true");
                return true;
            } else {
                runningTotal.add("false");
                return false;
            }
        }


    }

    public class CounterClass extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
//            String hms = String.format("%02d:%02d:%02d",
                    String hms = String.format("%02d",
//                    TimeUnit.MILLISECONDS.toHours(millis),
//                    TimeUnit.MILLISECONDS.toMinutes(millis)
//                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
//                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            timers.setText(hms);


        }

        @Override
        public void onFinish() {
            timers.setText("Time is up");
            currentNumber = 5;

        }
        public boolean isFinished() {

            return true;
        }
    }
    @Override
    public void onBackPressed() {
        Intent main = new Intent(BrainChallenge.this, MathQuizActivity.class);
        startActivity(main);
    }
}
