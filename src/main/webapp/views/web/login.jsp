<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<head>
    <title>Đăng nhập</title>
</head>

    <style>
        .auth-container { display: flex; justify-content: center; align-items: center; min-height: 70vh; }
        .auth-card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 100%; max-width: 400px; transition: transform 0.3s ease; }
        .auth-card:hover { transform: translateY(-5px); }
        .auth-title { text-align: center; margin-bottom: 30px; color: #2c3e50; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-size: 28px; font-weight: 700; }
        .input-group { margin-bottom: 20px; }
        .input-group label { display: block; margin-bottom: 8px; color: #7f8c8d; font-size: 14px; font-weight: 600; }
        .input-group input { width: 100%; padding: 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 16px; transition: border-color 0.3s, box-shadow 0.3s; box-sizing: border-box; }
        .input-group input:focus { border-color: #3498db; outline: none; box-shadow: 0 0 5px rgba(52, 152, 219, 0.3); }
        .auth-btn { width: 100%; padding: 14px; background: linear-gradient(135deg, #3498db, #2980b9); color: white; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; transition: background 0.3s, transform 0.1s; }
        .auth-btn:hover { background: linear-gradient(135deg, #2980b9, #2471a3); transform: scale(1.02); }
        .alert-error { color: #e74c3c; background: #fdf2f1; border: 1px solid #fadbd8; padding: 12px; border-radius: 6px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        .alert-success { color: #27ae60; background: #eafaf1; border: 1px solid #d5f5e3; padding: 12px; border-radius: 6px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        .auth-links { text-align: center; margin-top: 20px; font-size: 14px; }
        .auth-links a { color: #3498db; text-decoration: none; font-weight: 600; }
        .auth-links a:hover { text-decoration: underline; }
    </style>

    <div class="auth-container">
        <div class="auth-card">
            <h2 class="auth-title">Đăng Nhập</h2>
            
            <c:if test="${not empty error}">
                <div class="alert-error">${error}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert-success">${message}</div>
            </c:if>

            <form action="<c:url value='/login'/>" method="post">
                <div class="input-group">
                    <label for="username">Tên đăng nhập</label>
                    <input type="text" id="username" name="username" required placeholder="Nhập tài khoản của bạn">
                </div>
                
                <div class="input-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" required placeholder="Nhập mật khẩu">
                </div>
                
                <button type="submit" class="auth-btn">Đăng Nhập</button>
            </form>
            
            <div class="auth-links">
                Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a>
            </div>
        </div>
    </div>


<jsp:include page="/decorators/footer.jsp" />
