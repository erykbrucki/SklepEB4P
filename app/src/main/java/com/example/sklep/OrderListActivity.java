package com.example.sklep;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sklep.Database.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private ListView listViewOrders;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        listViewOrders = findViewById(R.id.listViewOrders);
        dbHelper = new DatabaseHelper(this);

        loadOrders();
    }

    private void loadOrders() {
        List<String> orderList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getOrders();

        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            String date = cursor.getString(3);
            int quantity = cursor.getInt(4);
            int productId = cursor.getInt(5);

            orderList.add(name + " | Cena: " + price + " zł | " + date);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderList);
        listViewOrders.setAdapter(adapter);
    }
}
