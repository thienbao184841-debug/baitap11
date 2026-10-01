package com.videoshare.models;

import java.io.Serializable;

public class CartItem_24110162 implements Serializable {
    private Video_24110162 video;
    private int quantity;
    private double price = 50000.0; // Đơn giá: 50.000 VNĐ

    public CartItem_24110162() {}

    public CartItem_24110162(Video_24110162 video, int quantity) {
        this.video = video;
        this.quantity = quantity;
        this.price = 50000.0;
    }

    public CartItem_24110162(Video_24110162 video, int quantity, double price) {
        this.video = video;
        this.quantity = quantity;
        this.price = price;
    }

    public Video_24110162 getVideo() { return video; }
    public void setVideo(Video_24110162 video) { this.video = video; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getTotalPrice() { return price * quantity; }
}
