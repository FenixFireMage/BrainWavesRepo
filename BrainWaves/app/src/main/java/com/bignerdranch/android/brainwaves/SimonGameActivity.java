package com.bignerdranch.android.brainwaves;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

enum Buttons {RED, BLUE, GREEN, PURPLE}

public class SimonGameActivity extends Activity {
    private boolean isDebug = true;
    private ShowPatternTask showPatterTask;
    private static final String TAG_GAME_ACTIVITY = "GAME_ACTIVITY";
    private static final String KEY_SEQUENCE = "sequence";
    private static final String KEY_PLAYER_SEQUENCE = "player_sequence";
    private static final String KEY_SCORE = "score";
    private static final String KEY_INGAME = "in_game";

    private SoundPool soundpool;
    private SparseIntArray soundsLoaded;
    int SE_MENU_BEEP = 0;
    int SE_CORRECT_PING = 1;
    int SE_CAR_DOOR = 2;
    int SE_CAMERA_CLICK = 3;
    int SE_ROCKS = 4;
    int SE_LASER = 5;
    int SE_WRONG = 6;

    private int[] buttonIds = {R.id.image_red, R.id.image_blue, R.id.image_green, R.id.image_purple};
    private LinkedList<Buttons> sequence;            //holds the entire sequence
    private LinkedList<Buttons> playerSequence;      //used to track where player is in sequence
    private int maxScore;
    private int score = 0;
    private boolean inGame = false;

    private double gameSpeed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game);

        Log.i(TAG_GAME_ACTIVITY, "onCreate");
        //setup variables
        soundsLoaded = new SparseIntArray();
        sequence = new LinkedList<>();
        playerSequence = new LinkedList<>();
        rand = new Random();
        maxScore = SimonMainActivity.maxScore;

        // setup the sound stuff b/c this is cool
        setupSoundPool();

        //setup listeners
        findViewById(R.id.image_red).setOnClickListener(new ButtonListener(Buttons.RED, SE_LASER));
        findViewById(R.id.image_blue).setOnClickListener(new ButtonListener(Buttons.BLUE, SE_ROCKS));
        findViewById(R.id.image_green).setOnClickListener(new ButtonListener(Buttons.GREEN, SE_CAMERA_CLICK));
        findViewById(R.id.image_purple).setOnClickListener(new ButtonListener(Buttons.PURPLE, SE_CAR_DOOR));

        if(savedInstanceState != null){
            // get me the sequence
            sequence = (LinkedList<Buttons>) savedInstanceState.getSerializable(KEY_SEQUENCE);
            // get me the player sequence
            playerSequence = (LinkedList<Buttons>) savedInstanceState.getSerializable(KEY_PLAYER_SEQUENCE);

            score = savedInstanceState.getInt(KEY_SCORE);
            inGame = savedInstanceState.getBoolean(KEY_INGAME);

            Log.i(TAG_GAME_ACTIVITY, "savedInstanceState sequence = " + sequence.toString());
            Log.i(TAG_GAME_ACTIVITY, "savedInstanceState playerSequence = " + playerSequence.toString());

            // If game is over, toggle buttons.
            // if game has ended, toggle the actual simon buttons.
            toggleStartButton();
            if (inGame) {
                toggleMenuButtons();

            } else {
                toggleMainButtons();
            }
        }

        findViewById(R.id.button_restartGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
        findViewById(R.id.button_toMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                toggleStartButton();
                startGame();
            }
        });

        // if the screen hasn't been rotated...
        if(savedInstanceState == null) {
            Log.i(TAG_GAME_ACTIVITY, "savedInstanceState == null");
            toggleMainButtons();    //Have to force buttons false so they'll be set true in startGame
        }
//        startGame();
    }

    // when the screen rotates, put the sequences onto the bundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG_GAME_ACTIVITY, "HELLO FRED!");
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_SEQUENCE, sequence);
        outState.putSerializable(KEY_PLAYER_SEQUENCE, playerSequence);
        outState.putInt(KEY_SCORE, score);
        outState.putBoolean(KEY_INGAME, inGame);
    }

    //do initialization and sound loading for sound effects
    private void setupSoundPool(){
        AudioAttributes.Builder ab = new AudioAttributes.Builder();
        ab.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder sb = new SoundPool.Builder();
        sb.setAudioAttributes(ab.build());
        sb.setMaxStreams(10);
        soundpool = sb.build();

        soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0){
                    Log.i("SOUNDPOOL", "Successfully loaded " + sampleId);
                } else {
                    Log.i("SOUNDPOOL", "fail to load sound " + sampleId);
                }
            }
        });

        int soundIds[] = {
                R.raw.correct_ping, R.raw.menu_beep, R.raw.car_door,
                R.raw.camera_click, R.raw.rocks, R.raw.laser, R.raw.wrong
        };
        int soundNames[] = {
                SE_CORRECT_PING, SE_MENU_BEEP, SE_CAR_DOOR,
                SE_CAMERA_CLICK, SE_ROCKS, SE_LASER, SE_WRONG
        };

        for(int i = 0; i < soundIds.length; i++){
            int id = soundpool.load(getApplicationContext(), soundIds[i], 1);
            soundsLoaded.append(soundNames[i], id);
        }

    }

    //Get sound from the soundNames map and play it.
    private void playSound(int key){
        if (!SimonMainActivity.soundOn){
            return;
        }
        int sound = soundsLoaded.get(key, -1);
        if (sound != -1) {
            soundpool.play(soundsLoaded.get(key), 1.f, 1.f, 0, 0, 1.0f);
            Log.i("SOUNDPOOL", "Played sound with key = " + key);
        }
    }

    //Handles button pressing and what action is done when a button is pressed
    private class ButtonListener implements View.OnClickListener {
        Buttons thisButton;
        int soundToPlay;
        ButtonListener(Buttons thisButton, int soundToPlay){
            this.thisButton = thisButton;
            this.soundToPlay = soundToPlay;
        }

        //does appropriate action for whether button pressed was correct or not
        @Override
        public void onClick(View view) {

            // if the showPatterTask is going, DONT GIVE CLICK ABILITY!!!
            if(showPatterTask != null){
                return;
            }

            Buttons nextButton = playerSequence.peek();
            Log.i(TAG_GAME_ACTIVITY, "SimonMainActivity.gameMode = " + SimonMainActivity.gameMode);
            Log.i(TAG_GAME_ACTIVITY, "playerSequence.toString() = " + playerSequence.toString());
            if (SimonMainActivity.gameMode.equals(SimonMainActivity.GAME_MODE_REGULAR) || SimonMainActivity.gameMode.equals(SimonMainActivity.GAME_MODE_DOUBLE_TROUBLE)){
                nextButton = playerSequence.remove();

            // if the game mode is tipsy tina, we are going in reverse
            } else if (SimonMainActivity.gameMode.equals(SimonMainActivity.GAME_MODE_TOPSY_TURVY)) {
                int lastPosition = playerSequence.size() - 1;
                nextButton = playerSequence.remove(lastPosition);
            }

            if (nextButton == thisButton){      //if correct button, continue down sequence.
                playSound(soundToPlay);
                updateDebugTextViews();
            } else {                            //if wrong button, continue to next sequence
                //play incorrect sound
                playSound(SE_WRONG);
                endGame();
                return;
            }

            if (playerSequence.isEmpty()){      //if right sequence, restart game with success.
                endTurn();
            }
        }
    }

    // Called when user moves into activity or presses "restart"
    private void startGame() {
        score = 0;
        inGame = true;
        toggleMenuButtons();
        toggleMainButtons();
        addRandomToSequence();
        updateDebugTextViews();
        startShowPatternTask();

    }

    // function for starting the Thread for flashing the user the sequence
    private void startShowPatternTask() {
        // start the showPatternTask
        if ( showPatterTask == null ){
            showPatterTask = new ShowPatternTask();
            showPatterTask.execute();
        } else {
            showPatterTask = null;
        }
    }

    //Add x Buttons to the sequence and reset playerSequence
    private void addRandomToSequence(){
        sequence.add(getRandomButton());

        // if the game mode is Speedy Spencer, add an extra piece.
        if (SimonMainActivity.gameMode == SimonMainActivity.GAME_MODE_DOUBLE_TROUBLE){
            sequence.add(getRandomButton());
        }
        playerSequence.clear();
        playerSequence.addAll(sequence);
    }

    //Generates a random button and returns it as a type Button.
    private Random rand;
    private static final List<Buttons> VALUES = Arrays.asList(Buttons.values());
    private Buttons getRandomButton(){
        return VALUES.get(rand.nextInt(VALUES.size()));
    }

    // Called when the player has successfully completed a sequence.
    private void endTurn(){
        addRandomToSequence();
        updateDebugTextViews();
        startShowPatternTask();

        // increment score
        score++;
        // based on the size of the queue upgrade the speed
        toggleSpeed();
    }

    // Called when user fails a sequence.
    // Reset all local variables.
    private void endGame() {
        SimonMakeToast.toast(getApplicationContext(), "Game Over... score: " + (score));

        if (score > maxScore){
            maxScore = score; //exclude last elem in sequence since they failed that one
            SimonMainActivity.saveScore(getApplicationContext(), maxScore);
        }
        sequence.clear();
        toggleMenuButtons();
        toggleMainButtons();
        inGame = false;
    }

    //toggles between enabled and disabled on main Simon buttons
    private void toggleMainButtons(){
        for(int id : buttonIds){
            ImageButton btn = findViewById(id);
            btn.setClickable(!(btn.isClickable()));
            Log.i("TOGGLE", "ImageButton " + btn.getId() + " : isEnabled = " + btn.isClickable());
        }
    }

    //toggles between visible and invisible on bottom menu buttons
    private void toggleMenuButtons(){
        Button btn = findViewById(R.id.button_restartGame);
        btn.setVisibility(btn.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        btn = findViewById(R.id.button_toMenu);
        btn.setVisibility(btn.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    // turn on or off the start button of the game activity
    private void toggleStartButton(){
        Button btn = findViewById(R.id.button_start);
        btn.setVisibility(btn.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    //Only works in debug mode:
    //Only works in debug mode:
    // update the sequence textview and playerSequence textview
    private void updateDebugTextViews(){
        if (isDebug) {
            TextView tv = findViewById(R.id.textview_debugSequenceOutput);
            tv.setText(sequence.toString());
            TextView tv2 = findViewById(R.id.textview_debugPlayerSequence);
            tv2.setText(playerSequence.toString());
        }
    }

    // functions for upping the speed of the flasher
    private void toggleSpeed(){
        // score comes from the endTurn function
        Log.i(TAG_GAME_ACTIVITY, "score = " + score);
        // up game speed based on turns
        if (score == 5){
            gameSpeed = 0.80;
            SimonMakeToast.toast(getApplicationContext(),"Speed Up!");
        } else if (score == 9) {
            gameSpeed = 0.40;
            SimonMakeToast.toast(getApplicationContext(),"Speed Up!");
        } else if (score == 13) {
            gameSpeed = 0.20;
            SimonMakeToast.toast(getApplicationContext(),"Speed Up!");
        }
        Log.i(TAG_GAME_ACTIVITY, "gameSpeed = " + gameSpeed); //(int)(500 * gameSpeed)
        Log.i(TAG_GAME_ACTIVITY, "(int)(500 * gameSpeed) = " + (int)(500 * gameSpeed)); //(int)(500 * gameSpeed)

    }

    class ShowPatternTask extends AsyncTask<Void, Integer, Void> {
        static final String STATE_OFF = "off";
        static final String STATE_ON = "on";

        // Id's for the translucent drawables
        private int [] flasherDrawableIds = {
            R.drawable.flash_red,
            R.drawable.flash_blue,
            R.drawable.flash_green,
            R.drawable.flash_purple
        };

        // ids for the imageviews for the flashers
        private int [] flasherIds = {
            R.id.imageview_top_left,
            R.id.imageview_top_right,
            R.id.imageview_bottom_left,
            R.id.imageview_bottom_right
        };

        // ids for sounds in the order this Thread needs
        private int [] flasherSoundKeys = {
            SE_LASER,
            SE_ROCKS,
            SE_CAMERA_CLICK,
            SE_CAR_DOOR
        };

        @Override
        protected void onPreExecute() {
            toggleMainButtons();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Buttons buttonThing;
            int buttonOrdinal;
            // move through the sequence list and display each
            try {
                // do a short wait for the user to track the screen better
                Thread.sleep(600);
                for (int i = 0; i < sequence.size(); i++) {
                    // get the button object from the queue
                    buttonThing = sequence.get(i);
                    // get the or
                    buttonOrdinal = buttonThing.ordinal();
                    // publish flash this ID to the UI!!
                    publishProgress(buttonOrdinal);
                    // ok, now wait 200ms
                    Thread.sleep((int)(500 * gameSpeed));
                    // the light is on for 1/2 second
                    // turn off
                    publishProgress(-1);
                    // wait shortly for the user to know the same button is used twice or more
                    // in a row.
                    Thread.sleep(50);

                }
            } catch (InterruptedException e){
                Log.i(TAG_GAME_ACTIVITY, "planned InterruptedException has triggered");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            // get me the position of the button to flash
            int pos = values[0];

            // if real position flash light and play sound
            if (pos >= 0) {
                // here we need to "flash" the color to the user
                ((ImageView) findViewById(flasherIds[pos])).setBackground(getResources().getDrawable(flasherDrawableIds[pos]));

                // play sound
                playSound(flasherSoundKeys[pos]);

                // if not real pos, turn off
            } else if (pos == -1){
                setFlashers(STATE_OFF);
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // set the showPatterTask back to null, for it may start again
            toggleMainButtons();

            // set showPatterTask to null so it can go again
            showPatterTask = null;
            super.onPostExecute(aVoid);
        }

        // function to turn on or off all the flashers
        private void setFlashers(String state){
            for(int i = 0; i < flasherIds.length; i++) {
                if (state.equals("on")) {
                    // flip all the flashers to on
                    // ( (ImageView) findViewById(flasherIds[i])).setImageResource();
                } else if (state.equals("off")) {
                    // flip all the flashers to off
                    // set ImageView to empty drawable shape
                    ( (ImageView) findViewById(flasherIds[i])).setBackground(getResources().getDrawable(R.drawable.flash_off));
                }
            }
        }
    }


}
