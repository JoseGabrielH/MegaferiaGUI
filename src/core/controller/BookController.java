/*
 * Controlador para Libros
 */
package core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import core.model.Book;
import core.model.PrintedBook;
import core.model.DigitalBook;
import core.model.Audiobook;
import core.model.Author;
import core.model.Publisher;
import core.model.Narrator;
import core.model.Response;
import core.model.StatusCode;
import core.storage.BookStorage;
import core.storage.PersonStorage;
import core.storage.PublisherStorage;

/**
 *
 * @author edangulo
 */
public class BookController {
    
    private BookStorage bookStorage;
    private PersonStorage personStorage;
    private PublisherStorage publisherStorage;
    
    public BookController() {
        this.bookStorage = BookStorage.getInstance();
        this.personStorage = PersonStorage.getInstance();
        this.publisherStorage = PublisherStorage.getInstance();
    }
    
    /**
     * Valida que el ISBN siga el formato XXX-X-XX-XXXXXX-X
     */
    private boolean isValidIsbn(String isbn) {
        String pattern = "^\\d{3}-\\d-\\d{2}-\\d{6}-\\d$";
        return Pattern.matches(pattern, isbn);
    }
    
    public Response<PrintedBook> createPrintedBook(String title, List<Long> authorIds, String isbn, 
                                                   String genre, String format, double value, 
                                                   String publisherNit, int pages, int copies) {
        
        // Validación de ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ISBN no puede estar vacío");
        }
        
        if (!isValidIsbn(isbn)) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ISBN debe tener el formato XXX-X-XX-XXXXXX-X");
        }
        
        if (bookStorage.existsBookByIsbn(isbn)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un libro con el ISBN: " + isbn);
        }
        
        // Validación de campos básicos
        if (title == null || title.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El título del libro no puede estar vacío");
        }
        
        if (genre == null || genre.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El género del libro no puede estar vacío");
        }
        
        if (format == null || format.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El formato del libro no puede estar vacío");
        }
        
        if (value <= 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El valor del libro debe ser superior a 0");
        }
        
        // Validación de autores
        if (authorIds == null || authorIds.isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El libro debe tener al menos un autor");
        }
        
        ArrayList<Author> authors = new ArrayList<>();
        for (Long authorId : authorIds) {
            Author author = personStorage.getAuthorById(authorId);
            if (author == null) {
                return new Response<>(StatusCode.NOT_FOUND, "Autor no encontrado con ID: " + authorId);
            }
            // Validar que no haya autores duplicados
            if (authors.contains(author)) {
                return new Response<>(StatusCode.CONFLICT, "Los autores no pueden estar duplicados");
            }
            authors.add(author);
        }
        
        // Validación de editorial
        Publisher publisher = publisherStorage.getPublisherByNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Editorial no encontrada con NIT: " + publisherNit);
        }
        
        PrintedBook book = new PrintedBook(title.trim(), authors, isbn, genre.trim(), 
                                          format.trim(), value, publisher, pages, copies);
        bookStorage.addBook(book);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Libro impreso creado exitosamente", (PrintedBook) book.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Libro impreso creado exitosamente (sin clon)", book);
        }
    }
    
    public Response<DigitalBook> createDigitalBook(String title, List<Long> authorIds, String isbn, 
                                                   String genre, String format, double value, 
                                                   String publisherNit, String hyperlink) {
        
        // Validación de ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ISBN no puede estar vacío");
        }
        
        if (!isValidIsbn(isbn)) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ISBN debe tener el formato XXX-X-XX-XXXXXX-X");
        }
        
        if (bookStorage.existsBookByIsbn(isbn)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un libro con el ISBN: " + isbn);
        }
        
        // Validación de campos básicos
        if (title == null || title.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El título del libro no puede estar vacío");
        }
        
        if (genre == null || genre.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El género del libro no puede estar vacío");
        }
        
        if (format == null || format.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El formato del libro no puede estar vacío");
        }
        
        if (value <= 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El valor del libro debe ser superior a 0");
        }
        
        // Validación de autores
        if (authorIds == null || authorIds.isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El libro debe tener al menos un autor");
        }
        
        ArrayList<Author> authors = new ArrayList<>();
        for (Long authorId : authorIds) {
            Author author = personStorage.getAuthorById(authorId);
            if (author == null) {
                return new Response<>(StatusCode.NOT_FOUND, "Autor no encontrado con ID: " + authorId);
            }
            if (authors.contains(author)) {
                return new Response<>(StatusCode.CONFLICT, "Los autores no pueden estar duplicados");
            }
            authors.add(author);
        }
        
        // Validación de editorial
        Publisher publisher = publisherStorage.getPublisherByNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Editorial no encontrada con NIT: " + publisherNit);
        }
        
        DigitalBook book;
        if (hyperlink != null && !hyperlink.trim().isEmpty()) {
            book = new DigitalBook(title.trim(), authors, isbn, genre.trim(), 
                                  format.trim(), value, publisher, hyperlink.trim());
        } else {
            book = new DigitalBook(title.trim(), authors, isbn, genre.trim(), 
                                  format.trim(), value, publisher);
        }
        bookStorage.addBook(book);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Libro digital creado exitosamente", (DigitalBook) book.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Libro digital creado exitosamente (sin clon)", book);
        }
    }
    
    public Response<Audiobook> createAudiobook(String title, List<Long> authorIds, String isbn, 
                                               String genre, String format, double value, 
                                               String publisherNit, int duration, long narratorId) {
        
        // Validación de ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ISBN no puede estar vacío");
        }
        
        if (!isValidIsbn(isbn)) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ISBN debe tener el formato XXX-X-XX-XXXXXX-X");
        }
        
        if (bookStorage.existsBookByIsbn(isbn)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un libro con el ISBN: " + isbn);
        }
        
        // Validación de campos básicos
        if (title == null || title.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El título del libro no puede estar vacío");
        }
        
        if (genre == null || genre.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El género del libro no puede estar vacío");
        }
        
        if (format == null || format.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El formato del libro no puede estar vacío");
        }
        
        if (value <= 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El valor del libro debe ser superior a 0");
        }
        
        // Validación de autores
        if (authorIds == null || authorIds.isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El libro debe tener al menos un autor");
        }
        
        ArrayList<Author> authors = new ArrayList<>();
        for (Long authorId : authorIds) {
            Author author = personStorage.getAuthorById(authorId);
            if (author == null) {
                return new Response<>(StatusCode.NOT_FOUND, "Autor no encontrado con ID: " + authorId);
            }
            if (authors.contains(author)) {
                return new Response<>(StatusCode.CONFLICT, "Los autores no pueden estar duplicados");
            }
            authors.add(author);
        }
        
        // Validación de editorial
        Publisher publisher = publisherStorage.getPublisherByNit(publisherNit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Editorial no encontrada con NIT: " + publisherNit);
        }
        
        // Validación de narrador
        Narrator narrator = personStorage.getNarratorById(narratorId);
        if (narrator == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Narrador no encontrado con ID: " + narratorId);
        }
        
        Audiobook book = new Audiobook(title.trim(), authors, isbn, genre.trim(), 
                                      format.trim(), value, publisher, duration, narrator);
        bookStorage.addBook(book);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Audiolibro creado exitosamente", (Audiobook) book.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Audiolibro creado exitosamente (sin clon)", book);
        }
    }
    
    public Response<List<Book>> getAllBooks() {
        List<Book> books = bookStorage.getAllBooks();
        
        // Patrón Prototype: Clonar cada libro antes de retornar
        List<Book> clonedBooks = new ArrayList<>();
        for (Book book : books) {
            try {
                clonedBooks.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                clonedBooks.add(book); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Libros obtenidos", clonedBooks);
    }
    
    public Response<List<PrintedBook>> getAllPrintedBooks() {
        List<Book> allBooks = bookStorage.getBooksByType(PrintedBook.class);
        List<PrintedBook> printedBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book instanceof PrintedBook) {
                try {
                    printedBooks.add((PrintedBook) book.clone());
                } catch (CloneNotSupportedException e) {
                    printedBooks.add((PrintedBook) book); // Fallback
                }
            }
        }
        return new Response<>(StatusCode.OK, "Libros impresos obtenidos", printedBooks);
    }
    
    public Response<List<DigitalBook>> getAllDigitalBooks() {
        List<Book> allBooks = bookStorage.getBooksByType(DigitalBook.class);
        List<DigitalBook> digitalBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book instanceof DigitalBook) {
                try {
                    digitalBooks.add((DigitalBook) book.clone());
                } catch (CloneNotSupportedException e) {
                    digitalBooks.add((DigitalBook) book); // Fallback
                }
            }
        }
        return new Response<>(StatusCode.OK, "Libros digitales obtenidos", digitalBooks);
    }
    
    public Response<List<Audiobook>> getAllAudiobooks() {
        List<Book> allBooks = bookStorage.getBooksByType(Audiobook.class);
        List<Audiobook> audiobooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book instanceof Audiobook) {
                try {
                    audiobooks.add((Audiobook) book.clone());
                } catch (CloneNotSupportedException e) {
                    audiobooks.add((Audiobook) book); // Fallback
                }
            }
        }
        return new Response<>(StatusCode.OK, "Audiolibros obtenidos", audiobooks);
    }
    
    public Response<List<Book>> getBooksByAuthor(long authorId) {
        Author author = personStorage.getAuthorById(authorId);
        if (author == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Autor no encontrado con ID: " + authorId);
        }
        
        List<Book> books = bookStorage.getBooksByAuthor(author);
        
        // Patrón Prototype: Clonar cada libro antes de retornar
        List<Book> clonedBooks = new ArrayList<>();
        for (Book book : books) {
            try {
                clonedBooks.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                clonedBooks.add(book); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Libros del autor obtenidos", clonedBooks);
    }
    
    public Response<List<Book>> getBooksByFormat(String format) {
        List<Book> books = bookStorage.getBooksByFormat(format);
        
        // Patrón Prototype: Clonar cada libro antes de retornar
        List<Book> clonedBooks = new ArrayList<>();
        for (Book book : books) {
            try {
                clonedBooks.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                clonedBooks.add(book); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Libros por formato obtenidos", clonedBooks);
    }
}
