package com.prog4.libraryManagement.service;

import com.prog4.libraryManagement.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prog4.libraryManagement.repository.BookCrudOperations;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private BookCrudOperations bookRepository;

    public List<Book> getAll(){
        return bookRepository.findAll();
    }
    public List<Book> saveAllBooks(List<Book> books){
        return bookRepository.saveAll(books);
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(Book bookId) {
        bookRepository.delete(bookId);
    }
}
