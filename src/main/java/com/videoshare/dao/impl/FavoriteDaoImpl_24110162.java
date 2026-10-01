package com.videoshare.dao.impl;

import com.videoshare.configs.DBConnect_24110162;
import com.videoshare.dao.IFavoriteDao_24110162;
import com.videoshare.models.Favorite_24110162;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDaoImpl_24110162 implements IFavoriteDao_24110162 {

    @Override
    public void insert(Favorite_24110162 favorite) {
        String sql = "INSERT INTO Favorites(LikedDate, VideoId, Username) VALUES (?, ?, ?)";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, favorite.getLikedDate());
            ps.setString(2, favorite.getVideoId());
            ps.setString(3, favorite.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Favorite_24110162 favorite) {
        String sql = "UPDATE Favorites SET LikedDate=?, VideoId=?, Username=? WHERE FavoriteId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, favorite.getLikedDate());
            ps.setString(2, favorite.getVideoId());
            ps.setString(3, favorite.getUsername());
            ps.setInt(4, favorite.getFavoriteId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Favorites WHERE FavoriteId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Favorite_24110162 findById(Integer id) {
        String sql = "SELECT * FROM Favorites WHERE FavoriteId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapFavorite(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Favorite_24110162> findAll() {
        List<Favorite_24110162> list = new ArrayList<>();
        String sql = "SELECT * FROM Favorites";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapFavorite(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Favorite_24110162 findByUsernameAndVideoId(String username, String videoId) {
        String sql = "SELECT * FROM Favorites WHERE Username=? AND VideoId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, videoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapFavorite(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countFavoritesByVideoId(String videoId) {
        String sql = "SELECT COUNT(*) FROM Favorites WHERE VideoId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Favorite_24110162 mapFavorite(ResultSet rs) throws Exception {
        Favorite_24110162 favorite = new Favorite_24110162();
        favorite.setFavoriteId(rs.getInt("FavoriteId"));
        favorite.setLikedDate(rs.getDate("LikedDate"));
        favorite.setVideoId(rs.getString("VideoId"));
        favorite.setUsername(rs.getString("Username"));
        return favorite;
    }
}
