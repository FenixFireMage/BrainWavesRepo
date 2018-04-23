package com.bignerdranch.android.brainwaves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
//import com.bignerdranch.android.brainwaves.IQtest.FirstActivity;
import com.bignerdranch.android.brainwaves.MathQuiz.MyActivity;

public class IntelligenceAppMenuActivity extends AppCompatActivity {

    @BindView(R.id.playiq) Button _playiq;
    @BindView(R.id.playmath) Button _playmath;
    Intent main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.intelligence_app_menu);
        ButterKnife.bind(this);


//        _playiq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                main = new Intent(IntelligenceAppMenuActivity.this, FirstActivity.class);
//                startActivity(main);
//
//            }
//        });
        _playmath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main = new Intent(IntelligenceAppMenuActivity.this, MyActivity.class);
                startActivity(main);


            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        Intent main = new Intent(IntelligenceAppMenuActivity.this, GameMenuActivity.class);
//        startActivity(main);
//    }
}
