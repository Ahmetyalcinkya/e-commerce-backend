package com.workintech.Ecommerce.repository;

import com.workintech.Ecommerce.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void findByProductName() {
        Optional<Product> product = productRepository.findByProductName("holt");

        if (product.isPresent()){
            assertNotNull(product.get());
        }
    }
    @Test
    void findByProductNameFail() {
        Optional<Product> product = productRepository.findByProductName("ABCDE");

        if (product.isPresent()){
            assertNull(product.get());
        }
    }

    @Test
    void sortHighestToLowest() {
        List<Product> products = productRepository.sortHighestToLowest();
        Product product = products.get(1);
        assertEquals(699.99, product.getPrice());
    }
    @Test
    void sortHighestToLowestFail() {
        List<Product> products = productRepository.sortHighestToLowest();
        Product product = products.get(1);
        assertNotEquals(62, product.getPrice());
    }

    @Test
    void sortLowestToHighest() {
        List<Product> products = productRepository.sortLowestToHighest();
        Product product = products.get(0);
        assertEquals(62, product.getPrice());
    }
    @Test
    void sortLowestToHighestFail() {
        List<Product> products = productRepository.sortLowestToHighest();
        Product product = products.get(0);
        assertNotEquals(749.99, product.getPrice());
    }

    @Test
    void sortBestToWorst() {
        List<Product> products = productRepository.sortBestToWorst();
        Product product = products.get(0);
        assertEquals(5, product.getRating());
    }
    @Test
    void sortBestToWorstFail() {
        List<Product> products = productRepository.sortBestToWorst();
        Product product = products.get(0);
        assertNotEquals(3.1, product.getRating());
    }

    @Test
    void sortWorstToBest() {
        List<Product> products = productRepository.sortWorstToBest();
        Product product = products.get(0);
        assertEquals(2.6, product.getRating());
    }
    @Test
    void sortWorstToBestFail() {
        List<Product> products = productRepository.sortWorstToBest();
        Product product = products.get(0);
        assertNotEquals(4.2, product.getRating());
    }

    @Test
    void searchByName() {
        List<Product> products = productRepository.searchByName("Holt");
        Product product = products.get(0);
        assertNotNull(product);
    }
    @Test
    void searchByNameFail() {
        List<Product> products = productRepository.searchByName("ABCDE");
        assertEquals(0, products.size());
    }

    @Test
    void searchAndSortHighest() {
        List<Product> products = productRepository.searchAndSortHighest("Holt");
        Product product = products.get(0);
        assertEquals("HOLT 3FX GRI Erkek Sneaker", product.getName());
    }
    @Test
    void searchAndSortHighestFail() {
        List<Product> products = productRepository.searchAndSortHighest("Holt");
        Product product = products.get(0);
        assertNotEquals("Holt", product.getName());
    }

    @Test
    void searchAndSortLowest() {
        List<Product> products = productRepository.searchAndSortLowest("Unisex");
        Product product = products.get(0);
        assertEquals(69.9, product.getPrice());
    }
    @Test
    void searchAndSortLowestFail() {
        List<Product> products = productRepository.searchAndSortLowest("Unisex");
        Product product = products.get(0);
        assertNotEquals(100, product.getPrice());
    }

    @Test
    void searchAndSortBest() {
        List<Product> products = productRepository.searchAndSortBest("Unisex");
        Product product = products.get(0);
        assertEquals(3.8, product.getRating());
    }
    @Test
    void searchAndSortBestFail() {
        List<Product> products = productRepository.searchAndSortBest("Unisex");
        Product product = products.get(0);
        assertNotEquals(3.4, product.getRating());
    }

    @Test
    void searchAndSortWorst() {
        List<Product> products = productRepository.searchAndSortWorst("Unisex");
        Product product = products.get(0);
        assertEquals(3.4, product.getRating());
    }
    @Test
    void searchAndSortWorstFail() {
        List<Product> products = productRepository.searchAndSortWorst("Unisex");
        Product product = products.get(0);
        assertNotEquals(3.8, product.getRating());
    }

    @Test
    void getCategoryProducts() {
        List<Product> products = productRepository.getCategoryProducts(1);
        assertEquals(5,products.size());
    }
    @Test
    void getCategoryProductsFail() {
        List<Product> products = productRepository.getCategoryProducts(1);
        assertNotEquals(15,products.size());
    }
}