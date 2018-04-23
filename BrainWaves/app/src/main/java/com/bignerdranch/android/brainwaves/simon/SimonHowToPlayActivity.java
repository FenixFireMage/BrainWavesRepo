package com.bignerdranch.android.brainwaves.simon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bignerdranch.android.brainwaves.R;

public class SimonHowToPlayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_how_to_play);

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
