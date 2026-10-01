package com.videoshare.services;

import com.videoshare.models.CartItem_24110162;
import com.videoshare.models.Order_24110162;
import java.util.List;

public interface IOrderService_24110162 {
    Order_24110162 processCODOrder(String username, String fullname, String email, String phone,
                                   String address, String note, List<CartItem_24110162> cartItems);
    Order_24110162 getOrderById(String orderId);
    List<Order_24110162> getOrdersByUsername(String username);
}
