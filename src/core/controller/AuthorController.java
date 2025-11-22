/*
 * Controlador para Autores
 */
package core.controller;

import core.model.Author;
import core.model.Response;
import core.model.StatusCode;
import core.storage.PersonStorage;

/**
 *
 * @author edangulo
 */
public class AuthorController {
    
    private PersonStorage personStorage;
    
    public AuthorController() {
        this.personStorage = PersonStorage.getInstance();
    }
    
    public Response<Author> createAuthor(long id, String firstname, String lastname) {
        // Validar ID: único, >= 0, máximo 15 dígitos
        if (id < 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del autor debe ser mayor o igual a 0");
        }
        
        if (id > 999999999999999L) { // 15 dígitos
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del autor no puede tener más de 15 dígitos");
        }
        
        if (personStorage.existsAuthorById(id)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un autor con el ID: " + id);
        }
        
        // Validar campos no vacíos
        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El nombre del autor no puede estar vacío");
        }
        
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El apellido del autor no puede estar vacío");
        }
        
        Author author = new Author(id, firstname.trim(), lastname.trim());
        personStorage.addAuthor(author);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Autor creado exitosamente", (Author) author.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Autor creado exitosamente (sin clon)", author);
        }
    }
    
    public Response<java.util.List<Author>> getAllAuthors() {
        java.util.List<Author> authors = personStorage.getAllAuthors();
        
        // Patrón Prototype: Clonar cada autor antes de retornar
        java.util.List<Author> clonedAuthors = new java.util.ArrayList<>();
        for (Author author : authors) {
            try {
                clonedAuthors.add((Author) author.clone());
            } catch (CloneNotSupportedException e) {
                clonedAuthors.add(author); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Autores obtenidos", clonedAuthors);
    }
    
    public Response<Author> getAuthorById(long id) {
        Author author = personStorage.getAuthorById(id);
        if (author == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Autor no encontrado con ID: " + id);
        }
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.OK, "Autor obtenido", (Author) author.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.OK, "Autor obtenido (sin clon)", author);
        }
    }
}
