<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>
                <sitemesh:write property='title' />
            </title>
            <!-- Thêm CSS của Admin (sẽ làm sau) -->
            <sitemesh:write property='head' />
        </head>

        <body>
            <!-- HEADER -->
            <header style="background-color: #333; color: white; padding: 10px;">
                <h2>Admin Dashboard - Quản trị hệ thống</h2>
                <nav>
                    <a href="<c:url value='/admin/home' />" style="color: white; margin-right: 15px;">Trang chủ
                        Admin</a>
                    <a href="<c:url value='/admin/users' />" style="color: white; margin-right: 15px;">Quản lý Users</a>
                    <a href="<c:url value='/admin/videos' />" style="color: white; margin-right: 15px;">Quản lý
                        Videos</a>
                    <a href="<c:url value='/logout' />" style="color: white;">Đăng xuất</a>
                </nav>
            </header>

            <!-- CONTENT BODY (Phần thay đổi theo từng trang con) -->
            <main style="padding: 20px; min-height: 400px;">
                <sitemesh:write property='body' />
            </main>

            <!-- FOOTER -->
            <footer style="background-color: #f1f1f1; text-align: center; padding: 15px;">
                <p>Họ tên: Trần Thiên Bảo - MSSV: 24110162 - Mã đề: 04</p>
            </footer>
        </body>

        </html>
