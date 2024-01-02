package com.workintech.Ecommerce.repository;

import com.workintech.Ecommerce.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
