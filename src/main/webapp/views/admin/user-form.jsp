<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
    <h2 style="color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; margin-bottom: 20px;">
        ${not empty user ? 'Cập nhật User' : 'Thêm mới User'}
    </h2>

    <form action="" method="post">
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Username <span style="color:red;">*</span></label>
            <input type="text" name="username" value="${user.username}" ${not empty user ? 'readonly' : ''} required style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box; ${not empty user ? 'background: #ecf0f1;' : ''}">
        </div>
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Mật khẩu <span style="color:red;">*</span></label>
            <input type="password" name="password" value="${user.password}" required style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Họ và tên</label>
            <input type="text" name="fullname" value="${user.fullname}" style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Email <span style="color:red;">*</span></label>
            <input type="email" name="email" value="${user.email}" required style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Số điện thoại</label>
            <input type="text" name="phone" value="${user.phone}" style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Ảnh đại diện (URL)</label>
            <input type="text" name="images" value="${user.images}" style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        
        <div style="margin-bottom: 25px; display: flex; gap: 20px;">
            <label style="display: flex; align-items: center; gap: 5px;">
                <input type="checkbox" name="admin" ${user.admin ? 'checked' : ''}> Quyền Admin
            </label>
            <label style="display: flex; align-items: center; gap: 5px;">
                <input type="checkbox" name="active" ${user.active || empty user ? 'checked' : ''}> Đang hoạt động
            </label>
        </div>

        <div style="display: flex; gap: 10px;">
            <button type="submit" style="background: #27ae60; color: white; padding: 12px 20px; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; flex: 1;">
                Lưu Thông Tin
            </button>
            <a href="<c:url value='/admin/users'/>" style="background: #95a5a6; color: white; padding: 12px 20px; border: none; border-radius: 4px; font-weight: bold; text-decoration: none; text-align: center; cursor: pointer; flex: 1;">
                Hủy bỏ
            </a>
        </div>
    </form>
</div>

<jsp:include page="/decorators/footer.jsp" />
