package com.example.p15;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Activity to display the history of all expenses with filtering options and a summary total.
 */
public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private ExpenseAdapter adapter;
    private DatabaseHelper dbHelper;
    private TabLayout tabLayout;
    private TextView tvHistoryTotalAmount, tvHistoryTotalLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DatabaseHelper(this);
        rvHistory = findViewById(R.id.rvHistory);
        tabLayout = findViewById(R.id.tabLayout);
        tvHistoryTotalAmount = findViewById(R.id.tvHistoryTotalAmount);
        tvHistoryTotalLabel = findViewById(R.id.tvHistoryTotalLabel);

        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        // Initial load: All expenses
        loadAllExpenses();

        // Handle tab selection for filtering
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // All
                        tvHistoryTotalLabel.setText("Total Expenses:");
                        loadAllExpenses();
                        break;
                    case 1: // Week
                        tvHistoryTotalLabel.setText("Weekly Total:");
                        loadWeeklyExpenses();
                        break;
                    case 2: // Month
                        tvHistoryTotalLabel.setText("Monthly Total:");
                        loadMonthlyExpenses();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadAllExpenses() {
        List<Expense> expenseList = dbHelper.getAllExpenses();
        updateUI(expenseList);
    }

    private void loadWeeklyExpenses() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String startDate = sdf.format(cal.getTime());
        String endDate = sdf.format(Calendar.getInstance().getTime());

        List<Expense> expenseList = dbHelper.getWeeklyExpenses(startDate, endDate);
        updateUI(expenseList);
    }

    private void loadMonthlyExpenses() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.US);
        String currentMonth = sdf.format(Calendar.getInstance().getTime());

        List<Expense> expenseList = dbHelper.getMonthlyExpenses(currentMonth);
        updateUI(expenseList);
    }

    private void updateUI(List<Expense> expenses) {
        // Update List
        adapter = new ExpenseAdapter(expenses);
        rvHistory.setAdapter(adapter);

        // Calculate and Update Total
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        tvHistoryTotalAmount.setText(String.format(Locale.US, "$%.2f", total));
    }
}
