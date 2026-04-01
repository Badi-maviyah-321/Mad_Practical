package com.example.p15;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TakeOrderActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etPhone, etItemName, etQuantity;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);

        myDb = new DatabaseHelper(this);

        etPhone = findViewById(R.id.etPhone);
        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String item = etItemName.getText().toString();
                String quantity = etQuantity.getText().toString();

                if (phone.isEmpty() || item.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(TakeOrderActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = myDb.insertData(phone, item, quantity);
                if (isInserted) {
                    Toast.makeText(TakeOrderActivity.this, "Order Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TakeOrderActivity.this, "Error Saving Order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}