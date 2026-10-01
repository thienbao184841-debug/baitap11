package com.videoshare.services;

import com.videoshare.models.User_24110162;

public interface IUserService_24110162 {
    // Hàm xử lý đăng nhập
    User_24110162 login(String username, String password);
    
    // Hàm xử lý đăng ký
    boolean register(User_24110162 user);
    
    // Kiểm tra email đã tồn tại hay chưa
    boolean checkExistEmail(String email);
    
    // Kiểm tra username đã tồn tại hay chưa
    boolean checkExistUsername(String username);
    
    // Các hàm CRUD phục vụ Câu 3
    void insert(User_24110162 user);
    void update(User_24110162 user);
    void delete(String username);
    User_24110162 findById(String username);
    java.util.List<User_24110162> findAll();
    java.util.List<User_24110162> findUsersByPage(int offset, int limit);
    int countAll();
}
