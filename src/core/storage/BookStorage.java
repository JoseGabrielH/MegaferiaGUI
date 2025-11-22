package core.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import core.model.Book;
import core.model.Author;
import core.model.Observer;

public class BookStorage {
    
    private static BookStorage instance;
    private List<Book> books;
    private List<Observer> observers;
    
    private BookStorage() {
        this.books = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    
    public static BookStorage getInstance() {
        if (instance == null) {
            instance = new BookStorage();
        }
        return instance;
    }
    
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
    
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    
    public void addBook(Book book) {
        this.books.add(book);
        notifyObservers();
    }
    
    public Book getBookByIsbn(String isbn) {
        return books.stream()
            .filter(b -> b.getIsbn().equals(isbn))
            .findFirst()
            .orElse(null);
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.stream()
            .sorted((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()))
            .collect(Collectors.toList()));
    }
    
    public List<Book> getBooksByType(Class<?> type) {
        return new ArrayList<>(books.stream()
            .filter(b -> type.isInstance(b))
            .sorted((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()))
            .collect(Collectors.toList()));
    }
    
    public List<Book> getBooksByFormat(String format) {
        return new ArrayList<>(books.stream()
            .filter(b -> b.getFormat().equals(format))
            .sorted((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()))
            .collect(Collectors.toList()));
    }
    
    public List<Book> getBooksByAuthor(Author author) {
        return new ArrayList<>(author.getBooks().stream()
            .sorted((b1, b2) -> b1.getIsbn().compareTo(b2.getIsbn()))
            .collect(Collectors.toList()));
    }
    
    public boolean existsBookByIsbn(String isbn) {
        return books.stream().anyMatch(b -> b.getIsbn().equals(isbn));
    }
    
    public void clear() {
        this.books.clear();
    }
}
