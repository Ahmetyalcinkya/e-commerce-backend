package com.workintech.Ecommerce.util;

import com.workintech.Ecommerce.dto.responseDto.CategoryResponse;
import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Category;
import com.workintech.Ecommerce.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    // CATEGORY
    public static List<CategoryResponse> findCategories(List<Category> categories){
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for(Category category: categories){
            categoryResponses.add(new CategoryResponse(category.getId(),category.getCode(),category.getGender(),category.getRating(),
                    category.getTitle(),category.getImage()));
        }
        return categoryResponses;
    }
    public static CategoryResponse findCategory(Category category){
        return new CategoryResponse(category.getId(), category.getCode(), category.getGender(),category.getRating(),
                category.getTitle(), category.getImage());
    }

    // PRODUCTS
    public static List<ProductResponse> findProducts(List<Product> products){
        List<ProductResponse> productResponses = new ArrayList<>();

        for(Product product: products){
            productResponses.add(new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),
                    product.getRating(),product.getSellCount(),product.getStock(),product.getImages(),product.getCategory().getId()));
        }
        return productResponses;
    }
    public static ProductResponse findProduct(Product product){
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),
                product.getRating(),product.getSellCount(),product.getStock(),product.getImages(),product.getCategory().getId());
    }
}
