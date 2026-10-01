<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 850px; margin: 0 auto; background: white; padding: 35px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.08);">
    
    <!-- Header thành công -->
    <div style="text-align: center; margin-bottom: 30px;">
        <div style="width: 70px; height: 70px; background: #eafaf1; border-radius: 50%; display: inline-flex; align-items: center; justify-content: center; font-size: 36px; color: #27ae60; margin-bottom: 15px;">
            ✔
        </div>
        <h2 style="color: #27ae60; margin: 0 0 8px 0;">Đặt Hàng COD Thành Công!</h2>
        <p style="color: #7f8c8d; font-size: 15px; margin: 0;">Cảm ơn bạn đã tin tưởng mua sắm tại VideoShare. Đơn hàng của bạn đang được chuẩn bị để giao.</p>
    </div>

    <!-- Hộp thông tin chi tiết đơn hàng -->
    <div style="background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 20px; margin-bottom: 25px;">
        <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #e2e8f0; padding-bottom: 12px; margin-bottom: 15px;">
            <div>
                <span style="color: #64748b; font-size: 13px;">Mã đơn hàng:</span>
                <strong style="color: #1e293b; font-size: 16px; margin-left: 5px;">${order.orderId}</strong>
            </div>
            <div>
                <span style="background: #fef3c7; color: #92400e; font-weight: bold; padding: 4px 12px; border-radius: 20px; font-size: 13px; border: 1px solid #fde68a;">
                    🚚 ${order.status}
                </span>
            </div>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; font-size: 14px; line-height: 1.6;">
            <div>
                <strong style="color: #475569;">Người nhận:</strong> ${order.fullname}
            </div>
            <div>
                <strong style="color: #475569;">Số điện thoại:</strong> ${order.phone}
            </div>
            <div style="grid-column: span 2;">
                <strong style="color: #475569;">Địa chỉ giao:</strong> ${order.address}
            </div>
            <div>
                <strong style="color: #475569;">Hình thức:</strong> <span style="color: #2563eb; font-weight: bold;">${order.paymentMethod}</span>
            </div>
            <c:if test="${not empty order.note}">
                <div style="grid-column: span 2;">
                    <strong style="color: #475569;">Ghi chú:</strong> <em>${order.note}</em>
                </div>
            </c:if>
        </div>
    </div>

    <!-- Bảng danh sách sản phẩm trong đơn -->
    <h3 style="color: #334155; margin-bottom: 12px; font-size: 16px;">📋 Chi tiết sản phẩm đã đặt</h3>
    <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px; font-size: 14px;">
        <thead>
            <tr style="background: #334155; color: white;">
                <th style="padding: 10px; text-align: left; border: 1px solid #cbd5e1;">Sản phẩm</th>
                <th style="padding: 10px; text-align: center; border: 1px solid #cbd5e1; width: 100px;">Đơn giá</th>
                <th style="padding: 10px; text-align: center; border: 1px solid #cbd5e1; width: 80px;">Số lượng</th>
                <th style="padding: 10px; text-align: right; border: 1px solid #cbd5e1; width: 120px;">Thành tiền</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${order.items}">
                <tr style="border-bottom: 1px solid #e2e8f0;">
                    <td style="padding: 10px; border: 1px solid #e2e8f0; font-weight: bold; color: #1e293b;">
                        ${item.videoTitle}
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0; text-align: center; color: #64748b;">
                        50.000 đ
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0; text-align: center; font-weight: bold;">
                        ${item.quantity}
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0; text-align: right; font-weight: bold; color: #2563eb;">
                        ${item.totalPrice} đ
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr style="background: #f1f5f9; font-weight: bold;">
                <td colspan="3" style="padding: 12px; border: 1px solid #e2e8f0; text-align: right; color: #1e293b; font-size: 15px;">
                    Tổng số tiền thanh toán khi nhận hàng (COD):
                </td>
                <td style="padding: 12px; border: 1px solid #e2e8f0; text-align: right; color: #e11d48; font-size: 18px;">
                    ${order.totalAmount} đ
                </td>
            </tr>
        </tfoot>
    </table>

    <!-- Hộp ghi chú shipper -->
    <div style="background: #ecfdf5; border-left: 4px solid #10b981; padding: 12px 15px; border-radius: 4px; margin-bottom: 25px; font-size: 13px; color: #065f46;">
        💡 <strong>Lưu ý giao hàng COD:</strong> Shipper sẽ liên hệ trước qua số điện thoại <strong>${order.phone}</strong> trước khi giao hàng. Quý khách vui lòng chuẩn bị số tiền mặt <strong>${order.totalAmount} đ</strong> để thanh toán khi nhận kiện hàng.
    </div>

    <!-- Nút điều hướng -->
    <div style="text-align: center; display: flex; justify-content: center; gap: 15px;">
        <a href="<c:url value='/videos'/>" style="background: #2563eb; color: white; padding: 10px 22px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 14px;">
            🛒 Tiếp tục xem sản phẩm
        </a>
        <a href="<c:url value='/home'/>" style="background: #64748b; color: white; padding: 10px 22px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 14px;">
            🏠 Về Trang Chủ
        </a>
    </div>

</div>

<jsp:include page="/decorators/footer.jsp" />
