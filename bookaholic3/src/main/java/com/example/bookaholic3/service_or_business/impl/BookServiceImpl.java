package com.example.bookaholic3.service_or_business.impl;


import com.example.bookaholic3.model.Category;
import com.example.bookaholic3.model.Book;
import com.example.bookaholic3.model.exception.BookNotFoundException;
import com.example.bookaholic3.persistence_or_repository.BookRepository;
import com.example.bookaholic3.service_or_business.CategoryService;
import com.example.bookaholic3.service_or_business.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookaholic3.service_or_business.utils.PopulateInitialValuesInBooks;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository,
                              CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByCategoryId(Long categoryId) {
        return this.bookRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Book> findAllSortedByPrice(boolean asc) {
        if (asc) {
            return this.bookRepository.findAllByOrderByPriceAsc();
        }
        return this.bookRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Book findById(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book saveBook(String name, Float price, Integer quantity, Long categoryId) {
        Category category = this.categoryService.findById(categoryId);
        Book book = new Book(null, name, price, quantity, category);
        return this.bookRepository.save(book);
    }

    @Override
    public Book saveBook(Book book, MultipartFile image) throws IOException {
        Category category = this.categoryService.findById(book.getCategory().getId());
        book.setCategory(category);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
//            product.setImage(image);
            book.setImageBase64(base64Image);
        }
        return this.bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book, MultipartFile image) throws IOException {
        Book b = this.findById(id);
        Category category = this.categoryService.findById(book.getCategory().getId());
        b.setCategory(category);
        b.setPrice(book.getPrice());
        b.setQuantity(book.getQuantity());
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            b.setImageBase64(base64Image);
        }
        return this.bookRepository.save(b);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Book book = this.findById(id);
        this.bookRepository.deleteById(id);
    }
    @PostConstruct
    public void init() {
            if(bookRepository.findAll().size() == 0) {
                PopulateInitialValuesInBooks populateInitialValuesInBooks = new PopulateInitialValuesInBooks();
                populateInitialValuesInBooks.init();
            }
        }
    }
