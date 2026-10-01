<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/decorators/header.jsp" />

<div style="max-width: 1050px; margin: 0 auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.08);">
    <h2 style="color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; margin-top: 0;">
        &#128722; Gi&#7887; h&agrave;ng c&#7911;a b&#7841;n (T&#7889;i &#273;a 10 c&aacute;i/s&#7843;n ph&#7849;m)
    </h2>
    
    <c:if test="${empty cart}">
        <div style="text-align: center; padding: 50px 20px;">
            <p style="color: #7f8c8d; font-size: 16px; margin-bottom: 20px;">Gi&#7887; h&agrave;ng c&#7911;a b&#7841;n &#273;ang tr&#7889;ng.</p>
            <a href="<c:url value='/videos'/>" style="background: #3498db; color: white; padding: 12px 25px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 15px;">
                &#128722; Kh&aacute;m ph&aacute; s&#7843;n ph&#7849;m ngay
            </a>
        </div>
    </c:if>

    <c:if test="${not empty cart}">
        <c:set var="totalCart" value="0" />
        <table style="width: 100%; border-collapse: collapse; text-align: left; margin-bottom: 20px; font-size: 14px;">
            <thead>
                <tr style="background: #34495e; color: white;">
                    <th style="padding: 12px; border: 1px solid #ddd;">S&#7843;n ph&#7849;m</th>
                    <th style="padding: 12px; border: 1px solid #ddd; text-align: center;">H&igrave;nh &#7843;nh</th>
                    <th style="padding: 12px; border: 1px solid #ddd; text-align: center; width: 100px;">&#272;&#417;n gi&aacute;</th>
                    <th style="padding: 12px; border: 1px solid #ddd; text-align: center; width: 140px;">S&#7889; l&#432;&#7883;ng</th>
                    <th style="padding: 12px; border: 1px solid #ddd; text-align: right; width: 120px;">Th&agrave;nh ti&#7873;n</th>
                    <th style="padding: 12px; border: 1px solid #ddd; text-align: center; width: 90px;">H&agrave;nh &#273;&#7897;ng</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${cart}">
                    <c:set var="sub" value="${item.quantity * 50000}" />
                    <c:set var="totalCart" value="${totalCart + sub}" />
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td style="padding: 12px; border: 1px solid #ddd; font-weight: bold; color: #2980b9;">
                            <a href="<c:url value='/video?id=${item.video.videoId}'/>" style="text-decoration: none; color: #2980b9;">${item.video.title}</a>
                        </td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">
                            <img src="${item.video.poster}" alt="${item.video.title}" style="width: 100px; border-radius: 4px; aspect-ratio: 16/9; object-fit: cover;">
                        </td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align: center; color: #555;">
                            50.000 &#273;
                        </td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">
                            <form action="<c:url value='/cart/update'/>" method="post" style="display: flex; gap: 5px; justify-content: center; align-items: center; margin: 0;">
                                <input type="hidden" name="videoId" value="${item.video.videoId}">
                                <input type="number" name="quantity" value="${item.quantity}" min="1" max="10" style="width: 50px; padding: 5px; text-align: center; border: 1px solid #bdc3c7; border-radius: 4px;">
                                <button type="submit" style="background: #f39c12; color: white; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer; font-weight: bold;">S&#7917;a</button>
                            </form>
                        </td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align: right; font-weight: bold; color: #27ae60;">
                            ${sub} &#273;
                        </td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align: center;">
                            <a href="<c:url value='/cart/remove?videoId=${item.video.videoId}'/>" style="background: #e74c3c; color: white; padding: 6px 12px; text-decoration: none; border-radius: 4px; font-size: 13px;" onclick="return confirm('B&#7841;n c&oacute; ch&#7855;c ch&#7855;n mu&#7889;n x&oacute;a kh&#7887;i gi&#7887; h&agrave;ng?');">X&oacute;a</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr style="background: #f8fafc; font-weight: bold;">
                    <td colspan="4" style="padding: 15px; border: 1px solid #ddd; text-align: right; font-size: 16px; color: #2c3e50;">
                        T&#7893;ng gi&aacute; tr&#7883; gi&#7887; h&agrave;ng:
                    </td>
                    <td style="padding: 15px; border: 1px solid #ddd; text-align: right; font-size: 18px; color: #e74c3c;">
                        ${totalCart} &#273;
                    </td>
                    <td style="border: 1px solid #ddd;"></td>
                </tr>
            </tfoot>
        </table>

        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">
            <a href="<c:url value='/videos'/>" style="background: #95a5a6; color: white; padding: 12px 20px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 14px;">
                &larr; Ti&#7871;p t&#7909;c xem Video
            </a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.account}">
                    <a href="<c:url value='/checkout'/>" style="background: #27ae60; color: white; padding: 12px 25px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px; box-shadow: 0 4px 6px rgba(39,174,96,0.3); display: flex; align-items: center; gap: 8px;">
                        🚚 Tiến hành đặt hàng COD &rarr;
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/checkout'/>" style="background: #e67e22; color: white; padding: 12px 25px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px; box-shadow: 0 4px 6px rgba(230,126,34,0.3); display: flex; align-items: center; gap: 8px;" title="Vui lòng đăng nhập để tiến hành đặt hàng">
                        🔒 Đăng nhập để đặt hàng COD &rarr;
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</div>

<jsp:include page="/decorators/footer.jsp" />
