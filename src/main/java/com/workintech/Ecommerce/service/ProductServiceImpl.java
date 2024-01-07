package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.requestDto.ProductRequest;
import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Category;
import com.workintech.Ecommerce.entity.Product;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.repository.ProductRepository;
import com.workintech.Ecommerce.util.Constants;
import com.workintech.Ecommerce.util.Converter;
import com.workintech.Ecommerce.util.validation.ECommerceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductByName(String name) {
        Optional<Product> optionalProduct = productRepository.findByProductName(name);
        if(optionalProduct.isPresent()){
            return Converter.findProduct(optionalProduct.get());
        }
        throw new ECommerceException(Constants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return Converter.findProducts(productRepository.findAll());
    }

    @Override
    public Product getProductByID(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            return productOptional.get();
        }
        throw new ECommerceException(Constants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    @Override
    public Product saveProduct(Product product) {
        Optional<Product> foundProduct = productRepository.findByProductName(product.getName());
        if(foundProduct.isPresent()){
           throw new ECommerceException(Constants.PRODUCT_IS_EXIST, HttpStatus.BAD_REQUEST);
        }
        ECommerceValidation.checkString(product.getName(),"Name",100);
        ECommerceValidation.checkString(product.getDescription(),"Description",250);
        ECommerceValidation.checkPrice(product.getPrice());
        ECommerceValidation.checkRating(product.getRating());
        ECommerceValidation.checkSellCountAndStock(product.getSellCount());
        ECommerceValidation.checkSellCountAndStock(product.getStock());
        ECommerceValidation.checkImageLength(product.getImages());
        ECommerceValidation.checkCategory(product.getCategory().getId());
        return productRepository.save(product);
    }
    @Override
    public Product deleteProduct(long id) {
        Product product = getProductByID(id);
        if(product != null){
        productRepository.deleteById(id);
        return product;
        }
        throw new ECommerceException(Constants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    public List<ProductResponse> sortHighestToLowest() {
        return Converter.findProducts(productRepository.sortHighestToLowest());
    }

    @Override
    public List<ProductResponse> sortLowestToHighest() {
        return Converter.findProducts(productRepository.sortLowestToHighest());
    }

    @Override
    public List<ProductResponse> sortBestToWorst() {
        return Converter.findProducts(productRepository.sortBestToWorst());
    }

    @Override
    public List<ProductResponse> sortWorstToBest() {
        return Converter.findProducts(productRepository.sortWorstToBest());
    }

    @Override
    public List<ProductResponse> searchByName(String name) {
        ECommerceValidation.checkString(name,"Filter",100);
        return Converter.findProducts(productRepository.searchByName(name));
    }

    @Override
    public List<ProductResponse> searchAndSortHighest(String name) {
        ECommerceValidation.checkString(name,"Filter",100);
        return Converter.findProducts(productRepository.searchAndSortHighest(name));
    }

    @Override
    public List<ProductResponse> searchAndSortLowest(String name) {
        ECommerceValidation.checkString(name,"Filter",100);
        return Converter.findProducts(productRepository.searchAndSortLowest(name));
    }

    @Override
    public List<ProductResponse> searchAndSortBest(String name) {
        ECommerceValidation.checkString(name,"Filter",100);
        return Converter.findProducts(productRepository.searchAndSortBest(name));
    }

    @Override
    public List<ProductResponse> searchAndSortWorst(String name) {
        ECommerceValidation.checkString(name,"Filter",100);
        return Converter.findProducts(productRepository.searchAndSortWorst(name));
    }

    @Override
    public List<ProductResponse> getCategoryProducts(int categoryID) {
        ECommerceValidation.checkCategory(categoryID);
        return Converter.findProducts(productRepository.getCategoryProducts(categoryID));
    }
}
