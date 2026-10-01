package com.videoshare.controllers.admin;

import com.videoshare.models.Category_24110162;
import com.videoshare.services.ICategoryService_24110162;
import com.videoshare.services.impl.CategoryServiceImpl_24110162;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/categories", "/admin/categories/create", "/admin/categories/update", "/admin/categories/delete"})
public class AdminCategoryController_24110162 extends HttpServlet {
    private ICategoryService_24110162 categoryService = new CategoryServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("create")) {
            req.getRequestDispatcher("/views/admin/category-form.jsp").forward(req, resp);
        } else if (url.contains("update")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Category_24110162 category = categoryService.findById(id);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/views/admin/category-form.jsp").forward(req, resp);
        } else if (url.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            categoryService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else {
            List<Category_24110162> list = categoryService.findAll();
            req.setAttribute("categoryList", list);
            req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Category_24110162 category = new Category_24110162();
        category.setCategoryname(req.getParameter("categoryname"));
        category.setCategorycode(req.getParameter("categorycode"));
        category.setImages(req.getParameter("images"));
        category.setStatus(req.getParameter("status") != null);

        if (url.contains("create")) {
            categoryService.insert(category);
        } else if (url.contains("update")) {
            category.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
            categoryService.update(category);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
