package com.example.p15;

/**
 * Model class for an Expense.
 * This class represents a single expense entry with ID, amount, category, description, and date.
 */
public class Expense {
    private int id;
    private double amount;
    private String category;
    private String description;
    private String date; // Stored as "YYYY-MM-DD"

    // Constructor for creating a new expense (without ID)
    public Expense(double amount, String category, String description, String date) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    // Constructor for retrieving an expense from database (with ID)
    public Expense(int id, double amount, String category, String description, String date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
