package com.workintech.Ecommerce.dto.requestDto;

public record ProductRequest(String name, String desc, Double price, Double rating,
                              Integer sellCount, Integer stock, String[] images, Long categoryID) {
}
