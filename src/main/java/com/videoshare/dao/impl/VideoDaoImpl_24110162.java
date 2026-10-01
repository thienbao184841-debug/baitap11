package com.videoshare.dao.impl;

import com.videoshare.configs.DBConnect_24110162;
import com.videoshare.dao.IVideoDao_24110162;
import com.videoshare.models.Video_24110162;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VideoDaoImpl_24110162 implements IVideoDao_24110162 {

    @Override
    public void insert(Video_24110162 video) {
        String sql = "INSERT INTO Videos(VideoId, Title, Poster, Views, Description, Active, CategoryId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, video.getVideoId());
            ps.setString(2, video.getTitle());
            ps.setString(3, video.getPoster());
            ps.setInt(4, video.getViews());
            ps.setString(5, video.getDescription());
            ps.setBoolean(6, video.isActive());
            ps.setInt(7, video.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Video_24110162 video) {
        String sql = "UPDATE Videos SET Title=?, Poster=?, Views=?, Description=?, Active=?, CategoryId=? WHERE VideoId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, video.getTitle());
            ps.setString(2, video.getPoster());
            ps.setInt(3, video.getViews());
            ps.setString(4, video.getDescription());
            ps.setBoolean(5, video.isActive());
            ps.setInt(6, video.getCategoryId());
            ps.setString(7, video.getVideoId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Videos WHERE VideoId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Video_24110162 findById(String id) {
        String sql = "SELECT * FROM Videos WHERE VideoId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapVideo(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Video_24110162> findAll() {
        List<Video_24110162> list = new ArrayList<>();
        String sql = "SELECT * FROM Videos";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapVideo(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Video_24110162> findVideosByCategoryId(int categoryId, int offset, int limit) {
        List<Video_24110162> list = new ArrayList<>();
        String sql = "SELECT v.*, c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                     "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.CategoryId=? ORDER BY v.VideoId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Video_24110162 video = mapVideo(rs);
                    video.setCategoryName(rs.getString("Categoryname"));
                    video.setShareCount(rs.getInt("ShareCount"));
                    video.setLikeCount(rs.getInt("LikeCount"));
                    list.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countVideosByCategoryId(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Videos WHERE CategoryId=?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
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

    private Video_24110162 mapVideo(ResultSet rs) throws Exception {
        Video_24110162 video = new Video_24110162();
        video.setVideoId(rs.getString("VideoId"));
        video.setTitle(rs.getString("Title"));
        video.setPoster(rs.getString("Poster"));
        video.setViews(rs.getInt("Views"));
        video.setDescription(rs.getString("Description"));
        video.setActive(rs.getBoolean("Active"));
        video.setCategoryId(rs.getInt("CategoryId"));
        return video;
    }

    @Override
    public Video_24110162 getVideoDetail(String videoId) {
        String sql = "SELECT v.*, c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                     "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.VideoId = ?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Video_24110162 video = mapVideo(rs);
                    video.setCategoryName(rs.getString("Categoryname"));
                    video.setShareCount(rs.getInt("ShareCount"));
                    video.setLikeCount(rs.getInt("LikeCount"));
                    return video;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
