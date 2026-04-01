package com.example.p3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tvDisplay = findViewById(R.id.tvDisplay);
        String info = getIntent().getStringExtra("info");
        tvDisplay.setText(info);
    }
}
