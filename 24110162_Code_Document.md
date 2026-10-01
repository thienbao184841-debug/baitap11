# BÀI THI LẬP TRÌNH WEB - MÃ ĐỀ 04
**Sinh viên:** Trần Thiên Bảo
**MSSV:** 24110162

---
http://localhost:8080/VideoShareProject/home

## CÂU 1: Cấu trúc Project, Maven, MVC và Decorator (Header/Footer)
*(Bạn hãy dán ảnh chụp màn hình giao diện có Header và Footer tại đây)*

### 1. File cấu hình pom.xml (Cấu hình Maven, Jakarta EE, JSTL, SQL Server)
```xml
<!-- Đã cấu hình đẩy đủ theo chuẩn Jakarta EE 10 -->
<!-- (Copy nội dung file pom.xml của bạn vào đây) -->
```

### 2. Giao diện Header (header.jsp)
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>VideoShare</title>
</head>
<body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #ecf0f1; display: flex; flex-direction: column; min-height: 100vh;">
    <header style="background-color: #2c3e50; color: white; padding: 15px;">
        <nav style="display: flex; justify-content: space-between; align-items: center;">
            <div style="font-size: 20px; font-weight: bold;">
                <a href="<c:url value='/home' />" style="color: white; text-decoration: none;">VideoShare</a>
            </div>
            <div>
                <a href="<c:url value='/home' />" style="color: white; margin-right: 15px; text-decoration: none;">Trang Chủ</a>
                <a href="<c:url value='/videos' />" style="color: white; margin-right: 15px; text-decoration: none;">Sản phẩm</a>
                
                <c:choose>
                    <c:when test="${sessionScope.account != null}">
                        <span style="margin-right: 15px;">Chào, ${sessionScope.account.fullname}</span>
                        <c:if test="${sessionScope.account.admin}">
                            <a href="<c:url value='/admin/home' />" style="color: #f1c40f; margin-right: 15px; text-decoration: none;">Trang quản trị</a>
                        </c:if>
                        <a href="<c:url value='/logout' />" style="color: #e74c3c; text-decoration: none;">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/login' />" style="color: white; text-decoration: none;">Đăng nhập</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>
    <main style="padding: 20px; min-height: 500px; background-color: #ecf0f1;">
```

### 3. Giao diện Footer (footer.jsp)
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    </main>
    <footer style="background-color: #34495e; color: white; text-align: center; padding: 20px;">
        <p>Họ tên: Trần Thiên Bảo - MSSV: 24110162 - Mã đề: 04</p>
    </footer>
</body>
</html>
```

---

## CÂU 2: Đăng ký (có OTP qua mail), Đăng nhập, Đăng xuất, Phân quyền
*(Bạn hãy dán ảnh màn hình trang Đăng ký, trang xác nhận OTP và trang Đăng nhập tại đây)*

### 1. Tầng Tiện ích gửi Mail (EmailUtil_24110162.java)
```java
public class EmailUtil_24110162 {
    private static final String FROM_EMAIL = "thienbao.184841@gmail.com";
    private static final String APP_PASSWORD = "bogkapkibjmbzcik";

    public static boolean sendOTP(String toEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD.replace(" ", ""));
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP Xác Nhận Đăng Ký Tài Khoản VideoShare", "UTF-8");
            message.setText("Mã OTP kích hoạt tài khoản của bạn là: " + otp, "UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
```

### 2. Tầng Controller (RegisterServlet_24110162.java)
```java
@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        
        if (userService.checkExistUsername(username) || userService.checkExistEmail(email)) {
            req.setAttribute("error", "Tên đăng nhập hoặc Email đã tồn tại!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        User_24110162 pendingUser = new User_24110162(username, req.getParameter("password"), req.getParameter("phone"), req.getParameter("fullname"), email, false, true, "");
        
        HttpSession session = req.getSession();
        session.setAttribute("pendingUser", pendingUser);
        session.setAttribute("otp", otp);

        if (EmailUtil_24110162.sendOTP(email, otp)) {
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } else {
            req.setAttribute("error", "Lỗi gửi mail OTP.");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
        }
    }
}
```

### 3. Tầng Controller (LoginServlet_24110162.java)
```java
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110162 user = userService.login(req.getParameter("username"), req.getParameter("password"));

        if (user != null) {
            req.getSession().setAttribute("account", user);
            if (user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        }
    }
}
```

---

## CÂU 3: Quản trị dữ liệu bảng Users (CRUD + Phân trang 6 items)
*(Bạn hãy dán ảnh chụp màn hình Danh sách User có phân trang và Form thêm/sửa User)*

### 1. Tầng DAO (UserDaoImpl_24110162.java)
```java
    @Override
    public List<User_24110162> findUsersByPage(int offset, int limit) {
        List<User_24110162> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY Username OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapUser(rs));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
```

### 2. Tầng Controller (AdminUserController_24110162.java)
```java
@WebServlet(urlPatterns = {"/admin/users", "/admin/users/create", "/admin/users/update", "/admin/users/delete"})
public class AdminUserController_24110162 extends HttpServlet {
    private IUserService_24110162 userService = new UserServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("delete")) {
            userService.delete(req.getParameter("id"));
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else if (url.contains("update") || url.contains("create")) {
            if(url.contains("update")) req.setAttribute("user", userService.findById(req.getParameter("id")));
            req.getRequestDispatcher("/views/admin/user-form.jsp").forward(req, resp);
        } else {
            int page = 1;
            int limit = 6; // Đề bài yêu cầu phân trang 6 user/trang
            if (req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
            
            int totalUsers = userService.countAll();
            int totalPage = (int) Math.ceil((double) totalUsers / limit);
            
            req.setAttribute("userList", userService.findUsersByPage((page - 1) * limit, limit));
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPage", totalPage);
            req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
        }
    }
}
```

---

## CÂU 4: Trang chi tiết 1 Video
*(Bạn hãy dán ảnh chụp màn hình chi tiết 1 video)*

### 1. Tầng DAO lấy Video kèm số liệu (VideoDaoImpl_24110162.java)
```java
    @Override
    public Video_24110162 getVideoDetail(String videoId) {
        String sql = "SELECT v.*, c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                     "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId WHERE v.VideoId = ?";
        try (Connection conn = new DBConnect_24110162().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Video_24110162 video = mapVideo(rs);
                    video.setCategoryName(rs.getString("Categoryname"));
                    video.setShareCount(rs.getInt("ShareCount"));
                    video.setLikeCount(rs.getInt("LikeCount"));
                    return video;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
```

### 2. Tầng View (video-detail.jsp)
```jsp
<!-- Hiển thị các thông tin Share(10), Like(10) và Category Name -->
<p><strong>Mã video:</strong> ${video.videoId}</p>
<p><strong>Category name:</strong> ${video.categoryName}</p>
<p><strong>View:</strong> ${video.views}</p>
<div style="display: flex; gap: 15px; margin-bottom: 20px;">
    <span style="background: #3498db; color: white; padding: 8px 15px; border-radius: 5px;">Share(${video.shareCount})</span>
    <span style="background: #e74c3c; color: white; padding: 8px 15px; border-radius: 5px;">Like(${video.likeCount})</span>
</div>
```

---

## CÂU 5 & CÂU 6: Hiển thị Video theo Category (phân trang 3 video/trang) và Đếm số lượng
*(Bạn hãy dán ảnh chụp màn hình trang có cột Danh mục bên trái, lưới Video bên phải có phân trang)*

### 1. Tầng DAO đếm và lấy danh sách (VideoDaoImpl_24110162.java)
```java
    @Override
    public List<Video_24110162> findVideosByCategoryId(int categoryId, int offset, int limit) {
        List<Video_24110162> list = new ArrayList<>();
        String sql = "SELECT v.*, c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                     "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.CategoryId=? ORDER BY v.VideoId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        // ... thực thi truy vấn
        return list;
    }

    @Override
    public int countVideosByCategoryId(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Videos WHERE CategoryId=?";
        // ... thực thi trả về số đếm
        return count;
    }
```

### 2. Tầng Controller (CategoryVideoController_24110162.java)
```java
@WebServlet(urlPatterns = {"/videos"})
public class CategoryVideoController_24110162 extends HttpServlet {
    private ICategoryService_24110162 categoryService = new CategoryServiceImpl_24110162();
    private IVideoService_24110162 videoService = new VideoServiceImpl_24110162();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category_24110162> categories = categoryService.findAll();
        int categoryId = req.getParameter("categoryId") != null ? Integer.parseInt(req.getParameter("categoryId")) : categories.get(0).getCategoryId();

        int limit = 3; // Đề bài yêu cầu 3 video/trang
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
        
        int totalVideos = videoService.countVideosByCategoryId(categoryId); // Đếm số lượng video (Câu 6)
        
        req.setAttribute("currentCategory", categoryService.findById(categoryId));
        req.setAttribute("videoList", videoService.findVideosByCategoryId(categoryId, (page - 1) * limit, limit));
        req.setAttribute("totalVideos", totalVideos); // Gửi tổng số đếm ra giao diện
        req.setAttribute("totalPage", (int) Math.ceil((double) totalVideos / limit));
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/views/web/videos.jsp").forward(req, resp);
    }
}
```

### 3. Tầng View (videos.jsp)
```jsp
<!-- Cột đếm số lượng (Câu 6) -->
<h2 style="color: #e67e22;">${currentCategory.categoryname} (${totalVideos})</h2>

<!-- Phân trang 3 video -->
<c:if test="${totalPage > 1}">
    <c:forEach begin="1" end="${totalPage}" var="i">
        <a href="<c:url value='/videos?categoryId=${currentCategory.categoryId}&page=${i}'/>">${i}</a>
    </c:forEach>
</c:if>
```
