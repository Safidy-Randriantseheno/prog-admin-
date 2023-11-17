package repository;

import model.Book;
import repository.interfacegenerique.CrudOperations;

import java.util.List;

public class BookCrudOperations implements CrudOperations<Book> {
    private List<Book> bookList;
    @Override
    public List<Book> findAll() {
        return bookList;
    }
    @Override
    public List<Book> saveAll(List<Book> toSave) {
        bookList.addAll(toSave);
        return bookList;
    }
    @Override
    public Book save(Book toSave) {
        bookList.add(toSave);
        return toSave;
    }
    @Override
    public Book delete(Book toDelete) {
        bookList.remove(toDelete);
        return toDelete;
    }
}
