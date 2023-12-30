package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
}
