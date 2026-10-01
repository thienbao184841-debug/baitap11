<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<head>
    <title>Xác nhận OTP</title>
</head>

    <style>
        .auth-container { display: flex; justify-content: center; align-items: center; min-height: 70vh; }
        .auth-card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 100%; max-width: 400px; transition: transform 0.3s ease; text-align: center; }
        .auth-card:hover { transform: translateY(-5px); }
        .auth-title { text-align: center; margin-bottom: 15px; color: #2c3e50; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-size: 28px; font-weight: 700; }
        .auth-desc { color: #7f8c8d; font-size: 15px; margin-bottom: 25px; }
        .input-group { margin-bottom: 25px; text-align: left; }
        .input-group label { display: block; margin-bottom: 8px; color: #7f8c8d; font-size: 14px; font-weight: 600; }
        .input-group input { width: 100%; padding: 15px; border: 2px dashed #bdc3c7; border-radius: 8px; font-size: 24px; text-align: center; letter-spacing: 5px; font-weight: bold; transition: border-color 0.3s; box-sizing: border-box; }
        .input-group input:focus { border-color: #f39c12; outline: none; border-style: solid; }
        .auth-btn { width: 100%; padding: 14px; background: linear-gradient(135deg, #f39c12, #d35400); color: white; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; transition: background 0.3s, transform 0.1s; }
        .auth-btn:hover { background: linear-gradient(135deg, #d35400, #a04000); transform: scale(1.02); }
        .alert-error { color: #e74c3c; background: #fdf2f1; border: 1px solid #fadbd8; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 14px; }
    </style>

    <div class="auth-container">
        <div class="auth-card">
            <h2 class="auth-title">Nhập Mã OTP</h2>
            <p class="auth-desc">Mã xác nhận 6 số đã được gửi đến email <b>${sessionScope.pendingUser.email}</b> của bạn.</p>
            
            <c:if test="${not empty error}">
                <div class="alert-error">${error}</div>
            </c:if>

            <form action="<c:url value='/verify-otp'/>" method="post">
                <div class="input-group">
                    <label for="otp">Mã Xác Nhận (OTP)</label>
                    <input type="text" id="otp" name="otp" required maxlength="6" autocomplete="off" placeholder="------">
                </div>
                
                <button type="submit" class="auth-btn">Kích Hoạt Tài Khoản</button>
            </form>
        </div>
    </div>


<jsp:include page="/decorators/footer.jsp" />
