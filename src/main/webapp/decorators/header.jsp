<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>VideoShare</title>
</head>
<body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #ecf0f1; display: flex; flex-direction: column; min-height: 100vh;">
    <header style="background-color: #2c3e50; color: white; padding: 15px;">
        <nav style="display: flex; justify-content: space-between; align-items: center;">
            <div style="font-size: 20px; font-weight: bold;">
                <a href="<c:url value='/home' />" style="color: white; text-decoration: none;">VideoShare</a>
            </div>
            <div>
                <a href="<c:url value='/home' />" style="color: white; margin-right: 15px; text-decoration: none;">Trang Chủ</a>
                <a href="<c:url value='/videos' />" style="color: white; margin-right: 15px; text-decoration: none;">Sản phẩm</a>
                <a href="<c:url value='/cart' />" style="color: #2ecc71; margin-right: 15px; text-decoration: none; font-weight: bold;">🛒 Giỏ hàng</a>
                <a href="<c:url value='/orders' />" style="color: #60a5fa; margin-right: 15px; text-decoration: none; font-weight: bold;">📜 Đơn hàng</a>
                
                <c:choose>
                    <c:when test="${sessionScope.account != null}">
                        <span style="margin-right: 15px;">Chào, ${sessionScope.account.username}</span>
                        <c:if test="${sessionScope.account.admin}">
                            <a href="<c:url value='/admin/home' />" style="color: #f1c40f; margin-right: 15px; text-decoration: none;">Trang quản trị</a>
                            <a href="<c:url value='/admin/orders' />" style="color: #fbbf24; margin-right: 15px; text-decoration: none;">📦 QL Đơn hàng</a>
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
    <main style="padding: 20px; min-height: 500px; background-color: #ecf0f1;">
