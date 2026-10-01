<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1100px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08);">
    
    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #3498db; padding-bottom: 15px; margin-bottom: 25px;">
        <h2 style="color: #2c3e50; margin: 0;">📦 Thanh toán đơn hàng COD</h2>
        <a href="<c:url value='/cart'/>" style="color: #3498db; text-decoration: none; font-weight: bold;">&larr; Quay lại giỏ hàng</a>
    </div>

    <c:if test="${not empty error}">
        <div style="background: #fadbd8; color: #78281f; padding: 12px 15px; border-radius: 6px; margin-bottom: 20px; font-weight: bold; border-left: 4px solid #e74c3c;">
            ${error}
        </div>
    </c:if>

    <form action="<c:url value='/checkout'/>" method="post" style="display: flex; gap: 30px; flex-wrap: wrap;">
        
        <!-- Cột trái: Thông tin nhận hàng & phương thức thanh toán -->
        <div style="flex: 1 1 550px;">
            <h3 style="color: #34495e; margin-top: 0; margin-bottom: 15px; border-bottom: 1px solid #ecf0f1; padding-bottom: 8px;">
                👤 Thông tin người nhận
            </h3>

            <div style="margin-bottom: 15px;">
                <label style="display: block; font-weight: bold; margin-bottom: 6px; color: #2c3e50;">Họ và tên người nhận <span style="color: red;">*</span></label>
                <input type="text" name="fullname" value="${fullname}" required placeholder="Ví dụ: Nguyễn Văn A"
                       style="width: 100%; box-sizing: border-box; padding: 10px 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 14px;">
            </div>

            <div style="display: flex; gap: 15px; margin-bottom: 15px;">
                <div style="flex: 1;">
                    <label style="display: block; font-weight: bold; margin-bottom: 6px; color: #2c3e50;">Số điện thoại <span style="color: red;">*</span></label>
                    <input type="tel" name="phone" value="${phone}" required placeholder="0901234567"
                           style="width: 100%; box-sizing: border-box; padding: 10px 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 14px;">
                </div>
                <div style="flex: 1;">
                    <label style="display: block; font-weight: bold; margin-bottom: 6px; color: #2c3e50;">Email nhận thông báo</label>
                    <input type="email" name="email" value="${email}" placeholder="email@domain.com"
                           style="width: 100%; box-sizing: border-box; padding: 10px 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 14px;">
                </div>
            </div>

            <div style="margin-bottom: 15px;">
                <label style="display: block; font-weight: bold; margin-bottom: 6px; color: #2c3e50;">Địa chỉ giao hàng chi tiết <span style="color: red;">*</span></label>
                <textarea name="address" required rows="3" placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố..."
                          style="width: 100%; box-sizing: border-box; padding: 10px 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 14px; font-family: inherit;">${address}</textarea>
            </div>

            <div style="margin-bottom: 25px;">
                <label style="display: block; font-weight: bold; margin-bottom: 6px; color: #2c3e50;">Ghi chú đơn hàng (Tùy chọn)</label>
                <input type="text" name="note" value="${note}" placeholder="Ghi chú cho người giao hàng (ví dụ: giao giờ hành chính)"
                       style="width: 100%; box-sizing: border-box; padding: 10px 12px; border: 1px solid #bdc3c7; border-radius: 6px; font-size: 14px;">
            </div>

            <h3 style="color: #34495e; margin-top: 20px; margin-bottom: 15px; border-bottom: 1px solid #ecf0f1; padding-bottom: 8px;">
                💳 Phương thức thanh toán
            </h3>

            <div style="border: 2px solid #27ae60; background: #eafaf1; border-radius: 8px; padding: 15px; display: flex; align-items: flex-start; gap: 12px;">
                <input type="radio" name="paymentMethod" value="COD" checked id="codRadio" style="margin-top: 4px; accent-color: #27ae60; transform: scale(1.2);">
                <label for="codRadio" style="cursor: pointer;">
                    <div style="font-weight: bold; font-size: 16px; color: #1e8449;">
                        🚚 Thanh toán khi nhận hàng (COD - Cash On Delivery)
                    </div>
                    <div style="color: #555; font-size: 13px; margin-top: 4px; line-height: 1.4;">
                        Quý khách sẽ thanh toán tiền mặt trực tiếp cho nhân viên giao hàng (Shipper) khi nhận được kiện hàng. Hoàn toàn yên tâm, không cần chuyển khoản trước!
                    </div>
                </label>
            </div>
        </div>

        <!-- Cột phải: Tóm tắt đơn hàng -->
        <div style="flex: 1 1 380px; background: #f8f9fa; border: 1px solid #e2e8f0; border-radius: 8px; padding: 20px; height: fit-content;">
            <h3 style="color: #2c3e50; margin-top: 0; margin-bottom: 15px; border-bottom: 1px solid #cbd5e1; padding-bottom: 10px;">
                🛒 Tóm tắt đơn hàng
            </h3>

            <div style="max-height: 250px; overflow-y: auto; margin-bottom: 15px;">
                <c:forEach var="item" items="${sessionScope.cart}">
                    <div style="display: flex; gap: 12px; margin-bottom: 12px; padding-bottom: 12px; border-bottom: 1px dashed #cbd5e1;">
                        <img src="${item.video.poster}" alt="${item.video.title}" onerror="this.src='https://placehold.co/120x80?text=Video';" style="width: 60px; height: 40px; object-fit: cover; border-radius: 4px;">
                        <div style="flex: 1;">
                            <div style="font-weight: bold; font-size: 13px; color: #2c3e50; line-height: 1.3;">${item.video.title}</div>
                            <div style="font-size: 12px; color: #7f8c8d; margin-top: 2px;">
                                Số lượng: <strong>${item.quantity}</strong> &times; 50.000 đ
                            </div>
                        </div>
                        <div style="font-weight: bold; color: #2980b9; font-size: 14px;">
                            ${item.quantity * 50000} đ
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div style="line-height: 1.8; margin-bottom: 20px; font-size: 14px;">
                <div style="display: flex; justify-content: space-between; color: #555;">
                    <span>Tạm tính:</span>
                    <span>${totalAmount} đ</span>
                </div>
                <div style="display: flex; justify-content: space-between; color: #555;">
                    <span>Phí giao hàng COD:</span>
                    <span style="color: #27ae60; font-weight: bold;">Miễn phí</span>
                </div>
                <div style="display: flex; justify-content: space-between; border-top: 2px solid #cbd5e1; padding-top: 10px; margin-top: 8px; font-size: 18px; font-weight: bold; color: #e74c3c;">
                    <span>Tổng thanh toán COD:</span>
                    <span>${totalAmount} đ</span>
                </div>
                <div style="font-size: 12px; color: #7f8c8d; text-align: right; margin-top: 3px;">(Thanh toán bằng tiền mặt khi nhận hàng)</div>
            </div>

            <button type="submit" style="width: 100%; background: #27ae60; color: white; border: none; padding: 14px 20px; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; box-shadow: 0 4px 6px rgba(39,174,96,0.3); transition: background 0.2s;">
                ✅ XÁC NHẬN ĐẶT HÀNG (COD)
            </button>
        </div>

    </form>
</div>

<jsp:include page="/decorators/footer.jsp" />
