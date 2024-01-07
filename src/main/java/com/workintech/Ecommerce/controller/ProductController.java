package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.requestDto.ProductRequest;
import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Category;
import com.workintech.Ecommerce.entity.Product;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.service.CategoryService;
import com.workintech.Ecommerce.service.ProductService;
import com.workintech.Ecommerce.util.Constants;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/name/{name}")
    public ProductResponse getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @GetMapping("/id/{id}")
    public ProductResponse getProductByID(@PathVariable long id){
        return Converter.findProduct(productService.getProductByID(id));
    }

    @PostMapping("/")
    public ProductResponse saveProduct(@RequestBody ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.desc());
        product.setPrice(productRequest.price());
        product.setRating(productRequest.rating());
        product.setSellCount(productRequest.sellCount());
        product.setStock(productRequest.stock());
        product.setImages(productRequest.images());
        Category category = categoryService.getCategoryByID(productRequest.categoryID());
        product.setCategory(category);
        category.addProduct(product);
        return Converter.findProduct(productService.saveProduct(product));
    }

    @DeleteMapping("/id/{id}")
    public ProductResponse deleteProduct(@PathVariable long id){
        return Converter.findProduct(productService.deleteProduct(id));
    }

    @GetMapping("/filter")
    @ResponseBody                   //TODO Category will be added in request param !!
    public List<ProductResponse> filterProducts(@RequestParam(name = "filter", required = false) String name,
                                                @RequestParam(name = "sort", required = false, defaultValue = "default") String sort){
        if(name == null){
            switch (sort){
                case "rating:desc":
                    return productService.sortBestToWorst();
                case "rating:asc":
                    return productService.sortWorstToBest();
                case "price:desc":
                    return productService.sortHighestToLowest();
                case "price:asc":
                    return productService.sortLowestToHighest();
            }
        }else {
            return switch (sort) {
                case "rating:desc" -> productService.searchAndSortBest(name);
                case "rating:asc" -> productService.searchAndSortWorst(name);
                case "price:desc" -> productService.searchAndSortHighest(name);
                case "price:asc" -> productService.searchAndSortLowest(name);
                default -> productService.searchByName(name);
            };
        }
        throw new ECommerceException(Constants.FILTER_NOT_EXIST, HttpStatus.NOT_FOUND);
    }
}
