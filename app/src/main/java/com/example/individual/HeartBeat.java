package com.example.individual;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HeartBeat extends AppCompatActivity {
    TextView timerDp;
    int timer_status = 0;
    CountDownTimer timerCD;
    EditText hbrV, ageV;
    TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat);
        timerDp = (TextView) findViewById(R.id.hbTimer);
        hbrV = (EditText) findViewById(R.id.num_hb);
        ageV = (EditText) findViewById(R.id.num_age);
        comment = (TextView) findViewById(R.id.hbrReport);
    }

    public void checkHB(View view) {
        int age, hbr, mxhbr, uzhbr, lzhbr;
        String hbrS = hbrV.getText().toString();
        String ageS = ageV.getText().toString();
        if (hbrS.equals("")) {
            Toast.makeText(HeartBeat.this, R.string.hbrInputWarning,
                    Toast.LENGTH_LONG).show();
        } else if (ageS.equals("")) {
            Toast.makeText(HeartBeat.this, R.string.ageInputWarning,
                    Toast.LENGTH_LONG).show();
        } else {
            hbr = Integer.parseInt(hbrS);
            age = Integer.parseInt(ageS);
            mxhbr = 200-age;
            uzhbr = (int) (mxhbr *0.85);
            lzhbr = (int) (mxhbr *0.5);
            if (hbr < lzhbr){
                // too slow
                String com = getString(R.string.hbrTooLow) +
                        " " + Integer.toString(lzhbr);
                comment.setText(com);
            }else if (hbr > uzhbr) {
                // too fast
                String com = getString(R.string.hbrTooLow) +
                        " " + Integer.toString(uzhbr);
                comment.setText(com);
            } else {
                //good
                String com = getString(R.string.hbrOK) +
                        " " + Integer.toString(lzhbr) +
                        " " + getString(R.string.to) +
                        " " + Integer.toString(uzhbr);
                comment.setText(com);
            }



        }
    }

    void startTimer() {
        timerCD = new CountDownTimer(60000, 1000) {
            public void onTick(long millisecToFinish) {
                int secToFinish = (int) (millisecToFinish / 1000);
                timerDp.setText(Long.toString(secToFinish));
            }
            public void onFinish() {
                timerDp.setText("00");
                timer_status = 2;
            }
        };
        timerCD.start();
    }
    void cancelTimer() {
        if(timerCD!=null)
            timerCD.cancel();
    }

    public void onClickTimer(View view) {
        if (timer_status == 0) {
            //at Rest, start timer
            startTimer();
            timer_status = 1;
        } else if (timer_status == 1) {
            //counting, stop it
            cancelTimer();
            timer_status = 2;
        } else if (timer_status ==2) {
            //Reset Timer
            timerDp.setText(getString(R.string.count60));
            timer_status = 0;
        }
    }
}
