package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.CategoryResponse;
import com.workintech.Ecommerce.repository.CategoryRepository;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return Converter.findCategories(categoryRepository.findAll());
    }
}
