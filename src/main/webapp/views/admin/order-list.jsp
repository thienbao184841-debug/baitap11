<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1200px; margin: 0 auto; background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.08);">
    
    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #3498db; padding-bottom: 15px; margin-bottom: 20px;">
        <h2 style="color: #2c3e50; margin: 0;">📦 Quản lý Đơn hàng (Admin)</h2>
        <div>
            <a href="<c:url value='/admin/users'/>" style="color: #3498db; margin-right: 15px; text-decoration: none; font-weight: bold;">Quản lý User</a>
            <a href="<c:url value='/admin/categories'/>" style="color: #3498db; text-decoration: none; font-weight: bold;">Quản lý Category</a>
        </div>
    </div>

    <!-- Thanh Tabs lọc theo 8 trạng thái cho Admin -->
    <div style="display: flex; gap: 8px; flex-wrap: wrap; margin-bottom: 25px; background: #f8fafc; padding: 12px; border-radius: 8px; border: 1px solid #e2e8f0;">
        <a href="<c:url value='/admin/orders?status=Tất cả'/>" 
           style="padding: 6px 12px; border-radius: 20px; text-decoration: none; font-size: 13px; font-weight: bold;
           ${currentStatus == 'Tất cả' ? 'background: #2563eb; color: white;' : 'background: white; color: #475569; border: 1px solid #cbd5e1;'}">
            Tất cả (${statusCounts['Tất cả']})
        </a>

        <c:forEach var="st" items="${allStatuses}">
            <a href="<c:url value='/admin/orders?status=${st}'/>" 
               style="padding: 6px 12px; border-radius: 20px; text-decoration: none; font-size: 13px; font-weight: bold;
               ${currentStatus == st ? 'background: #2563eb; color: white;' : 'background: white; color: #475569; border: 1px solid #cbd5e1;'}">
                ${st} (${statusCounts[st] != null ? statusCounts[st] : 0})
            </a>
        </c:forEach>
    </div>

    <!-- Bảng danh sách đơn hàng -->
    <table style="width: 100%; border-collapse: collapse; font-size: 13px; margin-bottom: 20px;">
        <thead>
            <tr style="background: #334155; color: white;">
                <th style="padding: 10px; border: 1px solid #cbd5e1; width: 120px;">Mã đơn</th>
                <th style="padding: 10px; border: 1px solid #cbd5e1;">Khách hàng / SĐT</th>
                <th style="padding: 10px; border: 1px solid #cbd5e1;">Địa chỉ</th>
                <th style="padding: 10px; border: 1px solid #cbd5e1; text-align: right; width: 100px;">Tổng tiền</th>
                <th style="padding: 10px; border: 1px solid #cbd5e1; text-align: center; width: 140px;">Trạng thái hiện tại</th>
                <th style="padding: 10px; border: 1px solid #cbd5e1; text-align: center; width: 220px;">Cập nhật trạng thái</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ord" items="${orders}">
                <tr style="border-bottom: 1px solid #e2e8f0;">
                    <td style="padding: 10px; border: 1px solid #e2e8f0; font-weight: bold; color: #2563eb;">
                        ${ord.orderId}
                        <div style="font-size: 11px; color: #64748b; font-weight: normal;">${ord.orderDate}</div>
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0;">
                        <strong>${ord.fullname}</strong> (${ord.username})
                        <div style="color: #64748b;">${ord.phone}</div>
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0;">
                        ${ord.address}
                        <c:if test="${not empty ord.note}">
                            <div style="color: #f59e0b; font-style: italic;">Note: ${ord.note}</div>
                        </c:if>
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0; text-align: right; font-weight: bold; color: #e11d48;">
                        ${ord.totalAmount} đ
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0; text-align: center;">
                        <span style="display: inline-block; padding: 4px 8px; border-radius: 12px; font-size: 11px; font-weight: bold; background: #e0f2fe; color: #0369a1;">
                            ${ord.status}
                        </span>
                    </td>
                    <td style="padding: 10px; border: 1px solid #e2e8f0; text-align: center;">
                        <form action="<c:url value='/admin/orders/update-status'/>" method="post" style="display: flex; gap: 5px; justify-content: center; margin: 0;">
                            <input type="hidden" name="orderId" value="${ord.orderId}">
                            <select name="newStatus" style="padding: 4px 8px; border-radius: 4px; border: 1px solid #cbd5e1; font-size: 12px;">
                                <c:forEach var="st" items="${allStatuses}">
                                    <option value="${st}" ${ord.status == st ? 'selected' : ''}>${st}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" style="background: #10b981; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; font-size: 12px; font-weight: bold;">
                                Lưu
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty orders}">
        <p style="text-align: center; color: #94a3b8; padding: 30px 0;">Không có đơn hàng nào trong trạng thái "${currentStatus}".</p>
    </c:if>

</div>

<jsp:include page="/decorators/footer.jsp" />
