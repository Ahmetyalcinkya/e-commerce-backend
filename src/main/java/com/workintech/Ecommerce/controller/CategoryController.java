package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.CategoryResponse;
import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Category;
import com.workintech.Ecommerce.entity.Product;
import com.workintech.Ecommerce.service.CategoryService;
import com.workintech.Ecommerce.service.ProductService;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/addProduct/{categoryID}")
    public ProductResponse addProduct(@RequestBody Product product, @PathVariable long categoryID){
        Category category = categoryService.getCategoryByID(categoryID);
        product.setCategory(category);
        category.addProduct(product);
        productService.saveProduct(product);
        return Converter.findProduct(product);
    }
}
