package com.example.bookaholic3.persistence_or_repository;

import com.example.bookaholic3.model.Category;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
@Profile("!in-memory")
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
