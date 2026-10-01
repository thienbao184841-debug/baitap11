package com.videoshare.services.impl;

import com.videoshare.dao.IUserDao_24110162;
import com.videoshare.dao.impl.UserDaoImpl_24110162;
import com.videoshare.models.User_24110162;
import com.videoshare.services.IUserService_24110162;

public class UserServiceImpl_24110162 implements IUserService_24110162 {
    
    // Khởi tạo UserDao để tương tác với Database
    private IUserDao_24110162 userDao = new UserDaoImpl_24110162();

    @Override
    public User_24110162 login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean register(User_24110162 user) {
        // Kiểm tra xem username hoặc email đã tồn tại trong database chưa
        if (checkExistUsername(user.getUsername()) || checkExistEmail(user.getEmail())) {
            return false;
        }
        // Thêm user vào database
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        User_24110162 user = userDao.findById(username);
        return user != null;
    }

    @Override
    public void insert(User_24110162 user) {
        userDao.insert(user);
    }

    @Override
    public void update(User_24110162 user) {
        userDao.update(user);
    }

    @Override
    public void delete(String username) {
        userDao.delete(username);
    }

    @Override
    public User_24110162 findById(String username) {
        return userDao.findById(username);
    }

    @Override
    public java.util.List<User_24110162> findAll() {
        return userDao.findAll();
    }

    @Override
    public java.util.List<User_24110162> findUsersByPage(int offset, int limit) {
        return userDao.findUsersByPage(offset, limit);
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }
}
