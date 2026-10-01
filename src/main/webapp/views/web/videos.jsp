<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1200px; margin: 0 auto; padding: 20px; display: flex; gap: 30px;">
    
    <!-- Cột trái: Sidebar hiển thị danh sách Categories -->
    <div style="width: 250px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); height: fit-content;">
        <h3 style="color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; margin-top: 0;">Danh mục</h3>
        <ul style="list-style: none; padding: 0; margin: 0;">
            <c:forEach var="cat" items="${categories}">
                <li style="margin-bottom: 10px;">
                    <a href="<c:url value='/videos?categoryId=${cat.categoryId}'/>" 
                       style="display: block; padding: 10px; border-radius: 4px; text-decoration: none; font-weight: bold; transition: background 0.2s;
                       ${currentCategory.categoryId == cat.categoryId ? 'background: #3498db; color: white;' : 'color: #34495e; background: #ecf0f1;'}">
                        ${cat.categoryname}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- Cột phải: Hiển thị Video theo Category được chọn -->
    <div style="flex: 1;">
        <!-- Tiêu đề Category và đếm số lượng (Câu 6) -->
        <h2 style="color: #e67e22; border-bottom: 2px solid #e67e22; padding-bottom: 10px; margin-top: 0;">
            ${currentCategory.categoryname} (${totalVideos})
        </h2>

        <!-- Danh sách Video 3 cột -->
        <div style="display: flex; flex-wrap: wrap; gap: 20px; margin-top: 20px;">
            <c:forEach var="video" items="${videoList}">
                <div style="width: calc(33.333% - 14px); background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1); display: flex; flex-direction: column;">
                    <!-- Ảnh poster -->
                    <a href="<c:url value='/video?id=${video.videoId}'/>">
                        <img src="${video.poster}" alt="${video.title}" style="width: 100%; aspect-ratio: 16/9; object-fit: cover;">
                    </a>
                    
                    <!-- Nội dung -->
                    <div style="padding: 15px; flex: 1; display: flex; flex-direction: column;">
                        <h3 style="margin: 0 0 10px 0; font-size: 16px;">
                            <a href="<c:url value='/video?id=${video.videoId}'/>" style="color: #2c3e50; text-decoration: none;">${video.title}</a>
                        </h3>
                        <p style="margin: 0 0 5px 0; font-size: 14px; color: #7f8c8d;">Mã video: <strong>${video.videoId}</strong></p>
                        <p style="margin: 0 0 5px 0; font-size: 14px; color: #7f8c8d;">Category name: <strong>${video.categoryName}</strong></p>
                        <p style="margin: 0 0 15px 0; font-size: 14px; color: #7f8c8d;">View: <strong>${video.views}</strong></p>
                        
                        <div style="margin-top: auto; display: flex; gap: 10px; flex-wrap: wrap;">
                            <span style="background: #ecf0f1; padding: 5px 10px; border-radius: 4px; font-size: 13px; font-weight: bold; color: #34495e;">Share(${video.shareCount})</span>
                            <span style="background: #ecf0f1; padding: 5px 10px; border-radius: 4px; font-size: 13px; font-weight: bold; color: #e74c3c;">Like(${video.likeCount})</span>
                            <a href="<c:url value='/cart/add?videoId=${video.videoId}'/>" style="background: #2ecc71; color: white; padding: 5px 10px; border-radius: 4px; font-size: 13px; font-weight: bold; text-decoration: none; display: flex; align-items: center; justify-content: center; flex: 1; text-align: center;">Thêm vào giỏ hàng</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
            <c:if test="${empty videoList}">
                <p style="color: #7f8c8d; font-style: italic;">Không có video nào trong danh mục này.</p>
            </c:if>
        </div>

        <!-- Phân trang -->
        <div style="margin-top: 30px; text-align: center;">
            <c:if test="${totalPage > 1}">
                <div style="display: inline-flex; align-items: center; gap: 5px; background: white; padding: 10px 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                    <span style="font-weight: bold; color: #7f8c8d; margin-right: 10px;">&lt;&lt;</span>
                    
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <a href="<c:url value='/videos?categoryId=${currentCategory.categoryId}&page=${i}'/>" 
                           style="display: inline-block; padding: 5px 12px; border-radius: 4px; text-decoration: none; font-weight: bold;
                           ${currentPage == i ? 'background: #3498db; color: white;' : 'background: #ecf0f1; color: #2c3e50;'}">
                            ${i}
                        </a>
                    </c:forEach>
                    
                    <span style="font-weight: bold; color: #7f8c8d; margin-left: 10px;">&gt;&gt;</span>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/decorators/footer.jsp" />
