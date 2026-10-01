package com.videoshare.controllers.admin;

import com.videoshare.models.User_24110162;
import com.videoshare.services.IUserService_24110162;
import com.videoshare.services.impl.UserServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/users", "/admin/users/create", "/admin/users/update", "/admin/users/delete"})
public class AdminUserController_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("create")) {
            req.getRequestDispatcher("/views/admin/user-form.jsp").forward(req, resp);
        } else if (url.contains("update")) {
            String username = req.getParameter("username");
            User_24110162 user = userService.findById(username);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/views/admin/user-form.jsp").forward(req, resp);
        } else if (url.contains("delete")) {
            String username = req.getParameter("username");
            userService.delete(username);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else {
            // Hiển thị danh sách có phân trang 6 user/trang (Câu 3)
            int page = 1;
            int limit = 6; // Đề bài yêu cầu 6 user / 1 trang
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            int offset = (page - 1) * limit;
            
            List<User_24110162> list = userService.findUsersByPage(offset, limit);
            int total = userService.countAll();
            int totalPage = (int) Math.ceil((double) total / limit);
            
            req.setAttribute("userList", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPage", totalPage);
            
            req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        // Lấy dữ liệu từ form
        User_24110162 user = new User_24110162();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setPhone(req.getParameter("phone"));
        user.setFullname(req.getParameter("fullname"));
        user.setEmail(req.getParameter("email"));
        user.setAdmin(req.getParameter("admin") != null);
        user.setActive(req.getParameter("active") != null);
        user.setImages(req.getParameter("images"));
        
        if (url.contains("create")) {
            userService.insert(user);
        } else if (url.contains("update")) {
            userService.update(user);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
