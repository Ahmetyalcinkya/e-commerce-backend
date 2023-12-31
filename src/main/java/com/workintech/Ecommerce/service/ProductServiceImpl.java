package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.requestDto.ProductRequest;
import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Product;
import com.workintech.Ecommerce.repository.ProductRepository;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductResponse> getAllProducts() {
        return Converter.findProducts(productRepository.findAll());
    }

    @Override
    public ProductResponse getProductByID(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            return Converter.findProduct(productOptional.get());
        }
        //TODO Throw exception -> Ürün bulunamadı
        return null;
    }

    @Override
    public ProductResponse saveProduct(ProductRequest product) {
            if(product.name().trim().equalsIgnoreCase(productRepository.findByProductName(product.name().trim()))){
                //TODO Throw exception -> Ürün zaten var
                return null;
            }
            //TODO save doesn't work IMPORTANT!!!
//            productRepository.save(product);
//            return Converter.findProduct();
            return null;
    }

    //TODO update method will be added if save works properly

    @Override
    public ProductResponse deleteProduct(long id) {
        ProductResponse product = getProductByID(id);
        if(product != null){
        productRepository.deleteById(id);
        return product;
        }
        //TODO Throw exception -> Ürün bulunamadı
        return null;
    }

    @Override
    public List<ProductResponse> sortHighestToLowest(/*String name*/) {
        return Converter.findProducts(productRepository.sortHighestToLowest());
    }

    @Override
    public List<ProductResponse> sortLowestToHighest(/*String name*/) {
        return Converter.findProducts(productRepository.sortLowestToHighest());
    }

    @Override
    public List<ProductResponse> sortBestToWorst(/*String name*/) {
        return Converter.findProducts(productRepository.sortBestToWorst());
    }

    @Override
    public List<ProductResponse> sortWorstToBest(/*String name*/) {
        return Converter.findProducts(productRepository.sortWorstToBest());
    }

    @Override
    public List<ProductResponse> searchByName(String name) {
        return Converter.findProducts(productRepository.searchByName(name));
    }
}
