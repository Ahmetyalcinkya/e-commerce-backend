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

    @GetMapping("/name/{name}") //TODO nullpointer must be fixed
    public ProductResponse getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @GetMapping("/id/{id}")
    public ProductResponse getProductByID(@PathVariable long id){
        return productService.getProductByID(id);
    }

    @PostMapping("/") //TODO Category choice should be fixed
    public ProductResponse saveProduct(Product product){
        return productService.saveProduct(product);
    }

    //TODO Update will be added

    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
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
        //TODO Throw exception
        return null;
    }
}
