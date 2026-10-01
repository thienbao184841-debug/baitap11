package com.videoshare.dao.impl;

import com.videoshare.configs.DBConnect_24110162;
import com.videoshare.dao.IOrderDao_24110162;
import com.videoshare.models.OrderItem_24110162;
import com.videoshare.models.Order_24110162;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderDaoImpl_24110162 implements IOrderDao_24110162 {
    private static final List<Order_24110162> memoryOrders = new CopyOnWriteArrayList<>();
    private static boolean tableChecked = false;

    private synchronized void ensureTableExists() {
        if (tableChecked) return;
        try (Connection conn = new DBConnect_24110162().getConnection();
             Statement stmt = conn.createStatement()) {
            
            String sqlOrder = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Orders_24110162' AND xtype='U') "
                    + "CREATE TABLE Orders_24110162 ("
                    + "OrderId VARCHAR(50) PRIMARY KEY, "
                    + "Username NVARCHAR(50), "
                    + "Fullname NVARCHAR(100), "
                    + "Email NVARCHAR(100), "
                    + "Phone VARCHAR(20), "
                    + "Address NVARCHAR(255), "
                    + "Note NVARCHAR(500), "
                    + "TotalAmount FLOAT, "
                    + "PaymentMethod NVARCHAR(50), "
                    + "Status NVARCHAR(50), "
                    + "OrderDate DATETIME DEFAULT GETDATE()"
                    + ")";
            stmt.executeUpdate(sqlOrder);

            String sqlDetails = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='OrderDetails_24110162' AND xtype='U') "
                    + "CREATE TABLE OrderDetails_24110162 ("
                    + "Id INT IDENTITY(1,1) PRIMARY KEY, "
                    + "OrderId VARCHAR(50), "
                    + "VideoId VARCHAR(50), "
                    + "VideoTitle NVARCHAR(200), "
                    + "Price FLOAT, "
                    + "Quantity INT, "
                    + "TotalPrice FLOAT"
                    + ")";
            stmt.executeUpdate(sqlDetails);
            tableChecked = true;
        } catch (Exception e) {
            tableChecked = true;
        }
    }

    private Order_24110162 mapOrder(ResultSet rs) throws Exception {
        return new Order_24110162(
                rs.getString("OrderId"),
                rs.getString("Username"),
                rs.getString("Fullname"),
                rs.getString("Email"),
                rs.getString("Phone"),
                rs.getString("Address"),
                rs.getString("Note"),
                rs.getDouble("TotalAmount"),
                rs.getString("PaymentMethod"),
                rs.getString("Status"),
                rs.getTimestamp("OrderDate")
        );
    }

    private void loadOrderItems(Connection conn, Order_24110162 order) {
        String itemSql = "SELECT * FROM OrderDetails_24110162 WHERE OrderId = ?";
        try (PreparedStatement ips = conn.prepareStatement(itemSql)) {
            ips.setString(1, order.getOrderId());
            try (ResultSet irs = ips.executeQuery()) {
                List<OrderItem_24110162> items = new ArrayList<>();
                while (irs.next()) {
                    OrderItem_24110162 item = new OrderItem_24110162(
                            irs.getString("OrderId"),
                            irs.getString("VideoId"),
                            irs.getString("VideoTitle"),
                            irs.getDouble("Price"),
                            irs.getInt("Quantity")
                    );
                    item.setId(irs.getInt("Id"));
                    item.setTotalPrice(irs.getDouble("TotalPrice"));
                    items.add(item);
                }
                order.setItems(items);
            }
        } catch (Exception e) {
            // Keep existing items if fail
        }
    }

    @Override
    public boolean insert(Order_24110162 order) {
        memoryOrders.add(0, order);

        ensureTableExists();
        String sql = "INSERT INTO Orders_24110162(OrderId, Username, Fullname, Email, Phone, Address, Note, TotalAmount, PaymentMethod, Status, OrderDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getOrderId());
            ps.setNString(2, order.getUsername() != null ? order.getUsername() : "Khách vãng lai");
            ps.setNString(3, order.getFullname() != null ? order.getFullname() : "");
            ps.setNString(4, order.getEmail() != null ? order.getEmail() : "");
            ps.setString(5, order.getPhone() != null ? order.getPhone() : "");
            ps.setNString(6, order.getAddress() != null ? order.getAddress() : "");
            ps.setNString(7, order.getNote() != null ? order.getNote() : "");
            ps.setDouble(8, order.getTotalAmount());
            ps.setNString(9, order.getPaymentMethod() != null ? order.getPaymentMethod() : "COD");
            ps.setNString(10, order.getStatus() != null ? order.getStatus() : "Đơn hàng mới");
            ps.setTimestamp(11, order.getOrderDate() != null ? order.getOrderDate() : new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();

            String itemSql = "INSERT INTO OrderDetails_24110162(OrderId, VideoId, VideoTitle, Price, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ips = conn.prepareStatement(itemSql)) {
                for (OrderItem_24110162 item : order.getItems()) {
                    ips.setString(1, order.getOrderId());
                    ips.setString(2, item.getVideoId() != null ? item.getVideoId() : "");
                    ips.setNString(3, item.getVideoTitle() != null ? item.getVideoTitle() : "");
                    ips.setDouble(4, item.getPrice());
                    ips.setInt(5, item.getQuantity());
                    ips.setDouble(6, item.getTotalPrice());
                    ips.addBatch();
                }
                ips.executeBatch();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public Order_24110162 findById(String orderId) {
        ensureTableExists();
        String sql = "SELECT * FROM Orders_24110162 WHERE OrderId = ?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order_24110162 ord = mapOrder(rs);
                    loadOrderItems(conn, ord);
                    return ord;
                }
            }
        } catch (Exception e) {
            // Fallback
        }

        for (Order_24110162 ord : memoryOrders) {
            if (ord.getOrderId().equals(orderId)) {
                return ord;
            }
        }
        return null;
    }

    @Override
    public List<Order_24110162> findByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.equalsIgnoreCase("Khách vãng lai")) {
            return findAll();
        }
        ensureTableExists();
        String sql = "SELECT * FROM Orders_24110162 WHERE Username = ? ORDER BY OrderDate DESC";
        List<Order_24110162> orders = new ArrayList<>();
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
            for (Order_24110162 ord : orders) {
                loadOrderItems(conn, ord);
            }
            return orders;
        } catch (Exception e) {
            List<Order_24110162> list = new ArrayList<>();
            for (Order_24110162 ord : memoryOrders) {
                if (ord.getUsername() != null && ord.getUsername().equalsIgnoreCase(username)) {
                    list.add(ord);
                }
            }
            return list;
        }
    }

    @Override
    public List<Order_24110162> findByUsernameAndStatus(String username, String status) {
        if (status == null || status.trim().isEmpty() || status.equalsIgnoreCase("ALL") || status.equals("Tất cả")) {
            return findByUsername(username);
        }
        if (username == null || username.trim().isEmpty() || username.equalsIgnoreCase("Khách vãng lai")) {
            return findByStatus(status);
        }
        ensureTableExists();
        String sql = "SELECT * FROM Orders_24110162 WHERE Username = ? AND LTRIM(RTRIM(Status)) = ? ORDER BY OrderDate DESC";
        List<Order_24110162> orders = new ArrayList<>();
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, username);
            ps.setNString(2, status.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
            for (Order_24110162 ord : orders) {
                loadOrderItems(conn, ord);
            }
            return orders;
        } catch (Exception e) {
            List<Order_24110162> list = new ArrayList<>();
            for (Order_24110162 ord : memoryOrders) {
                if (ord.getUsername() != null && ord.getUsername().equalsIgnoreCase(username)
                        && ord.getStatus() != null && ord.getStatus().trim().equalsIgnoreCase(status.trim())) {
                    list.add(ord);
                }
            }
            return list;
        }
    }

    @Override
    public List<Order_24110162> findAll() {
        ensureTableExists();
        String sql = "SELECT * FROM Orders_24110162 ORDER BY OrderDate DESC";
        List<Order_24110162> orders = new ArrayList<>();
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
            for (Order_24110162 ord : orders) {
                loadOrderItems(conn, ord);
            }
            return orders;
        } catch (Exception e) {
            return new ArrayList<>(memoryOrders);
        }
    }

    @Override
    public List<Order_24110162> findByStatus(String status) {
        if (status == null || status.trim().isEmpty() || status.equalsIgnoreCase("ALL") || status.equals("Tất cả")) {
            return findAll();
        }
        ensureTableExists();
        String sql = "SELECT * FROM Orders_24110162 WHERE LTRIM(RTRIM(Status)) = ? ORDER BY OrderDate DESC";
        List<Order_24110162> orders = new ArrayList<>();
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, status.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
            for (Order_24110162 ord : orders) {
                loadOrderItems(conn, ord);
            }
            return orders;
        } catch (Exception e) {
            List<Order_24110162> list = new ArrayList<>();
            for (Order_24110162 ord : memoryOrders) {
                if (ord.getStatus() != null && ord.getStatus().trim().equalsIgnoreCase(status.trim())) {
                    list.add(ord);
                }
            }
            return list;
        }
    }

    @Override
    public boolean updateStatus(String orderId, String newStatus) {
        ensureTableExists();
        String sql = "UPDATE Orders_24110162 SET Status = ? WHERE OrderId = ?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, newStatus);
            ps.setString(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Order_24110162 ord : memoryOrders) {
            if (ord.getOrderId().equals(orderId)) {
                ord.setStatus(newStatus);
            }
        }
        return true;
    }
}
