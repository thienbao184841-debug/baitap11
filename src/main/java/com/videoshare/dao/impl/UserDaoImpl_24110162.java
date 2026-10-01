package com.videoshare.dao.impl;

import com.videoshare.configs.DBConnect_24110162;
import com.videoshare.dao.IUserDao_24110162;
import com.videoshare.models.User_24110162;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl_24110162 implements IUserDao_24110162 {

    @Override
    public void insert(User_24110162 user) {
        String sql = "INSERT INTO Users(Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isAdmin());
            ps.setBoolean(7, user.isActive());
            ps.setString(8, user.getImages());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User_24110162 user) {
        String sql = "UPDATE Users SET Password=?, Phone=?, Fullname=?, Email=?, Admin=?, Active=?, Images=? WHERE Username=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.isAdmin());
            ps.setBoolean(6, user.isActive());
            ps.setString(7, user.getImages());
            ps.setString(8, user.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String username) {
        String sql = "DELETE FROM Users WHERE Username=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User_24110162 findById(String username) {
        String sql = "SELECT * FROM Users WHERE Username=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User_24110162> findAll() {
        List<User_24110162> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User_24110162 findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username=? AND Password=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkExistEmail(String email) {
        String sql = "SELECT 1 FROM Users WHERE Email=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có dữ liệu trả về true
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User_24110162> findUsersByPage(int offset, int limit) {
        List<User_24110162> list = new ArrayList<>();
        // Query phân trang cho SQL Server >= 2012
        String sql = "SELECT * FROM Users ORDER BY Username OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapUser(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Hàm phụ trợ map ResultSet sang đối tượng User_24110162
    private User_24110162 mapUser(ResultSet rs) throws Exception {
        User_24110162 user = new User_24110162();
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setPhone(rs.getString("Phone"));
        user.setFullname(rs.getString("Fullname"));
        user.setEmail(rs.getString("Email"));
        user.setAdmin(rs.getBoolean("Admin"));
        user.setActive(rs.getBoolean("Active"));
        user.setImages(rs.getString("Images"));
        return user;
    }
}
