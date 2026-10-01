package com.videoshare.controllers.web;

import com.videoshare.models.CartItem_24110162;
import com.videoshare.models.Order_24110162;
import com.videoshare.models.User_24110162;
import com.videoshare.services.IOrderService_24110162;
import com.videoshare.services.impl.OrderServiceImpl_24110162;
import com.videoshare.utils.EmailUtil_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/checkout", "/checkout/cod", "/order/success"})
public class CheckoutController_24110162 extends HttpServlet {
    private IOrderService_24110162 orderService = new OrderServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // 1. Màn hình thông báo đặt hàng thành công
        if (uri.contains("/order/success")) {
            String orderId = req.getParameter("orderId");
            if (orderId != null) {
                Order_24110162 order = orderService.getOrderById(orderId);
                req.setAttribute("order", order);
            }
            req.getRequestDispatcher("/views/web/order-success.jsp").forward(req, resp);
            return;
        }

        // 2. Màn hình thanh toán COD
        HttpSession session = req.getSession();
        User_24110162 user = (User_24110162) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("msgWarning", "Vui lòng đăng nhập tài khoản để tiến hành đặt hàng!");
            session.setAttribute("redirectUrl", req.getContextPath() + "/checkout");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        @SuppressWarnings("unchecked")
        List<CartItem_24110162> cart = (List<CartItem_24110162>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        double subtotal = 0;
        for (CartItem_24110162 item : cart) {
            subtotal += item.getTotalPrice();
        }

        req.setAttribute("subtotal", subtotal);
        req.setAttribute("shippingFee", 0.0); // Miễn phí giao hàng COD
        req.setAttribute("totalAmount", subtotal);

        // Tự động điền thông tin nếu User đã đăng nhập
        req.setAttribute("fullname", user.getFullname());
        req.setAttribute("email", user.getEmail());
        req.setAttribute("phone", user.getPhone());

        req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        User_24110162 user = (User_24110162) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("msgWarning", "Vui lòng đăng nhập tài khoản để tiến hành đặt hàng!");
            session.setAttribute("redirectUrl", req.getContextPath() + "/checkout");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        @SuppressWarnings("unchecked")
        List<CartItem_24110162> cart = (List<CartItem_24110162>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");

        // Validate thông tin giao hàng
        if (fullname == null || fullname.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {

            double subtotal = 0;
            for (CartItem_24110162 item : cart) subtotal += item.getTotalPrice();
            req.setAttribute("subtotal", subtotal);
            req.setAttribute("shippingFee", 0.0);
            req.setAttribute("totalAmount", subtotal);

            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("note", note);
            req.setAttribute("error", "Vui lòng nhập đầy đủ Họ tên, Số điện thoại và Địa chỉ giao hàng!");
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
            return;
        }

        // Lấy chính xác username của user đã đăng nhập
        String username = user.getUsername();

        // Xử lý tạo đơn hàng COD
        Order_24110162 order = orderService.processCODOrder(username, fullname, email, phone, address, note, cart);

        if (order != null) {
            // Xóa sạch giỏ hàng sau khi đặt thành công
            session.removeAttribute("cart");

            // Chuyển hướng sang trang báo thành công
            resp.sendRedirect(req.getContextPath() + "/order/success?orderId=" + order.getOrderId());
        } else {
            req.setAttribute("error", "Có lỗi xảy ra trong quá trình tạo đơn hàng. Vui lòng thử lại!");
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
        }
    }
}
