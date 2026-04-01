package com.example.p11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    CheckBox cbRememberMe;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Load saved values if "Remember me" was checked previously
        if (sharedPreferences.getBoolean("remember", false)) {
            etEmail.setText(sharedPreferences.getString("email", ""));
            etPassword.setText(sharedPreferences.getString("password", ""));
            cbRememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (cbRememberMe.isChecked()) {
                // Store values in SharedPreferences
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putBoolean("remember", true);
                editor.apply();
            } else {
                // Clear SharedPreferences if not checked
                editor.clear();
                editor.apply();
            }

            // Open next activity with welcome message
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.putExtra("user_email", email);
            startActivity(intent);
        });
    }
}
