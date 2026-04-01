package com.example.p14;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewOrderActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    ListView listView;
    ArrayList<Order> orderList;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        myDb = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        orderList = new ArrayList<>();

        viewData();
    }

    private void viewData() {
        Cursor cursor = myDb.getAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                orderList.add(new Order(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }
            adapter = new OrderAdapter();
            listView.setAdapter(adapter);
        }
    }

    static class Order {
        String phone, item, quantity;
        Order(String phone, String item, String quantity) {
            this.phone = phone;
            this.item = item;
            this.quantity = quantity;
        }
    }

    class OrderAdapter extends BaseAdapter {
        @Override
        public int getCount() { return orderList.size(); }
        @Override
        public Object getItem(int i) { return orderList.get(i); }
        @Override
        public long getItemId(int i) { return i; }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(ViewOrderActivity.this).inflate(R.layout.list_item_order, viewGroup, false);
            }
            TextView tvItemName = view.findViewById(R.id.tvItemName);
            TextView tvPhone = view.findViewById(R.id.tvPhone);
            TextView tvQuantity = view.findViewById(R.id.tvQuantity);

            Order order = orderList.get(i);
            tvItemName.setText(order.item);
            tvPhone.setText("Phone: " + order.phone);
            tvQuantity.setText("Qty: " + order.quantity);

            return view;
        }
    }
}
