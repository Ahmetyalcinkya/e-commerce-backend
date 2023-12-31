package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
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
//    @PostMapping("/")
//    public ProductResponse saveProduct(){}
//    @PutMapping("/")
//    public ProductResponse updateProduct(){}
    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
    }
    @GetMapping("/h")
    public List<ProductResponse> sortHighestToLowest(/*String name*/){
//        if(name != null && !name.isEmpty())
        return productService.sortHighestToLowest();
    }
    @GetMapping("/l")
    public List<ProductResponse> sortLowestToHighest(/*String name*/){
        return productService.sortLowestToHighest();
    }
    @GetMapping("/b")
    public List<ProductResponse> sortBestToWorst(/*String name*/){
        return productService.sortBestToWorst();
    }
    @GetMapping("/w")
    public List<ProductResponse> sortWorstToBest(/*String name*/){
        return productService.sortWorstToBest();
    }
    @GetMapping("/")
    @ResponseBody
    public List<ProductResponse> searchByName(@RequestParam(name = "filter", required = false) String name){
        return productService.searchByName(name);
    }
}
