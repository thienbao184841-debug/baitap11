package com.videoshare.models;

import java.io.Serializable;

public class OrderItem_24110162 implements Serializable {
    private int id;
    private String orderId;
    private String videoId;
    private String videoTitle;
    private double price;
    private int quantity;
    private double totalPrice;

    public OrderItem_24110162() {}

    public OrderItem_24110162(String orderId, String videoId, String videoTitle, double price, int quantity) {
        this.orderId = orderId;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }

    public String getVideoTitle() { return videoTitle; }
    public void setVideoTitle(String videoTitle) { this.videoTitle = videoTitle; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
