package com.example.individual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class BMI_input extends AppCompatActivity {
    Button submitButton;
    EditText height1, height2, weight;
    Spinner spinnerUnitH, spinnerUnitW;
    String[] unitH_Array, unitW_Array;
    int unitH, unitW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_input);

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
                /*
                if (unitH == 1){
                    height2.setFocusable(true);
                } else {
                    height2.setFocusable(false);
                }
                */
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
}
