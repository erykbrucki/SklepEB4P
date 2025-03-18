package com.example.sklep.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sklep.Order;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "orders.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRODUCT_ID = "product_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_QUANTITY + " INTEGER, "
                + COLUMN_PRODUCT_ID + " INTEGER)";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, order.getName());
        values.put(COLUMN_PRICE, order.getTotalPrice());
        values.put(COLUMN_DATE, order.getDate());
        values.put(COLUMN_QUANTITY, order.getQuantity());
        values.put(COLUMN_PRODUCT_ID, order.getProductId());

        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }

    public Cursor getOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }
}
