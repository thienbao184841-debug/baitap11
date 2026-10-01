package com.videoshare.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/home"})
public class AdminHomeController_24110162 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiện tại trang chủ Admin ta sẽ chuyển hướng sang trang quản lý User_24110162 luôn cho tiện
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
