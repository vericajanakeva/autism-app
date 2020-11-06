package com.example.bookaholic3.service_or_business;

import com.example.bookaholic3.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    List<Book> findAllByCategoryId(Long categoryId);
    List<Book> findAllSortedByPrice(boolean asc);
    Book findById(Long id);
    Book saveBook(String name, Float price, Integer quantity, Long categoryId);
    Book saveBook(Book book, MultipartFile image) throws IOException;
    Book updateBook(Long id, Book book, MultipartFile image) throws IOException;
    void deleteById(Long id);
}
