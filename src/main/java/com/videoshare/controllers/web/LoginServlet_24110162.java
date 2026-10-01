package com.videoshare.controllers.web;

import com.videoshare.models.User_24110162;
import com.videoshare.services.IUserService_24110162;
import com.videoshare.services.impl.UserServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String msgWarning = (String) session.getAttribute("msgWarning");
        if (msgWarning != null) {
            req.setAttribute("error", msgWarning);
            session.removeAttribute("msgWarning");
        }
        req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Gọi DB kiểm tra
        User_24110162 user = userService.login(username, password);

        if (user != null) {
            // Tạo session lưu thông tin account
            HttpSession session = req.getSession();
            session.setAttribute("account", user);

            // Kiểm tra xem có URL cần quay lại trước khi đăng nhập không (VD: checkout, orders)
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            session.removeAttribute("redirectUrl");
            if (redirectUrl != null && !redirectUrl.trim().isEmpty()) {
                resp.sendRedirect(redirectUrl);
                return;
            }

            // Phân quyền theo Câu 2
            if (user.isAdmin()) {
                // Admin thành công -> vào trang chủ admin
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                // User_24110162 -> vào trang chủ user
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            // Thất bại -> quay lại trang đăng nhập (Câu 2)
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        }
    }
}
