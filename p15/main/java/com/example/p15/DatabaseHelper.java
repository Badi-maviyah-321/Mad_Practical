package com.example.p15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ExpenseTracker.db";
    public static final int DATABASE_VERSION = 5;

    // Expense Table constants
    public static final String TABLE_EXPENSES = "expenses_table";
    public static final String COL_ID = "ID";
    public static final String COL_AMOUNT = "AMOUNT";
    public static final String COL_CATEGORY = "CATEGORY";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_DATE = "DATE";

    // Notes Table constants
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";

    // Orders Table constants
    public static final String TABLE_ORDERS = "orders_table";
    public static final String ORDER_COL_ID = "ID";
    public static final String ORDER_COL_PHONE = "PHONE";
    public static final String ORDER_COL_ITEM = "ITEM";
    public static final String ORDER_COL_QUANTITY = "QUANTITY";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EXPENSES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_AMOUNT + " REAL, " +
                COL_CATEGORY + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_DATE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                ORDER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ORDER_COL_PHONE + " TEXT, " +
                ORDER_COL_ITEM + " TEXT, " +
                ORDER_COL_QUANTITY + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_CONTENT + " TEXT)");
        }
        if (oldVersion < 5) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS + " (" +
                    ORDER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDER_COL_PHONE + " TEXT, " +
                    ORDER_COL_ITEM + " TEXT, " +
                    ORDER_COL_QUANTITY + " TEXT)");
        }
    }

    public boolean insertExpense(double amount, String category, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AMOUNT, amount);
        contentValues.put(COL_CATEGORY, category);
        contentValues.put(COL_DESCRIPTION, description);
        contentValues.put(COL_DATE, date);
        long result = db.insert(TABLE_EXPENSES, null, contentValues);
        return result != -1;
    }

    public List<Expense> getAllExpenses() {
        return getExpensesByQuery("SELECT * FROM " + TABLE_EXPENSES + " ORDER BY " + COL_DATE + " DESC", null);
    }

    public List<Expense> getWeeklyExpenses(String startDate, String endDate) {
        return getExpensesByQuery("SELECT * FROM " + TABLE_EXPENSES + " WHERE " + COL_DATE + " BETWEEN ? AND ? ORDER BY " + COL_DATE + " DESC", new String[]{startDate, endDate});
    }

    public List<Expense> getMonthlyExpenses(String monthYear) {
        return getExpensesByQuery("SELECT * FROM " + TABLE_EXPENSES + " WHERE " + COL_DATE + " LIKE ? ORDER BY " + COL_DATE + " DESC", new String[]{monthYear + "%"});
    }

    private List<Expense> getExpensesByQuery(String query, String[] args) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                expenses.add(new Expense(
                        cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return expenses;
    }

    public double getTotalSpending() {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_AMOUNT + ") FROM " + TABLE_EXPENSES, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public double getMonthlySpending(String monthYear) {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_AMOUNT + ") FROM " + TABLE_EXPENSES + 
                " WHERE " + COL_DATE + " LIKE ?", new String[]{monthYear + "%"});
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public double getWeeklySpending(String startDate, String endDate) {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_AMOUNT + ") FROM " + TABLE_EXPENSES + 
                " WHERE " + COL_DATE + " BETWEEN ? AND ?", new String[]{startDate, endDate});
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public boolean insertData(String phone, String item, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_COL_PHONE, phone);
        contentValues.put(ORDER_COL_ITEM, item);
        contentValues.put(ORDER_COL_QUANTITY, quantity);
        long result = db.insert(TABLE_ORDERS, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }
}
