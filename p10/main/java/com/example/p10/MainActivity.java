package com.example.p10;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNo1, etNo2;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNo1 = findViewById(R.id.etNo1);
        etNo2 = findViewById(R.id.etNo2);
        tvResult = findViewById(R.id.tvResult);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String s1 = etNo1.getText().toString();
        String s2 = etNo2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Please enter numbers", Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }

        double n1 = Double.parseDouble(s1);
        double n2 = Double.parseDouble(s2);
        double res = 0;

        int id = item.getItemId();

        if (id == R.id.menu_addition) {
            res = n1 + n2;
            tvResult.setText("Result: " + res);
            return true;
        } else if (id == R.id.menu_subtraction) {
            res = n1 - n2;
            tvResult.setText("Result: " + res);
            return true;
        } else if (id == R.id.menu_multiplication) {
            res = n1 * n2;
            tvResult.setText("Result: " + res);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
