package com.example.bookaholic3.service_or_business.impl;

import com.example.bookaholic3.model.Category;
import com.example.bookaholic3.service_or_business.utils.PopulateInitialValuesInCategories;
import com.example.bookaholic3.model.exception.CategoryNotFoundException;
import com.example.bookaholic3.persistence_or_repository.BookRepository;
import com.example.bookaholic3.persistence_or_repository.CategoryRepository;
import com.example.bookaholic3.service_or_business.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Profile("!amazon")
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByName(String name) {
        return null;
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category c = this.findById(id);
        c.setName(category.getName());
        c.setAddress(category.getAddress());
        return this.categoryRepository.save(c);
    }

    @Override
    public Category updateName(Long id, String name) {
        Category c = this.findById(id);
        c.setName(name);
        return this.categoryRepository.save(c);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
    @PostConstruct
    public void init() {
        if(categoryRepository.findAll().size() == 0) {
            PopulateInitialValuesInCategories populateInitialValuesInBooks = new PopulateInitialValuesInCategories();
            populateInitialValuesInBooks.init();
        }
    }
}
