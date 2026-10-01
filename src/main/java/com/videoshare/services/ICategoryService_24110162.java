package com.videoshare.services;

import com.videoshare.models.Category_24110162;
import java.util.List;

public interface ICategoryService_24110162 {
    void insert(Category_24110162 category);
    void update(Category_24110162 category);
    void delete(Integer id);
    Category_24110162 findById(Integer id);
    List<Category_24110162> findAll();
}
