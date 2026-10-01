<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 900px; margin: 0 auto; padding: 20px;">
    <!-- Nút quay lại -->
    <a href="<c:url value='/home'/>" style="display: inline-block; margin-bottom: 20px; color: #3498db; text-decoration: none; font-weight: bold;">
        &larr; Quay lại trang chủ
    </a>

    <div style="display: flex; gap: 30px; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 5px 15px rgba(0,0,0,0.1);">
        <!-- Cột trái: Poster -->
        <div style="flex: 0 0 350px;">
            <img src="${video.poster}" alt="${video.title}" style="width: 100%; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); object-fit: cover; aspect-ratio: 16/9;">
        </div>

        <!-- Cột phải: Thông tin chi tiết -->
        <div style="flex: 1;">
            <h1 style="margin-top: 0; color: #2c3e50; font-size: 28px; line-height: 1.4;">${video.title}</h1>
            
            <div style="margin: 15px 0; padding: 15px 0; border-top: 1px solid #eee; border-bottom: 1px solid #eee;">
                <table style="width: 100%; text-align: left; line-height: 2;">
                    <tr>
                        <th style="width: 150px; color: #7f8c8d;">Mã video:</th>
                        <td style="font-weight: bold; color: #2c3e50;">${video.videoId}</td>
                    </tr>
                    <tr>
                        <th style="color: #7f8c8d;">Category name:</th>
                        <td style="font-weight: bold; color: #2980b9;">${video.categoryName}</td>
                    </tr>
                    <tr>
                        <th style="color: #7f8c8d;">Lượt xem (View):</th>
                        <td style="font-weight: bold; color: #e67e22;">${video.views} lượt</td>
                    </tr>
                </table>
            </div>

            <!-- Nút Tương tác -->
            <div style="display: flex; gap: 15px; margin-bottom: 20px;">
                <button style="background: #e74c3c; color: white; border: none; padding: 10px 20px; border-radius: 20px; cursor: pointer; font-weight: bold; display: flex; align-items: center; gap: 5px;">
                    ❤️ Like (${video.likeCount})
                </button>
                <button style="background: #3498db; color: white; border: none; padding: 10px 20px; border-radius: 20px; cursor: pointer; font-weight: bold; display: flex; align-items: center; gap: 5px;">
                    🔗 Share (${video.shareCount})
                </button>
                <a href="<c:url value='/cart/add?videoId=${video.videoId}'/>" style="background: #2ecc71; color: white; border: none; padding: 10px 20px; border-radius: 20px; cursor: pointer; font-weight: bold; display: flex; align-items: center; gap: 5px; text-decoration: none;">
                    🛒 Thêm vào giỏ hàng
                </a>
            </div>

            <!-- Mô tả -->
            <div>
                <h3 style="color: #34495e; margin-bottom: 10px;">Mô tả:</h3>
                <p style="color: #555; line-height: 1.6; text-align: justify; background: #f9f9f9; padding: 15px; border-radius: 6px;">
                    ${video.description}
                </p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/decorators/footer.jsp" />
