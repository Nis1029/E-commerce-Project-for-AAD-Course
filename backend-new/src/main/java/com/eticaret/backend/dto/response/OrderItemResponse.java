package com.eticaret.backend.dto.response;

public class OrderItemResponse {
    private String name;
    private double price;
    private int quantity;
    private double totalPrice;

    public OrderItemResponse() {
    }

    public OrderItemResponse(String name, double price, int quantity, double totalPrice) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}