<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<head>
    <title>Trang Chủ VideoShare</title>
</head>

    <div style="text-align: center; padding: 80px 20px;">
        <h1 style="color: #2c3e50; font-family: 'Segoe UI', sans-serif; font-size: 36px; margin-bottom: 10px;">Chào mừng đến với VideoShare!</h1>
        <p style="color: #7f8c8d; font-size: 18px;">Nền tảng chia sẻ video trực tuyến tuyệt vời nhất.</p>
        <div style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/videos" style="padding: 12px 25px; background: #3498db; color: white; text-decoration: none; border-radius: 25px; font-weight: bold; font-size: 16px;">Khám phá ngay</a>
        </div>
    </div>


<jsp:include page="/decorators/footer.jsp" />
