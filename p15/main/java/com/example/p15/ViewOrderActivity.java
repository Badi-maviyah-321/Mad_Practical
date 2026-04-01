package com.example.p15;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewOrderActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    ListView listView;
    ArrayList<String> orderList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        myDb = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        orderList = new ArrayList<>();

        viewAll();
    }

    public void viewAll() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No Orders Found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (res.moveToNext()) {
            String orderInfo = "Phone: " + res.getString(1) + "\nItem: " + res.getString(2) + "\nQty: " + res.getString(3);
            orderList.add(orderInfo);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderList);
        listView.setAdapter(adapter);
    }
}