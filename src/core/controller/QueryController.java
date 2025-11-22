/*
 * Controlador para Consultas Adicionales
 */
package core.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import core.model.Book;
import core.model.Author;
import core.model.Publisher;
import core.model.PrintedBook;
import core.model.DigitalBook;
import core.model.Audiobook;
import core.model.Stand;
import core.model.Person;
import core.model.Response;
import core.model.StatusCode;
import core.storage.BookStorage;
import core.storage.PersonStorage;
import core.storage.StandStorage;
import core.storage.PublisherStorage;

/**
 *
 * @author edangulo
 */
public class QueryController {
    
    private BookStorage bookStorage;
    private PersonStorage personStorage;
    private StandStorage standStorage;
    private PublisherStorage publisherStorage;
    
    public QueryController() {
        this.bookStorage = BookStorage.getInstance();
        this.personStorage = PersonStorage.getInstance();
        this.standStorage = StandStorage.getInstance();
        this.publisherStorage = PublisherStorage.getInstance();
    }
    
    /**
     * Obtiene los libros de un autor específico
     */
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
    
    /**
     * Obtiene los libros por formato específico
     */
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
    
    /**
     * Obtiene los autores con más libros en diferentes editoriales
     */
    public Response<List<Author>> getAuthorsWithMostPublishers() {
        List<Author> allAuthors = personStorage.getAllAuthors();
        
        if (allAuthors.isEmpty()) {
            return new Response<>(StatusCode.OK, "No hay autores registrados", new ArrayList<>());
        }
        
        // Encontrar el máximo número de editoriales
        int maxPublishers = allAuthors.stream()
            .mapToInt(Author::getPublisherQuantity)
            .max()
            .orElse(-1);
        
        if (maxPublishers <= 0) {
            return new Response<>(StatusCode.OK, "No hay autores con libros en diferentes editoriales", new ArrayList<>());
        }
        
        // Filtrar autores con el máximo número de editoriales
        List<Author> authorsWithMaxPublishers = allAuthors.stream()
            .filter(a -> a.getPublisherQuantity() == maxPublishers)
            .sorted(Comparator.comparingLong(Author::getId))
            .collect(Collectors.toList());
        
        // Patrón Prototype: Clonar cada autor antes de retornar
        List<Author> clonedAuthors = new ArrayList<>();
        for (Author author : authorsWithMaxPublishers) {
            try {
                clonedAuthors.add((Author) author.clone());
            } catch (CloneNotSupportedException e) {
                clonedAuthors.add(author); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Autores con más libros en diferentes editoriales obtenidos", clonedAuthors);
    }
    
    /**
     * Obtiene todos los stands
     */
    public Response<List<Stand>> getAllStands() {
        List<Stand> stands = standStorage.getAllStands();
        
        // Patrón Prototype: Clonar cada stand antes de retornar
        List<Stand> clonedStands = new ArrayList<>();
        for (Stand stand : stands) {
            try {
                clonedStands.add((Stand) stand.clone());
            } catch (CloneNotSupportedException e) {
                clonedStands.add(stand); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Stands obtenidos", clonedStands);
    }
    
    /**
     * Obtiene todas las personas (autores, gerentes, narradores)
     */
    public Response<List<Person>> getAllPersons() {
        List<Person> persons = personStorage.getAllPersons();
        
        // Patrón Prototype: Clonar cada persona antes de retornar
        List<Person> clonedPersons = new ArrayList<>();
        for (Person person : persons) {
            try {
                clonedPersons.add((Person) person.clone());
            } catch (CloneNotSupportedException e) {
                clonedPersons.add(person); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Personas obtenidas", clonedPersons);
    }
    
    /**
     * Obtiene todas las editoriales
     */
    public Response<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherStorage.getAllPublishers();
        
        // Patrón Prototype: Clonar cada editorial antes de retornar
        List<Publisher> clonedPublishers = new ArrayList<>();
        for (Publisher publisher : publishers) {
            try {
                clonedPublishers.add((Publisher) publisher.clone());
            } catch (CloneNotSupportedException e) {
                clonedPublishers.add(publisher); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Editoriales obtenidas", clonedPublishers);
    }
    
    /**
     * Obtiene todos los libros
     */
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
    
    /**
     * Obtiene todos los libros impresos
     */
    public Response<List<Book>> getAllPrintedBooks() {
        List<Book> books = bookStorage.getBooksByType(PrintedBook.class);
        
        // Patrón Prototype: Clonar cada libro antes de retornar
        List<Book> clonedBooks = new ArrayList<>();
        for (Book book : books) {
            try {
                clonedBooks.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                clonedBooks.add(book); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Libros impresos obtenidos", clonedBooks);
    }
    
    /**
     * Obtiene todos los libros digitales
     */
    public Response<List<Book>> getAllDigitalBooks() {
        List<Book> books = bookStorage.getBooksByType(DigitalBook.class);
        
        // Patrón Prototype: Clonar cada libro antes de retornar
        List<Book> clonedBooks = new ArrayList<>();
        for (Book book : books) {
            try {
                clonedBooks.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                clonedBooks.add(book); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Libros digitales obtenidos", clonedBooks);
    }
    
    /**
     * Obtiene todos los audiolibros
     */
    public Response<List<Book>> getAllAudiobooks() {
        List<Book> books = bookStorage.getBooksByType(Audiobook.class);
        
        // Patrón Prototype: Clonar cada libro antes de retornar
        List<Book> clonedBooks = new ArrayList<>();
        for (Book book : books) {
            try {
                clonedBooks.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                clonedBooks.add(book); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Audiolibros obtenidos", clonedBooks);
    }
}
