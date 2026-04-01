package com.example.p15;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

/**
 * Activity to add a new expense.
 */
public class AddExpenseActivity extends AppCompatActivity {

    private TextInputEditText etAmount, etDescription, etDate;
    private AutoCompleteTextView spinnerCategory;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    // Predefined categories for the dropdown
    private String[] categories = {"Food", "Travel", "Shopping", "Entertainment", "Health", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new DatabaseHelper(this);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSaveExpense);

        // Set up the category dropdown menu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        spinnerCategory.setAdapter(adapter);

        // Set up the date picker dialog
        etDate.setOnClickListener(v -> showDatePicker());

        // Set up the save button
        btnSave.setOnClickListener(v -> saveExpense());
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Format: YYYY-MM-DD for sorting convenience
                    String formattedDate = String.format("%d-%02d-%02d", year1, (monthOfYear + 1), dayOfMonth);
                    etDate.setText(formattedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveExpense() {
        String amountStr = etAmount.getText().toString().trim();
        String category = spinnerCategory.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        // Basic validation
        if (amountStr.isEmpty() || category.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        // Insert into database
        boolean isInserted = dbHelper.insertExpense(amount, category, description, date);

        if (isInserted) {
            Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to the previous screen
        } else {
            Toast.makeText(this, "Error saving expense", Toast.LENGTH_SHORT).show();
        }
    }
}
