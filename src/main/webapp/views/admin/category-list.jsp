<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
    <h2 style="color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px;">Quản lý Danh mục (Categories)</h2>
    
    <div style="margin-bottom: 15px; text-align: right;">
        <a href="<c:url value='/admin/categories/create'/>" style="background: #27ae60; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold;">+ Thêm Danh mục</a>
    </div>

    <table style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead>
            <tr style="background: #34495e; color: white;">
                <th style="padding: 12px; border: 1px solid #ddd; width: 60px; text-align: center;">ID</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Hình ảnh</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Tên danh mục</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Mã Code</th>
                <th style="padding: 12px; border: 1px solid #ddd;">Trạng thái</th>
                <th style="padding: 12px; border: 1px solid #ddd; text-align: center;">Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cat" items="${categoryList}">
                <tr style="border-bottom: 1px solid #ddd; transition: background 0.2s;">
                    <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">${cat.categoryId}</td>
                    <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">
                        <img src="${cat.images}" alt="image" style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;">
                    </td>
                    <td style="padding: 12px; border: 1px solid #ddd; font-weight: bold; color: #2980b9;">${cat.categoryname}</td>
                    <td style="padding: 12px; border: 1px solid #ddd;">${cat.categorycode}</td>
                    <td style="padding: 12px; border: 1px solid #ddd;">
                        <c:if test="${cat.status}"><span style="color: #27ae60;">Kích hoạt</span></c:if>
                        <c:if test="${!cat.status}"><span style="color: #7f8c8d;">Ẩn</span></c:if>
                    </td>
                    <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">
                        <a href="<c:url value='/admin/categories/update?id=${cat.categoryId}'/>" style="color: #2980b9; margin-right: 10px; text-decoration: none;">Sửa</a>
                        <a href="<c:url value='/admin/categories/delete?id=${cat.categoryId}'/>" style="color: #c0392b; text-decoration: none;" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/decorators/footer.jsp" />
