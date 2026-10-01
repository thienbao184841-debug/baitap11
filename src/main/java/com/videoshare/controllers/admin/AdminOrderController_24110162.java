package com.videoshare.controllers.admin;

import com.videoshare.controllers.web.OrderHistoryController_24110162;
import com.videoshare.models.Order_24110162;
import com.videoshare.models.User_24110162;
import com.videoshare.services.IOrderService_24110162;
import com.videoshare.services.impl.OrderServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/admin/orders", "/admin/orders/update-status"})
public class AdminOrderController_24110162 extends HttpServlet {
    private IOrderService_24110162 orderService = new OrderServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24110162 user = (User_24110162) session.getAttribute("account");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String status = req.getParameter("status");
        if (status == null || status.trim().isEmpty() || status.equalsIgnoreCase("ALL")) {
            status = "Tất cả";
        }

        List<Order_24110162> allOrders = orderService.getAllOrders();
        List<Order_24110162> filteredOrders = "Tất cả".equals(status) ? allOrders : orderService.getOrdersByStatus(status);

        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("Tất cả", allOrders.size());
        for (String st : OrderHistoryController_24110162.ALL_STATUSES) {
            int count = 0;
            for (Order_24110162 ord : allOrders) {
                if (ord.getStatus() != null && ord.getStatus().equalsIgnoreCase(st)) {
                    count++;
                }
            }
            statusCounts.put(st, count);
        }

        req.setAttribute("orders", filteredOrders);
        req.setAttribute("currentStatus", status);
        req.setAttribute("allStatuses", OrderHistoryController_24110162.ALL_STATUSES);
        req.setAttribute("statusCounts", statusCounts);

        req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User_24110162 user = (User_24110162) session.getAttribute("account");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String orderId = req.getParameter("orderId");
        String newStatus = req.getParameter("newStatus");

        if (orderId != null && newStatus != null) {
            orderService.updateOrderStatus(orderId, newStatus);
        }

        String redirectStatus = (newStatus != null) ? URLEncoder.encode(newStatus, "UTF-8") : "Tất+cả";
        resp.sendRedirect(req.getContextPath() + "/admin/orders?status=" + redirectStatus);
    }
}
