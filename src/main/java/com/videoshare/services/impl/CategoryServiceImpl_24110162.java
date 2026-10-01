package com.videoshare.services.impl;

import com.videoshare.dao.ICategoryDao_24110162;
import com.videoshare.dao.impl.CategoryDaoImpl_24110162;
import com.videoshare.models.Category_24110162;
import com.videoshare.services.ICategoryService_24110162;
import java.util.List;

public class CategoryServiceImpl_24110162 implements ICategoryService_24110162 {
    private ICategoryDao_24110162 categoryDao = new CategoryDaoImpl_24110162();

    @Override
    public void insert(Category_24110162 category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category_24110162 category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryDao.delete(id);
    }

    @Override
    public Category_24110162 findById(Integer id) {
        return categoryDao.findById(id);
    }

    @Override
    public List<Category_24110162> findAll() {
        return categoryDao.findAll();
    }
}
