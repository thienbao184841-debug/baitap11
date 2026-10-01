<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<head>
    <title>Đăng ký tài khoản</title>
</head>

    <style>
        .auth-container { display: flex; justify-content: center; align-items: center; min-height: 80vh; padding: 40px 0; }
        .auth-card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 100%; max-width: 450px; transition: transform 0.3s ease; }
        .auth-card:hover { transform: translateY(-5px); }
        .auth-title { text-align: center; margin-bottom: 30px; color: #2c3e50; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-size: 28px; font-weight: 700; }
        .input-group { margin-bottom: 15px; }
        .input-group label { display: block; margin-bottom: 8px; color: #7f8c8d; font-size: 14px; font-weight: 600; }
        .input-group input { width: 100%; padding: 10px 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 15px; transition: border-color 0.3s, box-shadow 0.3s; box-sizing: border-box; }
        .input-group input:focus { border-color: #2ecc71; outline: none; box-shadow: 0 0 5px rgba(46, 204, 113, 0.3); }
        .auth-btn { width: 100%; padding: 14px; background: linear-gradient(135deg, #2ecc71, #27ae60); color: white; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; transition: background 0.3s, transform 0.1s; margin-top: 10px; }
        .auth-btn:hover { background: linear-gradient(135deg, #27ae60, #1e8449); transform: scale(1.02); }
        .alert-error { color: #e74c3c; background: #fdf2f1; border: 1px solid #fadbd8; padding: 12px; border-radius: 6px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        .auth-links { text-align: center; margin-top: 20px; font-size: 14px; }
        .auth-links a { color: #2ecc71; text-decoration: none; font-weight: 600; }
        .auth-links a:hover { text-decoration: underline; }
    </style>

    <div class="auth-container">
        <div class="auth-card">
            <h2 class="auth-title">Đăng Ký Tài Khoản</h2>
            
            <c:if test="${not empty error}">
                <div class="alert-error">${error}</div>
            </c:if>

            <form action="<c:url value='/register'/>" method="post">
                <div class="input-group">
                    <label for="username">Tên đăng nhập</label>
                    <input type="text" id="username" name="username" required placeholder="Ví dụ: thienbao123">
                </div>
                
                <div class="input-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" required placeholder="Tối thiểu 6 ký tự">
                </div>
                
                <div class="input-group">
                    <label for="fullname">Họ và tên</label>
                    <input type="text" id="fullname" name="fullname" required placeholder="Ví dụ: Trần Thiên Bảo">
                </div>
                
                <div class="input-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required placeholder="Để nhận mã OTP kích hoạt">
                </div>
                
                <div class="input-group">
                    <label for="phone">Số điện thoại</label>
                    <input type="text" id="phone" name="phone" required placeholder="Ví dụ: 0987654321">
                </div>
                
                <button type="submit" class="auth-btn">Tạo Tài Khoản & Gửi OTP</button>
            </form>
            
            <div class="auth-links">
                Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a>
            </div>
        </div>
    </div>


<jsp:include page="/decorators/footer.jsp" />
