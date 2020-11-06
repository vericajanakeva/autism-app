package com.example.bookaholic3.web_or_presentation.controller;

import com.example.bookaholic3.model.Category;
import com.example.bookaholic3.model.Book;
import com.example.bookaholic3.service_or_business.CategoryService;
import com.example.bookaholic3.service_or_business.BookService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {


    private BookService bookService;
    private CategoryService categoryService;

    public BookController(BookService bookService,
                             CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }


    @GetMapping
    public String getBookPage(Model model) {
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }


    @GetMapping("/colors")
    public String getColorsPage(Model model) {
        List<Book> books = this.bookService.findAllByCategoryId(1L);
        model.addAttribute("books", books);
        return "books";
    }
    @GetMapping("/game")
    public String getGamePage() {

        return "game";
    }
    @GetMapping("/counting")
    public String getCountingPage(Model model) {
        List<Book> books = this.bookService.findAllByCategoryId(2L);
        model.addAttribute("books", books);
        return "books";
    }
    @GetMapping("/emotions")
    public String getEmotionsPage(Model model) {
        List<Book> books = this.bookService.findAllByCategoryId(3L);
        model.addAttribute("books", books);
        return "books";
    }
    @GetMapping("/greetings")
    public String getGreetingsPage(Model model) {
        List<Book> books = this.bookService.findAllByCategoryId(4L);
        model.addAttribute("books", books);
        return "books";
    }
    @GetMapping("/fruitsvegetables")
    public String getFruitsVegetablesPage(Model model) {
        List<Book> books = this.bookService.findAllByCategoryId(5L);
        model.addAttribute("books", books);
        return "books";
    }




    @GetMapping("/add-new")
    public String addNewBookPage(Model model) {
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("book", new Book());

        return "add-book";
    }

    @GetMapping("/{id}/edit")
    public String editBookPage(Model model, @PathVariable Long id) {
        try {
            Book book = this.bookService.findById(id);
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("book", book);
            model.addAttribute("categories", categories);
            return "add-book";
        } catch (RuntimeException ex) {
            return "redirect:/books?error=" + ex.getMessage();
        }
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public String saveBook(
            @Valid Book book,
            BindingResult bindingResult,
            @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            return "add-book";
        }
        try {
            this.bookService.saveBook(book, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBookWithPost(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
 }

    @DeleteMapping("/{id}/delete")
    public String deleteBookWithDelete(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }
}
