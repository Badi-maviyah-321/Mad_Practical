package com.example.p5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        tvResult = findViewById(R.id.tvResult);

        // Spinner Data
        List<String> data = Arrays.asList("Android", "Java", "Python");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Alert Dialog
        findViewById(R.id.btnAlert).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Do you want to continue?")
                    .setPositiveButton("OK", (d, w) ->
                            tvResult.setText("User selected OK"))
                    .setNegativeButton("Cancel", (d, w) ->
                            tvResult.setText("User selected Cancel"))
                    .show();
        });
    }
}
