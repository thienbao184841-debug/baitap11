<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
    <h2 style="color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px;">Quản lý Người Dùng</h2>
    
    <div style="margin-bottom: 15px; text-align: right;">
        <a href="<c:url value='/admin/users/create'/>" style="background: #27ae60; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold;">+ Thêm mới User</a>
    </div>

    <table style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead>
            <tr style="background: #34495e; color: white;">
                <th style="padding: 12px; border: 1px solid #ddd;">Username</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Họ tên</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Email</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Điện thoại</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Quyền</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Trạng thái</th>
                <th style="padding: 12px; border: 1px solid #ddd; text-align: center;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userList}">
                <tr style="border-bottom: 1px solid #ddd; transition: background 0.2s;">
                    <td style="padding: 12px; border: 1px solid #ddd;">${user.username}</td>
                    <td style="padding: 12px; border: 1px solid #ddd;">${user.fullname}</td>
                    <td style="padding: 12px; border: 1px solid #ddd;">${user.email}</td>
                    <td style="padding: 12px; border: 1px solid #ddd;">${user.phone}</td>
                    <td style="padding: 12px; border: 1px solid #ddd;">
                        <c:if test="${user.admin}"><span style="color: #c0392b; font-weight: bold;">Admin</span></c:if>
                        <c:if test="${!user.admin}">User</c:if>
                    </td>
                    <td style="padding: 12px; border: 1px solid #ddd;">
                        <c:if test="${user.active}"><span style="color: #27ae60;">Hoạt động</span></c:if>
                        <c:if test="${!user.active}"><span style="color: #7f8c8d;">Khóa</span></c:if>
                    </td>
                    <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">
                        <a href="<c:url value='/admin/users/update?username=${user.username}'/>" style="color: #2980b9; margin-right: 10px; text-decoration: none;">Sửa</a>
                        <a href="<c:url value='/admin/users/delete?username=${user.username}'/>" style="color: #c0392b; text-decoration: none;" onclick="return confirm('Bạn có chắc chắn muốn xóa user này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Phân trang 6 items / trang -->
    <div style="margin-top: 20px; text-align: center;">
        <c:if test="${totalPage > 1}">
            <c:forEach begin="1" end="${totalPage}" var="i">
                <a href="<c:url value='/admin/users?page=${i}'/>" style="display: inline-block; padding: 8px 12px; margin: 0 4px; border-radius: 4px; text-decoration: none; ${currentPage == i ? 'background: #3498db; color: white;' : 'background: #ecf0f1; color: #2c3e50; border: 1px solid #bdc3c7;'}">${i}</a>
            </c:forEach>
        </c:if>
    </div>
</div>

<jsp:include page="/decorators/footer.jsp" />
