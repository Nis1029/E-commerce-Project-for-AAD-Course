package com.eticaret.backend.dto.response;

import com.eticaret.backend.model.Order;
import com.eticaret.backend.model.OrderStatus;
import com.eticaret.backend.model.KargoDurumu;

public class OrderAdminResponse {

    private Long id;
    private double totalPrice;
    private String status;
    private String kargoDurumu;

    public OrderAdminResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus() != null ? order.getStatus().name() : null;
        this.kargoDurumu = order.getKargoDurumu() != null ? order.getKargoDurumu().name() : null;
    }

    public Long getId() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getKargoDurumu() {
        return kargoDurumu;
    }
}