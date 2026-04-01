package com.example.p15;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for RecyclerView to display the list of expenses.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        // Get the expense object for the current position
        Expense expense = expenseList.get(position);

        // Bind data to the views
        holder.tvCategory.setText(expense.getCategory());
        holder.tvDescription.setText(expense.getDescription());
        holder.tvDate.setText(expense.getDate());
        holder.tvAmount.setText(String.format("$%.2f", expense.getAmount()));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // ViewHolder class to hold references to the views for each item
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvDescription, tvDate, tvAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvItemCategory);
            tvDescription = itemView.findViewById(R.id.tvItemDescription);
            tvDate = itemView.findViewById(R.id.tvItemDate);
            tvAmount = itemView.findViewById(R.id.tvItemAmount);
        }
    }
}
