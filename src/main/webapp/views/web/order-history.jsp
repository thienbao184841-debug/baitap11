<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1100px; margin: 0 auto; background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08);">
    
    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #3498db; padding-bottom: 15px; margin-bottom: 20px;">
        <h2 style="color: #2c3e50; margin: 0;">📜 Lịch sử đặt hàng</h2>
        <a href="<c:url value='/videos'/>" style="background: #3498db; color: white; padding: 8px 18px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 14px;">
            🛒 Mua thêm sản phẩm
        </a>
    </div>

    <!-- Thông báo thành công / lỗi -->
    <c:if test="${not empty msgSuccess}">
        <div style="background: #d4edda; color: #155724; padding: 12px 15px; border-radius: 6px; margin-bottom: 20px; font-weight: bold; border-left: 4px solid #28a745;">
            ${msgSuccess}
        </div>
    </c:if>
    <c:if test="${not empty msgError}">
        <div style="background: #f8d7da; color: #721c24; padding: 12px 15px; border-radius: 6px; margin-bottom: 20px; font-weight: bold; border-left: 4px solid #dc3545;">
            ${msgError}
        </div>
    </c:if>

    <!-- Thanh Tabs lọc theo 8 trạng thái -->
    <div style="display: flex; gap: 8px; flex-wrap: wrap; margin-bottom: 25px; background: #f8fafc; padding: 12px; border-radius: 8px; border: 1px solid #e2e8f0;">
        
        <!-- Tab Tất cả -->
        <a href="<c:url value='/orders?status=Tất cả'/>" 
           style="padding: 8px 14px; border-radius: 20px; text-decoration: none; font-size: 13px; font-weight: bold; transition: all 0.2s;
           ${currentStatus == 'Tất cả' ? 'background: #2563eb; color: white; box-shadow: 0 2px 4px rgba(37,99,235,0.3);' : 'background: white; color: #475569; border: 1px solid #cbd5e1;'}">
            Tất cả (${statusCounts['Tất cả']})
        </a>

        <!-- Danh sách 8 trạng thái theo yêu cầu -->
        <c:forEach var="st" items="${allStatuses}">
            <a href="<c:url value='/orders?status=${st}'/>" 
               style="padding: 8px 14px; border-radius: 20px; text-decoration: none; font-size: 13px; font-weight: bold; transition: all 0.2s;
               ${currentStatus == st ? 'background: #2563eb; color: white; box-shadow: 0 2px 4px rgba(37,99,235,0.3);' : 'background: white; color: #475569; border: 1px solid #cbd5e1;'}">
                ${st} (${statusCounts[st] != null ? statusCounts[st] : 0})
            </a>
        </c:forEach>
    </div>

    <!-- Danh sách đơn hàng -->
    <c:if test="${empty orders}">
        <div style="text-align: center; padding: 60px 20px; background: #f8fafc; border-radius: 8px; border: 1px dashed #cbd5e1;">
            <div style="font-size: 40px; margin-bottom: 10px;">📦</div>
            <h3 style="color: #64748b; margin-bottom: 8px;">Không có đơn hàng nào</h3>
            <p style="color: #94a3b8; font-size: 14px; margin-bottom: 20px;">
                Hiện tại không có đơn hàng nào ở trạng thái "<strong>${currentStatus}</strong>".
            </p>
            <a href="<c:url value='/videos'/>" style="background: #2563eb; color: white; padding: 10px 22px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 14px;">
                Khám phá video ngay
            </a>
        </div>
    </c:if>

    <c:if test="${not empty orders}">
        <div style="display: flex; flex-direction: column; gap: 20px;">
            <c:forEach var="ord" items="${orders}">
                
                <div style="background: white; border: 1px solid #e2e8f0; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.04);">
                    
                    <!-- Header của card đơn hàng -->
                    <div style="background: #f1f5f9; padding: 12px 20px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #e2e8f0;">
                        <div style="display: flex; align-items: center; gap: 15px;">
                            <span style="font-weight: bold; color: #1e293b; font-size: 15px;">
                                #${ord.orderId}
                            </span>
                            <span style="font-size: 13px; color: #64748b;">
                                Ngày đặt: ${ord.orderDate}
                            </span>
                        </div>
                        
                        <!-- Badge trạng thái màu sắc riêng biệt -->
                        <div>
                            <c:choose>
                                <c:when test="${ord.status == 'Đơn hàng mới'}">
                                    <span style="background: #dbeafe; color: #1d4ed8; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        ✨ ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Đã xác nhận'}">
                                    <span style="background: #ccfbf1; color: #0f766e; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        ✔ ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Chuẩn bị hàng'}">
                                    <span style="background: #fef3c7; color: #b45309; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        📦 ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Vận chuyển'}">
                                    <span style="background: #ede9fe; color: #6d28d9; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        🚚 ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Giao hàng'}">
                                    <span style="background: #e0f2fe; color: #0369a1; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        🛵 ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Đã giao'}">
                                    <span style="background: #dcfce7; color: #15803d; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        🎉 ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Đơn hàng hủy'}">
                                    <span style="background: #fee2e2; color: #b91c1c; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        ❌ ${ord.status}
                                    </span>
                                </c:when>
                                <c:when test="${ord.status == 'Đơn hàng hoàn'}">
                                    <span style="background: #f3f4f6; color: #4b5563; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        🔄 ${ord.status}
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span style="background: #f1f5f9; color: #475569; padding: 5px 12px; border-radius: 12px; font-size: 12px; font-weight: bold;">
                                        ${ord.status}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Danh sách sản phẩm của đơn hàng -->
                    <div style="padding: 15px 20px;">
                        <c:forEach var="item" items="${ord.items}">
                            <div style="display: flex; justify-content: space-between; align-items: center; padding: 8px 0; border-bottom: 1px dashed #f1f5f9;">
                                <div>
                                    <span style="font-weight: bold; color: #2563eb;">${item.videoTitle}</span>
                                    <span style="color: #64748b; font-size: 13px; margin-left: 10px;">&times; ${item.quantity}</span>
                                </div>
                                <div style="font-weight: bold; color: #1e293b; font-size: 14px;">
                                    ${item.totalPrice} đ
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Thông tin người nhận và Footer hành động -->
                    <div style="background: #fafafa; padding: 12px 20px; border-top: 1px solid #f1f5f9; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px;">
                        <div style="font-size: 13px; color: #475569;">
                            <span>Người nhận: <strong>${ord.fullname}</strong> (${ord.phone})</span> | 
                            <span>Địa chỉ: <strong>${ord.address}</strong></span> | 
                            <span>Hình thức: <strong style="color: #16a34a;">${ord.paymentMethod}</strong></span>
                        </div>

                        <div style="display: flex; align-items: center; gap: 15px;">
                            <div style="font-size: 14px; color: #1e293b;">
                                Tổng thanh toán COD: <strong style="color: #e11d48; font-size: 16px;">${ord.totalAmount} đ</strong>
                            </div>

                            <!-- Nút Hủy đơn nếu còn ở trạng thái Đơn hàng mới hoặc Đã xác nhận -->
                            <c:if test="${ord.status == 'Đơn hàng mới' || ord.status == 'Đã xác nhận'}">
                                <a href="<c:url value='/order/cancel?orderId=${ord.orderId}'/>" 
                                   style="background: #ef4444; color: white; padding: 6px 12px; border-radius: 4px; text-decoration: none; font-size: 13px; font-weight: bold;"
                                   onclick="return confirm('Bạn có chắc chắn muốn hủy đơn hàng #${ord.orderId}?');">
                                    ❌ Hủy đơn
                                </a>
                            </c:if>

                            <a href="<c:url value='/order/success?orderId=${ord.orderId}'/>" 
                               style="background: #3b82f6; color: white; padding: 6px 14px; border-radius: 4px; text-decoration: none; font-size: 13px; font-weight: bold;">
                                👁️ Chi tiết
                            </a>
                        </div>
                    </div>

                </div>

            </c:forEach>
        </div>
    </c:if>

</div>

<jsp:include page="/decorators/footer.jsp" />
