package com.videoshare.dao.impl;

import com.videoshare.configs.DBConnect_24110162;
import com.videoshare.dao.IShareDao_24110162;
import com.videoshare.models.Share_24110162;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShareDaoImpl_24110162 implements IShareDao_24110162 {

    @Override
    public void insert(Share_24110162 share) {
        String sql = "INSERT INTO Shares(Emails, SharedDate, Username, VideoId) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, share.getEmails());
            ps.setDate(2, share.getSharedDate());
            ps.setString(3, share.getUsername());
            ps.setString(4, share.getVideoId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Share_24110162 share) {
        String sql = "UPDATE Shares SET Emails=?, SharedDate=?, Username=?, VideoId=? WHERE ShareId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, share.getEmails());
            ps.setDate(2, share.getSharedDate());
            ps.setString(3, share.getUsername());
            ps.setString(4, share.getVideoId());
            ps.setInt(5, share.getShareId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Shares WHERE ShareId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Share_24110162 findById(Integer id) {
        String sql = "SELECT * FROM Shares WHERE ShareId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapShare(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Share_24110162> findAll() {
        List<Share_24110162> list = new ArrayList<>();
        String sql = "SELECT * FROM Shares";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapShare(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countSharesByVideoId(String videoId) {
        String sql = "SELECT COUNT(*) FROM Shares WHERE VideoId=?";
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

    private Share_24110162 mapShare(ResultSet rs) throws Exception {
        Share_24110162 share = new Share_24110162();
        share.setShareId(rs.getInt("ShareId"));
        share.setEmails(rs.getString("Emails"));
        share.setSharedDate(rs.getDate("SharedDate"));
        share.setUsername(rs.getString("Username"));
        share.setVideoId(rs.getString("VideoId"));
        return share;
    }
}
