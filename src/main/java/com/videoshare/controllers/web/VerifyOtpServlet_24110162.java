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

@WebServlet(urlPatterns = {"/verify-otp"})
public class VerifyOtpServlet_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // Nếu không có thông tin đăng ký chờ duyệt, đá về trang đăng ký
        if (session.getAttribute("pendingUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        req.getRequestDispatcher("/views/web/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputOtp = req.getParameter("otp");
        HttpSession session = req.getSession();
        
        String sessionOtp = (String) session.getAttribute("otp");
        User_24110162 pendingUser = (User_24110162) session.getAttribute("pendingUser");

        // So khớp OTP
        if (sessionOtp != null && sessionOtp.equals(inputOtp)) {
            // Khớp -> Thực hiện lưu vào CSDL
            userService.register(pendingUser);
            
            // Xóa session đăng ký tạm
            session.removeAttribute("otp");
            session.removeAttribute("pendingUser");
            
            // Chuyển hướng sang trang đăng nhập với thông báo
            req.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        } else {
            // Sai OTP
            req.setAttribute("error", "Mã OTP không chính xác!");
            req.getRequestDispatcher("/views/web/verify-otp.jsp").forward(req, resp);
        }
    }
}
