package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Product;
import com.workintech.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public ProductResponse getProductByID(@PathVariable long id){
        return productService.getProductByID(id);
    }
    @PostMapping("/")
    public ProductResponse saveProduct(Product product){
        return productService.saveProduct(product);
    }
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable long id, Product product){
        return productService.updateProduct(id, product);
    }
    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/")
    @ResponseBody
    public List<ProductResponse> filterProducts(@RequestParam(name = "filter", required = false) String name,
                                                @RequestParam(name = "sort", required = false, defaultValue = "default") String sort){
        if(name == null && name.isEmpty()){
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
        //TODO Throw exception
        return null;
    }
}
