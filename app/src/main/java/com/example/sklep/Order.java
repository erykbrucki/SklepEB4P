package com.example.sklep;
public class Order {
    private String name;
    private double totalPrice;
    private String date;
    private int quantity;
    private int productId;

    public Order(String name, double totalPrice, String date, int quantity, int productId) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.date = date;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getName() { return name; }
    public double getTotalPrice() { return totalPrice; }
    public String getDate() { return date; }
    public int getQuantity() { return quantity; }
    public int getProductId() { return productId; }
}


