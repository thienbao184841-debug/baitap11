package com.videoshare.dao;

import com.videoshare.models.Order_24110162;
import java.util.List;

public interface IOrderDao_24110162 {
    boolean insert(Order_24110162 order);
    Order_24110162 findById(String orderId);
    List<Order_24110162> findByUsername(String username);
    List<Order_24110162> findAll();
}
