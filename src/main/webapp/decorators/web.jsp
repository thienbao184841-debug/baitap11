<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property='title' /></title>
    <!-- Thêm CSS của Web (sẽ làm sau) -->
    <sitemesh:write property='head' />
</head>
<body>
    <!-- HEADER -->
    <header style="background-color: #2c3e50; color: white; padding: 15px;">
        <nav style="display: flex; justify-content: space-between; align-items: center;">
            <div style="font-size: 20px; font-weight: bold;">
                <a href="<c:url value='/home' />" style="color: white; text-decoration: none;">VideoShare</a>
            </div>
            <div>
                <!-- Menu yêu cầu theo đề bài: Trang Chủ, Sản phẩm (Video), Đăng nhập, Trang quản trị -->
                <a href="<c:url value='/home' />" style="color: white; margin-right: 15px; text-decoration: none;">Trang Chủ</a>
                <a href="<c:url value='/videos' />" style="color: white; margin-right: 15px; text-decoration: none;">Sản phẩm</a>
                
                <!-- Hiển thị Đăng nhập hoặc Trang quản trị tùy theo Session -->
                <c:choose>
                    <c:when test="${sessionScope.account != null}">
                        <span style="margin-right: 15px;">Chào, ${sessionScope.account.username}</span>
                        <c:if test="${sessionScope.account.admin}">
                            <a href="<c:url value='/admin/home' />" style="color: #f1c40f; margin-right: 15px; text-decoration: none;">Trang quản trị</a>
                        </c:if>
                        <a href="<c:url value='/logout' />" style="color: #e74c3c; text-decoration: none;">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/login' />" style="color: white; text-decoration: none;">Đăng nhập</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>

    <!-- CONTENT BODY (Phần thay đổi theo từng trang con) -->
    <main style="padding: 20px; min-height: 500px; background-color: #ecf0f1;">
        <sitemesh:write property='body' />
    </main>

    <!-- FOOTER -->
    <footer style="background-color: #34495e; color: white; text-align: center; padding: 20px;">
        <p>Họ tên: [Tên của bạn] - MSSV: [MSSV của bạn] - Mã đề: 04</p>
    </footer>
</body>
</html>
