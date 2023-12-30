package com.workintech.Ecommerce.util;

import com.workintech.Ecommerce.dto.responseDto.CategoryResponse;
import com.workintech.Ecommerce.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<CategoryResponse> findCategories(List<Category> categories){
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for(Category category: categories){
            categoryResponses.add(new CategoryResponse(category.getId(),category.getCode(),category.getGender(),category.getRating(),
                    category.getTitle(),category.getImage()));
        }
        return categoryResponses;
    }
}
