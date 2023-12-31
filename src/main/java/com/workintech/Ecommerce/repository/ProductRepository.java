package com.workintech.Ecommerce.repository;

import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.name FROM ecommerce.products AS p WHERE p.name=:productName", nativeQuery = true)
    String findByProductName(String productName);

    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p ORDER BY p.price DESC", nativeQuery = true)
    List<Product> sortHighestToLowest();

    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p ORDER BY p.price ASC", nativeQuery = true)
    List<Product> sortLowestToHighest();

    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p ORDER BY p.rating DESC", nativeQuery = true)
    List<Product> sortBestToWorst();

    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p ORDER BY p.rating ASC", nativeQuery = true)
    List<Product> sortWorstToBest();

    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p WHERE p.name ILIKE %:name%", nativeQuery = true)
    List<Product> searchByName(String name);

    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p WHERE p.name ILIKE %:name% ORDER BY p.price DESC", nativeQuery = true)
    List<Product> searchAndSortHighest(String name);
    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p WHERE p.name ILIKE %:name% ORDER BY p.price ASC", nativeQuery = true)
    List<Product> searchAndSortLowest(String name);
    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p WHERE p.name ILIKE %:name% ORDER BY p.rating DESC", nativeQuery = true)
    List<Product> searchAndSortBest(String name);
    @Query(value = "SELECT p.id, p.name, p.description, p.price, p.rating, p.sell_count, p.stock, p.images, p.category_id" +
            " FROM ecommerce.products AS p WHERE p.name ILIKE %:name% ORDER BY p.rating ASC", nativeQuery = true)
    List<Product> searchAndSortWorst(String name);

}
