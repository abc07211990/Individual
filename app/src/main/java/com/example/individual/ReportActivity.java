package com.example.individual;
//package pl.pawelkleczkowski.customgaugeexample;
import pl.pawelkleczkowski.customgauge.CustomGauge;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ReportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CustomGauge gauge;
    TextView gaugeValue;
    TextView commentW, diffW;

    DrawerLayout drawerL;
    ActionBarDrawerToggle dToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        drawerL = (DrawerLayout) findViewById(R.id.drawer);
        dToggle = new ActionBarDrawerToggle(this,drawerL,R.string.open,R.string.close);
        drawerL.addDrawerListener(dToggle);
        dToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);


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
            advice = getString(R.string.Wadvice1) + " " +
                    getString(R.string.WadvP) + " " +
                    Integer.toString((wlimitLow - weight)) + " " +
                    getResources().getStringArray(R.array.unitW)[unitW];

        } else if (weight > wlimitUp) {
            advice = getString(R.string.Wadvice1) + " " +
                    getString(R.string.WadvD) + " " +
                    Integer.toString((weight - wlimitLow)) + " " +
                    getResources().getStringArray(R.array.unitW)[unitW];
        }
        diffW.setText(advice);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.bmi)
        {
            Intent intent = new Intent(this, BMI_input.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.hbr)
        {
            Intent intent = new Intent(this, HeartBeat.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.about)
        {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);
            builder.setTitle(R.string.about_bmi);
            builder.setMessage(R.string.about_bmi_msg);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        { }
                    });
            builder.create();
            builder.show();

        }
        return false;
    }
}
