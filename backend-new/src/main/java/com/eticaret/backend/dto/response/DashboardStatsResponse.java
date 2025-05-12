package com.eticaret.backend.dto.response;

public class DashboardStatsResponse {
    private int totalOrders;
    private int totalUsers;
    private int totalProducts;
    private double totalRevenue;

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    // Constructor
    public DashboardStatsResponse(int totalOrders, int totalUsers, int totalProducts, double totalRevenue) {
        this.totalOrders = totalOrders;
        this.totalUsers = totalUsers;
        this.totalProducts = totalProducts;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
}
