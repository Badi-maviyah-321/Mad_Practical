package com.example.p3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail, etPhone, etBirthDate, etBirthTime;
    private Spinner spCountry, spState;
    private RadioGroup rgGender;
    private CheckBox cbSports, cbMusic, cbReading;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etBirthDate = findViewById(R.id.etBirthDate);
        etBirthTime = findViewById(R.id.etBirthTime);
        spCountry = findViewById(R.id.spCountry);
        spState = findViewById(R.id.spState);
        rgGender = findViewById(R.id.rgGender);
        cbSports = findViewById(R.id.cbSports);
        cbMusic = findViewById(R.id.cbMusic);
        cbReading = findViewById(R.id.cbReading);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Setup Spinners
        String[] countries = {"India", "USA", "Canada", "UK"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        spCountry.setAdapter(countryAdapter);

        String[] states = {"Gujarat", "Maharashtra", "California", "Texas", "Ontario", "London"};
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, states);
        spState.setAdapter(stateAdapter);

        // Date Picker
        etBirthDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> etBirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            datePickerDialog.show();
        });

        // Time Picker
        etBirthTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                    (view, hourOfDay, minute1) -> etBirthTime.setText(hourOfDay + ":" + minute1), hour, minute, true);
            timePickerDialog.show();
        });

        btnSubmit.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String country = spCountry.getSelectedItem().toString();
            String state = spState.getSelectedItem().toString();

            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            String gender = "";
            if (selectedGenderId != -1) {
                RadioButton rbGender = findViewById(selectedGenderId);
                gender = rbGender.getText().toString();
            }

            StringBuilder interests = new StringBuilder();
            if (cbSports.isChecked()) interests.append("Sports ");
            if (cbMusic.isChecked()) interests.append("Music ");
            if (cbReading.isChecked()) interests.append("Reading ");

            String birthDate = etBirthDate.getText().toString();
            String birthTime = etBirthTime.getText().toString();

            String result = "Username: " + username +
                    "\nPassword: " + password +
                    "\nEmail: " + email +
                    "\nPhone: " + phone +
                    "\nCountry: " + country +
                    "\nState: " + state +
                    "\nGender: " + gender +
                    "\nInterests: " + interests.toString().trim() +
                    "\nBirth Date: " + birthDate +
                    "\nBirth Time: " + birthTime;

            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            intent.putExtra("info", result);
            startActivity(intent);
        });
    }
}
