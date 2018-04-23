package com.bignerdranch.android.brainwaves.simon;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.brainwaves.R;

public class SimonAboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_about);

        TextView tv = findViewById(R.id.textView_soundSource);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
