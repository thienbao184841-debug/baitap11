package com.videoshare.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order_24110162 implements Serializable {
    private String orderId;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private String note;
    private double totalAmount;
    private String paymentMethod; // COD
    private String status;        // Chờ giao hàng COD, Đã nhận hàng, v.v.
    private Timestamp orderDate;
    private List<OrderItem_24110162> items = new ArrayList<>();

    public Order_24110162() {}

    public Order_24110162(String orderId, String username, String fullname, String email, String phone,
                          String address, String note, double totalAmount, String paymentMethod,
                          String status, Timestamp orderDate) {
        this.orderId = orderId;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderDate = orderDate;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public List<OrderItem_24110162> getItems() { return items; }
    public void setItems(List<OrderItem_24110162> items) { this.items = items; }
}
