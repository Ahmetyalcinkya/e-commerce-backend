package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Product;

import java.util.List;

public interface ProductService {

    ProductResponse getProductByName(String name);
    List<ProductResponse> getAllProducts();
    Product getProductByID(long id);
    Product saveProduct(Product product); // Product request
    Product deleteProduct(long id);
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
