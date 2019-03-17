package com.example.individual;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class BMI_input extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button submitButton;
    EditText height1, height2, weight;
    Spinner spinnerUnitH, spinnerUnitW;
    String[] unitH_Array, unitW_Array;
    int unitH, unitW;
    DrawerLayout drawerL;
    ActionBarDrawerToggle dToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_input);

        drawerL = (DrawerLayout) findViewById(R.id.drawer);
        dToggle = new ActionBarDrawerToggle(this,drawerL,R.string.open,R.string.close);
        drawerL.addDrawerListener(dToggle);
        dToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        height1 = (EditText) findViewById(R.id.height1);
        height2 = (EditText) findViewById(R.id.height2);
        weight = (EditText) findViewById(R.id.weight);
        submitButton = (Button) findViewById(R.id.submit);

        unitH_Array = getResources().getStringArray(R.array.unitH);
        unitW_Array = getResources().getStringArray(R.array.unitW);
        spinnerUnitH = (Spinner) findViewById(R.id.spinner_unitH);
        spinnerUnitW = (Spinner) findViewById(R.id.spinner_unitW);
        ArrayAdapter<String> adapterUnitH = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, unitH_Array);
        ArrayAdapter<String> adapterUnitW = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, unitW_Array);
        spinnerUnitH.setAdapter(adapterUnitH);
        spinnerUnitW.setAdapter(adapterUnitW);

        spinnerUnitH.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                unitH = arg0.getSelectedItemPosition();
                String[] hintH = getResources().getStringArray(R.array.hintH)[unitH].split(",");
                height1.setHint(hintH[0]);
                height2.setHint(hintH[1]);

                if (unitH == 1){
                    height2.setVisibility(View.VISIBLE) ;
                } else {
                    height2.setVisibility(View.INVISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerUnitW.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                unitW = arg0.getSelectedItemPosition();
                String hintW = getResources().getStringArray(R.array.hintW)[unitW];
                weight.setHint(hintW);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calcBMI(View view) {
        String wS = weight.getText().toString();
        String hS1 = height1.getText().toString();
        String hS2 = height2.getText().toString();
        unitH = spinnerUnitH.getSelectedItemPosition();
        unitW = spinnerUnitW.getSelectedItemPosition();
        if (wS.equals("") || hS1.equals("") || (hS2.equals("") && unitH == 1)) {
            Toast.makeText(BMI_input.this, R.string.inputWarning,
                    Toast.LENGTH_LONG).show();
        } else {
            savePreferences(unitH, unitW, hS1, hS2, wS);
            int w = Integer.parseInt(wS);
            int h1 = Integer.parseInt(hS1);
            double hM;
            if (unitH == 1) {
                int h2 = Integer.parseInt(hS2);
                hM = (h1*12 + h2) * 0.0254;
            } else {
                hM = h1 / 100.0;
            }
            Intent intent = new Intent(this, ReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("height", hM);
            bundle.putInt("weight", w);
            bundle.putInt("unitW", unitW);
            intent.putExtras(bundle);
            startActivity(intent);
        }



    }

    public void savePreferences(int unitH, int unitW, String hS1, String hS2, String wS) {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        pref.edit().putInt("unitH", unitH).commit();
        pref.edit().putInt("unitW", unitW).commit();
        pref.edit().putString("hS1", hS1).commit();
        pref.edit().putString("hS2", hS2).commit();
        pref.edit().putString("wS", wS).commit();
    }
    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        spinnerUnitH.setSelection(pref.getInt("unitH", 0));
        spinnerUnitW.setSelection(pref.getInt("unitW", 0));
        weight.setText(pref.getString("wS", ""));
        height1.setText(pref.getString("hS1", ""));
        height2.setText(pref.getString("hS2", ""));

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
