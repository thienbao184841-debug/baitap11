package com.videoshare.dao;

import java.util.List;

/**
 * Interface DAO tổng quát chứa các hàm CRUD cơ bản dùng chung cho mọi bảng.
 * T: Loại đối tượng (Model/Entity)
 * K: Kiểu dữ liệu của khóa chính (Primary Key)
 */
public interface IBaseDao_24110162<T, K> {
    void insert(T entity);
    void update(T entity);
    void delete(K id);
    T findById(K id);
    List<T> findAll();
}
