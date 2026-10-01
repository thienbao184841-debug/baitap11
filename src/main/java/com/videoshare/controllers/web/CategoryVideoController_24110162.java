package com.videoshare.controllers.web;

import com.videoshare.models.Category_24110162;
import com.videoshare.models.Video_24110162;
import com.videoshare.services.ICategoryService_24110162;
import com.videoshare.services.IVideoService_24110162;
import com.videoshare.services.impl.CategoryServiceImpl_24110162;
import com.videoshare.services.impl.VideoServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/videos"})
public class CategoryVideoController_24110162 extends HttpServlet {
    private ICategoryService_24110162 categoryService = new CategoryServiceImpl_24110162();
    private IVideoService_24110162 videoService = new VideoServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category_24110162> categories = categoryService.findAll();
        
        // Mặc định lấy category đầu tiên nếu không có tham số
        int categoryId = -1;
        if (req.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(req.getParameter("categoryId"));
        } else if (categories != null && !categories.isEmpty()) {
            categoryId = categories.get(0).getCategoryId();
        }

        if (categoryId != -1) {
            int page = 1;
            int limit = 3; // Yêu cầu Câu 5: phân trang 3 video/trang
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            int offset = (page - 1) * limit;

            Category_24110162 currentCategory = categoryService.findById(categoryId);
            List<Video_24110162> videos = videoService.findVideosByCategoryId(categoryId, offset, limit);
            
            // Câu 6: Đếm số lượng video
            int totalVideos = videoService.countVideosByCategoryId(categoryId);
            int totalPage = (int) Math.ceil((double) totalVideos / limit);

            req.setAttribute("currentCategory", currentCategory);
            req.setAttribute("videoList", videos);
            req.setAttribute("totalVideos", totalVideos);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPage", totalPage);
        }
        
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/views/web/videos.jsp").forward(req, resp);
    }
}
