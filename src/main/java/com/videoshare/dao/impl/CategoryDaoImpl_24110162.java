package com.videoshare.dao.impl;

import com.videoshare.configs.DBConnect_24110162;
import com.videoshare.dao.ICategoryDao_24110162;
import com.videoshare.models.Category_24110162;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl_24110162 implements ICategoryDao_24110162 {

    @Override
    public void insert(Category_24110162 category) {
        String sql = "INSERT INTO Category(Categoryname, Categorycode, Images, Status) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryname());
            ps.setString(2, category.getCategorycode());
            ps.setString(3, category.getImages());
            ps.setBoolean(4, category.isStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category_24110162 category) {
        String sql = "UPDATE Category SET Categoryname=?, Categorycode=?, Images=?, Status=? WHERE CategoryId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryname());
            ps.setString(2, category.getCategorycode());
            ps.setString(3, category.getImages());
            ps.setBoolean(4, category.isStatus());
            ps.setInt(5, category.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Category WHERE CategoryId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category_24110162 findById(Integer id) {
        String sql = "SELECT * FROM Category WHERE CategoryId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCategory(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category_24110162> findAll() {
        List<Category_24110162> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Category_24110162 mapCategory(ResultSet rs) throws Exception {
        Category_24110162 category = new Category_24110162();
        category.setCategoryId(rs.getInt("CategoryId"));
        category.setCategoryname(rs.getString("Categoryname"));
        category.setCategorycode(rs.getString("Categorycode"));
        category.setImages(rs.getString("Images"));
        category.setStatus(rs.getBoolean("Status"));
        return category;
    }
}
