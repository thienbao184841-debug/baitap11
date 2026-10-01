package com.videoshare.services.impl;

import com.videoshare.dao.IOrderDao_24110162;
import com.videoshare.dao.impl.OrderDaoImpl_24110162;
import com.videoshare.models.CartItem_24110162;
import com.videoshare.models.OrderItem_24110162;
import com.videoshare.models.Order_24110162;
import com.videoshare.services.IOrderService_24110162;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderServiceImpl_24110162 implements IOrderService_24110162 {
    private IOrderDao_24110162 orderDao = new OrderDaoImpl_24110162();

    @Override
    public Order_24110162 processCODOrder(String username, String fullname, String email, String phone,
                                         String address, String note, List<CartItem_24110162> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return null;
        }

        // Tạo mã đơn hàng độc nhất: COD-yyyyMMdd-XXXX
        String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomSuffix = 1000 + new Random().nextInt(9000);
        String orderId = "COD-" + datePrefix + "-" + randomSuffix;

        double totalAmount = 0;
        List<OrderItem_24110162> orderItems = new ArrayList<>();
        for (CartItem_24110162 cItem : cartItems) {
            double price = cItem.getPrice();
            int qty = cItem.getQuantity();
            String title = (cItem.getVideo() != null) ? cItem.getVideo().getTitle() : "Video";
            String videoId = (cItem.getVideo() != null) ? cItem.getVideo().getVideoId() : "";

            OrderItem_24110162 oItem = new OrderItem_24110162(orderId, videoId, title, price, qty);
            orderItems.add(oItem);
            totalAmount += oItem.getTotalPrice();
        }

        Order_24110162 order = new Order_24110162(
                orderId,
                username != null && !username.trim().isEmpty() ? username : "Khách vãng lai",
                fullname,
                email,
                phone,
                address,
                note,
                totalAmount,
                "COD (Thanh toán khi nhận hàng)",
                "Chờ xử lý & Giao hàng COD",
                new Timestamp(System.currentTimeMillis())
        );
        order.setItems(orderItems);

        orderDao.insert(order);
        return order;
    }

    @Override
    public Order_24110162 getOrderById(String orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public List<Order_24110162> getOrdersByUsername(String username) {
        return orderDao.findByUsername(username);
    }
}
