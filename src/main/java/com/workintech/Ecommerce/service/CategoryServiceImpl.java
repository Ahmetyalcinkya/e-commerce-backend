package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.CategoryResponse;
import com.workintech.Ecommerce.entity.Category;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.repository.CategoryRepository;
import com.workintech.Ecommerce.util.Constants;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Category getCategoryByID(long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()){
            return categoryOptional.get();
        }
        throw new ECommerceException(Constants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
