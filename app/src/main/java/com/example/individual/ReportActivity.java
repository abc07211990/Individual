package com.example.individual;
//package pl.pawelkleczkowski.customgaugeexample;
import pl.pawelkleczkowski.customgauge.CustomGauge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ReportActivity extends AppCompatActivity {
    CustomGauge gauge;
    TextView gaugeValue;
    TextView commentW, diffW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Bundle bundle = getIntent().getExtras();
        double height = bundle.getDouble("height");
        int weight = bundle.getInt("weight");
        int unitW = bundle.getInt("unitW");
        int wKg;
        int bmi, wlimitUp, wlimitLow;
        if (unitW == 1) {
            wKg = (int)(weight * 0.45359);
            wlimitUp = (int)(25 * (height * height) / 0.45359);
            wlimitLow = (int)(18.5 * (height * height) / 0.45359);
        } else {
            wKg = weight;
            wlimitUp = (int)(25 * (height * height));
            wlimitLow = (int)(20 * (height * height));
        }
        bmi = (int)(wKg / (height*height));


        //Gauge
        gauge = (CustomGauge) findViewById(R.id.gauge);
        if (bmi < 10) {
            gauge.setValue(10);
        } else if (bmi > 40) {
            gauge.setValue(40);
        } else {
            gauge.setValue(bmi);
        }
        gaugeValue = (TextView) findViewById(R.id.gauge_value);
        gaugeValue.setText(Integer.toString(bmi));
        if (bmi >= 18.5 && bmi < 25) {
            gaugeValue.setTextColor(getResources().getColor(R.color.Green));
        } else {
            gaugeValue.setTextColor(getResources().getColor(R.color.Red));
        }

        //Comment
        commentW = (TextView) findViewById(R.id.commentW);
        if (bmi <= 15){
            commentW.setText(getString(R.string.wm3));
        } else if (bmi <= 16) {
            commentW.setText(getString(R.string.wm2));
        } else if (bmi < 18.5) {
            commentW.setText(getString(R.string.wm1));
        } else if (bmi <= 25) {
            commentW.setText(getString(R.string.w0));
        } else if (bmi <= 30) {
            commentW.setText(getString(R.string.wp1));
        } else if (bmi <= 35) {
            commentW.setText(getString(R.string.wp2));
        } else if (bmi <= 40) {
            commentW.setText(getString(R.string.wp3));
        } else {
            commentW.setText(getString(R.string.wp4));
        }

        diffW = (TextView) findViewById(R.id.diff_normW);
        String advice = "";
        //System.out.println(Integer.toString(wlimitUp));
        //System.out.println(Integer.toString(weight));
        //System.out.println(Integer.toString(wlimitLow));

        if (weight < wlimitLow) {
            advice = getString(R.string.Wadvice1) +
                    getString(R.string.WadvP) +
                    Integer.toString((wlimitLow - weight)) +
                    getResources().getStringArray(R.array.unitW)[unitW];

        } else if (weight > wlimitUp) {
            advice = getString(R.string.Wadvice1) +
                    getString(R.string.WadvD) +
                    Integer.toString((weight - wlimitLow)) +
                    getResources().getStringArray(R.array.unitW)[unitW];
        }
        diffW.setText(advice);
    }
}
