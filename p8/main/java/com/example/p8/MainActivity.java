package com.example.p8;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    // Sample data for mobile models (Samsung S24 removed)
    String[] modelNames = {"iPhone 15", "Google Pixel 8", "OnePlus 12", "Xiaomi 14", "Realme GT"};
    
    // Sample online image URLs (Samsung S24 URL removed)
    String[] imageUrls = {
            "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-15.jpg",
            "https://fdn2.gsmarena.com/vv/bigpic/google-pixel-8.jpg",
            "https://fdn2.gsmarena.com/vv/bigpic/oneplus-12.jpg",
            "https://fdn2.gsmarena.com/vv/bigpic/xiaomi-14.jpg",
            "https://fdn2.gsmarena.com/vv/bigpic/realme-gt5.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        CustomAdapter customAdapter = new CustomAdapter(this, modelNames, imageUrls);
        gridView.setAdapter(customAdapter);
    }

    private class CustomAdapter extends BaseAdapter {
        Context context;
        String[] names;
        String[] urls;
        LayoutInflater inflater;

        public CustomAdapter(Context context, String[] names, String[] urls) {
            this.context = context;
            this.names = names;
            this.urls = urls;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);

            textView.setText(names[position]);

            // Using Glide with centerCrop to ensure images fit perfectly and look uniform
            Glide.with(context)
                    .load(urls[position])
                    .apply(new RequestOptions().centerCrop())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);

            return convertView;
        }
    }
}
