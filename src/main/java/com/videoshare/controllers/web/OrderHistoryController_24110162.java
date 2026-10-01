package com.videoshare.controllers.web;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/orders", "/order/history", "/order/cancel"})
public class OrderHistoryController_24110162 extends HttpServlet {
    private IOrderService_24110162 orderService = new OrderServiceImpl_24110162();

    public static final String[] ALL_STATUSES = {
        "Đơn hàng mới",
        "Đã xác nhận",
        "Chuẩn bị hàng",
        "Vận chuyển",
        "Giao hàng",
        "Đã giao",
        "Đơn hàng hủy",
        "Đơn hàng hoàn"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession();
        User_24110162 currentUser = (User_24110162) session.getAttribute("account");

        // Xử lý hủy đơn hàng (nếu có action cancel)
        if (uri.contains("/order/cancel")) {
            String orderId = req.getParameter("orderId");
            String username = (currentUser != null) ? currentUser.getUsername() : "";
            boolean success = orderService.cancelOrder(orderId, username);
            if (success) {
                req.getSession().setAttribute("msgSuccess", "Đã hủy đơn hàng #" + orderId + " thành công!");
            } else {
                req.getSession().setAttribute("msgError", "Không thể hủy đơn hàng này do đơn đã được vận chuyển hoặc đã hoàn tất.");
            }
            resp.sendRedirect(req.getContextPath() + "/orders?status=" + java.net.URLEncoder.encode("Đơn hàng hủy", "UTF-8"));
            return;
        }

        // Lấy bộ lọc trạng thái được chọn từ query parameter
        String status = req.getParameter("status");
        if (status == null || status.trim().isEmpty() || status.equalsIgnoreCase("ALL")) {
            status = "Tất cả";
        }

        List<Order_24110162> orders;
        List<Order_24110162> allUserOrders;

        // Nếu đã đăng nhập và là user thường -> xem đơn của mình
        if (currentUser != null && !currentUser.isAdmin()) {
            allUserOrders = orderService.getOrdersByUsername(currentUser.getUsername());
            if ("Tất cả".equals(status)) {
                orders = allUserOrders;
            } else {
                orders = orderService.getOrdersByUsernameAndStatus(currentUser.getUsername(), status);
            }
        } else {
            // Khách vãng lai hoặc Admin xem danh sách đơn hàng
            allUserOrders = orderService.getAllOrders();
            if ("Tất cả".equals(status)) {
                orders = allUserOrders;
            } else {
                orders = orderService.getOrdersByStatus(status);
            }
        }

        // Thống kê số lượng đơn cho từng trạng thái
        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("Tất cả", allUserOrders.size());
        for (String st : ALL_STATUSES) {
            int count = 0;
            for (Order_24110162 ord : allUserOrders) {
                if (ord.getStatus() != null && ord.getStatus().equalsIgnoreCase(st)) {
                    count++;
                }
            }
            statusCounts.put(st, count);
        }

        // Đẩy dữ liệu ra giao diện JSP
        req.setAttribute("orders", orders);
        req.setAttribute("currentStatus", status);
        req.setAttribute("allStatuses", ALL_STATUSES);
        req.setAttribute("statusCounts", statusCounts);

        // Flash message nếu có
        String msgSuccess = (String) session.getAttribute("msgSuccess");
        String msgError = (String) session.getAttribute("msgError");
        if (msgSuccess != null) {
            req.setAttribute("msgSuccess", msgSuccess);
            session.removeAttribute("msgSuccess");
        }
        if (msgError != null) {
            req.setAttribute("msgError", msgError);
            session.removeAttribute("msgError");
        }

        req.getRequestDispatcher("/views/web/order-history.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
