<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
    <h2 style="color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; margin-bottom: 20px;">
        ${not empty category ? 'Cập nhật Danh mục' : 'Thêm Danh mục Mới'}
    </h2>

    <form action="" method="post">
        <c:if test="${not empty category}">
            <input type="hidden" name="categoryId" value="${category.categoryId}">
        </c:if>
        
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Tên danh mục <span style="color:red;">*</span></label>
            <input type="text" name="categoryname" value="${category.categoryname}" required style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Mã code danh mục <span style="color:red;">*</span></label>
            <input type="text" name="categorycode" value="${category.categorycode}" required style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #34495e;">Hình ảnh (URL)</label>
            <input type="text" name="images" value="${category.images}" style="width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 4px; box-sizing: border-box;">
        </div>
        
        <div style="margin-bottom: 25px;">
            <label style="display: flex; align-items: center; gap: 5px;">
                <input type="checkbox" name="status" ${category.status || empty category ? 'checked' : ''}> Trạng thái Kích hoạt
            </label>
        </div>

        <div style="display: flex; gap: 10px;">
            <button type="submit" style="background: #27ae60; color: white; padding: 12px 20px; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; flex: 1;">
                Lưu Danh mục
            </button>
            <a href="<c:url value='/admin/categories'/>" style="background: #95a5a6; color: white; padding: 12px 20px; border: none; border-radius: 4px; font-weight: bold; text-decoration: none; text-align: center; cursor: pointer; flex: 1;">
                Hủy bỏ
            </a>
        </div>
    </form>
</div>

<jsp:include page="/decorators/footer.jsp" />
