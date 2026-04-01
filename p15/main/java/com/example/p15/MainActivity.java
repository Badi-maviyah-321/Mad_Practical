package com.example.p15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Main Activity for the Expense Tracker app.
 * Displays total spending, monthly summary, and provides navigation to other features.
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvTotalSpending, tvMonthlySpending, tvWeeklySpending;
    private Button btnAddExpense, btnViewHistory;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        tvTotalSpending = findViewById(R.id.tvTotalSpending);
        tvWeeklySpending = findViewById(R.id.tvWeeklySpending);
        tvMonthlySpending = findViewById(R.id.tvMonthlySpending);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewHistory = findViewById(R.id.btnViewHistory);

        // Set up button listeners
        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the spending amounts every time we return to this screen
        updateSpendingSummary();
    }

    private void updateSpendingSummary() {
        // Calculate Total Spending
        double total = dbHelper.getTotalSpending();
        tvTotalSpending.setText(String.format(Locale.US, "$%.2f", total));

        // Calculate Weekly Spending
        Calendar cal = Calendar.getInstance();
        // Set to Monday of current week
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String startDate = sdf.format(cal.getTime());
        
        // Set to today
        String endDate = sdf.format(Calendar.getInstance().getTime());
        
        double weeklyTotal = dbHelper.getWeeklySpending(startDate, endDate);
        tvWeeklySpending.setText(String.format(Locale.US, "$%.2f", weeklyTotal));

        // Calculate Monthly Spending
        // Get current month in YYYY-MM format
        SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM", Locale.US);
        String currentMonth = monthSdf.format(Calendar.getInstance().getTime());
        
        double monthlyTotal = dbHelper.getMonthlySpending(currentMonth);
        tvMonthlySpending.setText(String.format(Locale.US, "$%.2f", monthlyTotal));
    }
}
