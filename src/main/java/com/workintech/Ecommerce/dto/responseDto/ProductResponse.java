package com.workintech.Ecommerce.dto.responseDto;

public record ProductResponse(Long id, String name, String desc, Double price, Double rating,
                              Integer sellCount, Integer stock, String[] images, Long categoryID) {
}
