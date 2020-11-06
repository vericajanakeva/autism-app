package com.example.bookaholic3.persistence_or_repository;

import com.example.bookaholic3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOrderByPriceAsc();

    List<Book> findAllByOrderByPriceDesc();

    long countAllByPriceGreaterThan(@Param("price") Float price);

    List<Book> findAllByPriceGreaterThan(@Param("price") Float price);

    List<Book> findAllByCategoryId(@Param("id") Long id);
    List<Book> findAllByNameLikeAndCategoryIdOrderByPriceDesc(String name, Long categoryId);
}
