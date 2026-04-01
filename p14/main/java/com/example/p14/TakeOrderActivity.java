package com.example.p14;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class TakeOrderActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etPhone, etItemName, etQuantity;
    TextInputLayout tilPhone, tilItem, tilQuantity;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);

        myDb = new DatabaseHelper(this);

        etPhone = findViewById(R.id.etPhone);
        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        
        tilPhone = findViewById(R.id.tilPhone);
        tilItem = findViewById(R.id.tilItem);
        tilQuantity = findViewById(R.id.tilQuantity);
        
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });
    }

    private void saveOrder() {
        String phone = etPhone.getText().toString().trim();
        String item = etItemName.getText().toString().trim();
        String qty = etQuantity.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            tilPhone.setError("Phone required");
            return;
        } else {
            tilPhone.setError(null);
        }

        if (TextUtils.isEmpty(item)) {
            tilItem.setError("Item name required");
            return;
        } else {
            tilItem.setError(null);
        }

        if (TextUtils.isEmpty(qty)) {
            tilQuantity.setError("Quantity required");
            return;
        } else {
            tilQuantity.setError(null);
        }

        boolean isInserted = myDb.insertData(phone, item, qty);

        if (isInserted) {
            Toast.makeText(TakeOrderActivity.this, "Order Saved Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(TakeOrderActivity.this, "Failed to Save Order", Toast.LENGTH_SHORT).show();
        }
    }
}
