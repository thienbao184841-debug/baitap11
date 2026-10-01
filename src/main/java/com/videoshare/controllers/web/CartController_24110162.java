package com.videoshare.controllers.web;

import com.videoshare.models.CartItem_24110162;
import com.videoshare.models.Video_24110162;
import com.videoshare.services.IVideoService_24110162;
import com.videoshare.services.impl.VideoServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/remove", "/cart/update"})
public class CartController_24110162 extends HttpServlet {
    private IVideoService_24110162 videoService = new VideoServiceImpl_24110162();
    private static final int MAX_QUANTITY = 10;
    private static final int MIN_QUANTITY = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        HttpSession session = req.getSession();
        
        @SuppressWarnings("unchecked")
        List<CartItem_24110162> cart = (List<CartItem_24110162>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        if (url.endsWith("/cart")) {
            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/views/web/cart.jsp").forward(req, resp);
        } else if (url.contains("/cart/add")) {
            String videoId = req.getParameter("videoId");
            int quantity = 1;
            boolean exists = false;
            for (CartItem_24110162 item : cart) {
                if (item.getVideo().getVideoId().equals(videoId)) {
                    if (item.getQuantity() < MAX_QUANTITY) {
                        item.setQuantity(item.getQuantity() + quantity);
                    }
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                Video_24110162 video = videoService.getVideoDetail(videoId);
                if (video != null) {
                    cart.add(new CartItem_24110162(video, quantity));
                }
            }
            session.setAttribute("cart", cart);
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else if (url.contains("/cart/remove")) {
            String videoId = req.getParameter("videoId");
            cart.removeIf(item -> item.getVideo().getVideoId().equals(videoId));
            session.setAttribute("cart", cart);
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("/cart/update")) {
            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            List<CartItem_24110162> cart = (List<CartItem_24110162>) session.getAttribute("cart");
            if (cart != null) {
                String videoId = req.getParameter("videoId");
                try {
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    if (quantity < MIN_QUANTITY) quantity = MIN_QUANTITY;
                    if (quantity > MAX_QUANTITY) quantity = MAX_QUANTITY;
                    
                    for (CartItem_24110162 item : cart) {
                        if (item.getVideo().getVideoId().equals(videoId)) {
                            item.setQuantity(quantity);
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
                session.setAttribute("cart", cart);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
