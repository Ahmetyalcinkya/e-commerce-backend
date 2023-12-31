package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.requestDto.ProductRequest;
import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();
    ProductResponse getProductByID(long id);
    ProductResponse saveProduct(Product product); // Product request
    ProductResponse updateProduct(long id, Product product);
    ProductResponse deleteProduct(long id);
    List<ProductResponse> sortHighestToLowest();
    List<ProductResponse> sortLowestToHighest();
    List<ProductResponse> sortBestToWorst();
    List<ProductResponse> sortWorstToBest();
    List<ProductResponse> searchByName(String name);
    List<ProductResponse> searchAndSortHighest(String name);
    List<ProductResponse> searchAndSortLowest(String name);
    List<ProductResponse> searchAndSortBest(String name);
    List<ProductResponse> searchAndSortWorst(String name);

    // PRODUCT LIMIT
}
