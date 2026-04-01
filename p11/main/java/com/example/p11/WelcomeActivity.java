package com.example.p11;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        String email = getIntent().getStringExtra("user_email");
        
        if (email != null && !email.isEmpty()) {
            tvWelcome.setText("Welcome " + email + "!");
        } else {
            tvWelcome.setText("Welcome!");
        }
    }
}
