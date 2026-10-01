package com.videoshare.controllers.web;

import com.videoshare.models.Video_24110162;
import com.videoshare.services.IVideoService_24110162;
import com.videoshare.services.impl.VideoServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/video"})
public class VideoDetailController_24110162 extends HttpServlet {
    private IVideoService_24110162 videoService = new VideoServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("id");
        if (videoId != null && !videoId.isEmpty()) {
            Video_24110162 video = videoService.getVideoDetail(videoId);
            if (video != null) {
                req.setAttribute("video", video);
                req.getRequestDispatcher("/views/web/video-detail.jsp").forward(req, resp);
                return;
            }
        }
        // Nếu không có ID hoặc video không tồn tại thì quay về trang chủ
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
