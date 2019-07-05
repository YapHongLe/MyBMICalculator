package com.example.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mybmicalculator.R;

import java.util.Calendar;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvCalcDate;
    TextView tvCalcBMI;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvCalcDate = findViewById(R.id.textViewCalculate);
        tvCalcBMI = findViewById(R.id.textViewCalBMI);
        tvStatus = findViewById(R.id.textViewStatus);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Step 1a: Get the user input from the EditText and store it in a variable
                float floatWeight = Float.parseFloat(etWeight.getText().toString());
                float floatHeight = Float.parseFloat(etHeight.getText().toString());
                float floatBMI = floatWeight / (floatHeight * floatHeight);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1)+"/"+
                        now.get(Calendar.YEAR)+" "+
                        now.get(Calendar.HOUR_OF_DAY)+":"+
                        now.get(Calendar.MINUTE);

                if (floatBMI < 18.5) {
                    tvStatus.setText("You are underweight");
                } else if (floatBMI >= 18.5 && floatBMI <= 24.9) {
                    tvStatus.setText("Your BMI is normal");
                } else if (floatBMI >= 25 && floatBMI < 30) {
                    tvStatus.setText("You are overweight");
                } else {
                    tvStatus.setText("You are obese");
                }

                // Step 1b: Obtain an instance of the SharedPreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                // Step 1c: Obtain an instance of the SharedPreferences Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();

                // Step 1d: Add the key-value pair
                prefEdit.putFloat("bmi", floatBMI);
                prefEdit.putString("date", datetime);
                prefEdit.putString("status", tvStatus.getText().toString());

                // Step 1e: Call commit() to save changes into SharedPreferences
                prefEdit.commit();

                // Step 2b: Retrieve the saved data from the SharedPreference object
                String msg = prefs.getString("date", "");
                Float msg2 = prefs.getFloat("bmi", 0);
                String msg3 = prefs.getString("status", "");


                // Step 2c: Update the UI element with the value
                tvCalcDate.setText("Last calculated Date: " + msg);
                tvCalcBMI.setText("Last calculated BMI: " + msg2.toString());
                tvStatus.setText(msg3);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Step 1b: Obtain an instance of the SharedPreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                // Step 1c: Obtain an instance of the SharedPreferences Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putFloat("bmi", 0);
                prefEdit.putString("date", "");
                prefEdit.putString("status", "");
                // Step 1e: Call commit() to save changes into SharedPreferences
                prefEdit.commit();

                // Step 2b: Retrieve the saved data from the SharedPreference object
                String msg = prefs.getString("date", "");
                Float msg2 = prefs.getFloat("bmi", 0);
                String msg3 = prefs.getString("status", "");


                // Step 2c: Update the UI element with the value
                tvCalcDate.setText("Last calculated Date: " + msg);
                tvCalcBMI.setText("Last calculated BMI: " + msg2.toString());
                tvStatus.setText(msg3);

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 2b: Retrieve the saved data from the SharedPreference object
        String msg = prefs.getString("date", "");
        Float msg2 = prefs.getFloat("bmi", 0);
        String msg3 = prefs.getString("status", "");


        // Step 2c: Update the UI element with the value
        tvCalcDate.setText("Last calculated Date: " + msg);
        tvCalcBMI.setText("Last calculated BMI: " + msg2.toString());
        tvStatus.setText(msg3);
    }
}
