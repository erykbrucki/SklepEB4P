package com.example.sklep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Order> orders;
    private LayoutInflater inflater;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_order, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);
        TextView textViewQuantity = convertView.findViewById(R.id.textViewQuantity);

        Order order = orders.get(position);

        textViewName.setText(order.getName());
        textViewPrice.setText("Cena: " + order.getTotalPrice() + " zł");
        textViewQuantity.setText("Ilość: " + order.getQuantity());

        return convertView;
    }
}
