package controller;

import lombok.AllArgsConstructor;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @PostMapping("/saveAll")
    public List<Book> saveAllBooks(@RequestBody List<Book> books) {
        return bookService.saveAllBooks(books);
    }

    @PostMapping("/save")
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody Book book) {
        bookService.deleteBook(book);
    }
}