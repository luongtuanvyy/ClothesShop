package com.asm.service.serviceImpl;

import com.asm.entity.Categories;
import com.asm.repository.CategoryRepository;
import com.asm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<String> getCategoryName() {
        List<String> categorieNames = new ArrayList<>();
        for (Categories category : categoryRepository.findAll()) {
            categorieNames.add(category.getCategory_name());
        }
        return categorieNames;
    }
}
