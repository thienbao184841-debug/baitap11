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
                <a href="<c:url value='/home' />" style="color: white; margin-right: 15px; text-decoration: none;">Trang Ch&#7911;</a>
                <a href="<c:url value='/videos' />" style="color: white; margin-right: 15px; text-decoration: none;">S&#7843;n ph&#7849;m</a>
                <a href="<c:url value='/cart' />" style="color: #2ecc71; margin-right: 15px; text-decoration: none; font-weight: bold;">&#128722; Gi&#7887; h&agrave;ng</a>
                
                <c:choose>
                    <c:when test="${sessionScope.account != null}">
                        <span style="margin-right: 15px;">Ch&agrave;o, ${sessionScope.account.username}</span>
                        <c:if test="${sessionScope.account.admin}">
                            <a href="<c:url value='/admin/home' />" style="color: #f1c40f; margin-right: 15px; text-decoration: none;">Trang qu&#7843;n tr&#7883;</a>
                        </c:if>
                        <a href="<c:url value='/logout' />" style="color: #e74c3c; text-decoration: none;">&#272;&#259;ng xu&#7845;t</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/login' />" style="color: white; text-decoration: none;">&#272;&#259;ng nh&#7853;p</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>
    <main style="padding: 20px; min-height: 500px; background-color: #ecf0f1;">
