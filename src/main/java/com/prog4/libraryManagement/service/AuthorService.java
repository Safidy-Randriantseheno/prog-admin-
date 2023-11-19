package com.prog4.libraryManagement.service;

import com.prog4.libraryManagement.model.Author;
import com.prog4.libraryManagement.model.Book;
import com.prog4.libraryManagement.repository.AuthorCrudOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorCrudOperation authorRepository;
    public List<Author> getAll(){
        return authorRepository.findAll();
    }
    public List<Author> saveAllAuthors(List<Author> authors){
        return authorRepository.saveAll(authors);
    }
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
    public void deleteAuthor(Author authorId) {
        authorRepository.delete(authorId);
    }
}
