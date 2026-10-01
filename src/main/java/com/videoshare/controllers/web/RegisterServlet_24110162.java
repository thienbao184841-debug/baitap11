package com.videoshare.controllers.web;

import com.videoshare.models.User_24110162;
import com.videoshare.services.IUserService_24110162;
import com.videoshare.services.impl.UserServiceImpl_24110162;
import com.videoshare.utils.EmailUtil_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // 1. Kiểm tra tồn tại
        if (userService.checkExistUsername(username) || userService.checkExistEmail(email)) {
            req.setAttribute("error", "Tên đăng nhập hoặc Email đã tồn tại!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        // 2. Tạo mã OTP 6 số
        String otp = String.format("%06d", new Random().nextInt(999999));
        
        // 3. Khởi tạo đối tượng User_24110162 tạm (chưa insert vào DB)
        User_24110162 pendingUser = new User_24110162(username, password, phone, fullname, email, false, true, "");
        
        // 4. Lưu User_24110162 và OTP vào Session
        HttpSession session = req.getSession();
        session.setAttribute("pendingUser", pendingUser);
        session.setAttribute("otp", otp);

        // 5. Gửi email
        boolean isSent = EmailUtil_24110162.sendOTP(email, otp);
        if (isSent) {
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } else {
            req.setAttribute("error", "Lỗi gửi mail OTP. Vui lòng thử lại!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
        }
    }
}
