package com.example.p4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class MainActivity extends AppCompatActivity {

    Button btnWeb, btnLocation, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWeb = findViewById(R.id.btnWeb);
        btnLocation = findViewById(R.id.btnLocation);
        btnShare = findViewById(R.id.btnShare);

        // Open Website
        btnWeb.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com"));
            startActivity(intent);
        });

        // Open Location
        btnLocation.setOnClickListener(v -> {
            Uri location = Uri.parse("geo:0,0?q=New Delhi");
            Intent intent = new Intent(Intent.ACTION_VIEW, location);
            startActivity(intent);
        });

        // Share Text
        btnShare.setOnClickListener(v -> {
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Share using")
                    .setText("Hello from Practical 4 (Implicit Intent)")
                    .startChooser();
        });
    }
}
