package com.videoshare.dao;

import com.videoshare.models.User_24110162;
import java.util.List;

public interface IUserDao_24110162 extends IBaseDao_24110162<User_24110162, String> {
    // Khai báo thêm các hàm đặc thù riêng cho User_24110162 (ngoài 5 hàm CRUD cơ bản đã thừa kế)
    
    // Hàm phục vụ đăng nhập
    User_24110162 findByUsernameAndPassword(String username, String password);
    
    // Hàm kiểm tra email tồn tại (phục vụ đăng ký)
    boolean checkExistEmail(String email);
    
    // Hàm phục vụ Câu 3: Phân trang (6 user / 1 trang)
    List<User_24110162> findUsersByPage(int offset, int limit);
    
    // Đếm tổng số user để tính số trang
    int countAll();
}
