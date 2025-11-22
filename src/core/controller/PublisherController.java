/*
 * Controlador para Editoriales
 */
package core.controller;

import java.util.regex.Pattern;
import core.model.Publisher;
import core.model.Manager;
import core.model.Response;
import core.model.StatusCode;
import core.storage.PublisherStorage;
import core.storage.PersonStorage;

/**
 *
 * @author edangulo
 */
public class PublisherController {
    
    private PublisherStorage publisherStorage;
    
    public PublisherController() {
        this.publisherStorage = PublisherStorage.getInstance();
    }
    
    /**
     * Valida que el NIT siga el formato XXX.XXX.XXX-X
     */
    private boolean isValidNit(String nit) {
        String pattern = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d$";
        return Pattern.matches(pattern, nit);
    }
    
    public Response<Publisher> createPublisher(String nit, String name, String address, long managerId) {
        // Validar NIT: formato XXX.XXX.XXX-X
        if (nit == null || nit.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El NIT no puede estar vacío");
        }
        
        if (!isValidNit(nit)) {
            return new Response<>(StatusCode.BAD_REQUEST, "El NIT debe tener el formato XXX.XXX.XXX-X");
        }
        
        if (publisherStorage.existsPublisherByNit(nit)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe una editorial con el NIT: " + nit);
        }
        
        // Validar campos no vacíos
        if (name == null || name.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El nombre de la editorial no puede estar vacío");
        }
        
        if (address == null || address.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "La dirección de la editorial no puede estar vacía");
        }
        
        // Validar que el gerente exista
        PersonStorage personStorage = PersonStorage.getInstance();
        Manager manager = personStorage.getManagerById(managerId);
        if (manager == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Gerente no encontrado con ID: " + managerId);
        }
        
        Publisher publisher = new Publisher(nit, name.trim(), address.trim(), manager);
        publisherStorage.addPublisher(publisher);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Editorial creada exitosamente", (Publisher) publisher.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Editorial creada exitosamente (sin clon)", publisher);
        }
    }
    
    public Response<java.util.List<Publisher>> getAllPublishers() {
        java.util.List<Publisher> publishers = publisherStorage.getAllPublishers();
        
        // Patrón Prototype: Clonar cada editorial antes de retornar
        java.util.List<Publisher> clonedPublishers = new java.util.ArrayList<>();
        for (Publisher publisher : publishers) {
            try {
                clonedPublishers.add((Publisher) publisher.clone());
            } catch (CloneNotSupportedException e) {
                clonedPublishers.add(publisher); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Editoriales obtenidas", clonedPublishers);
    }
    
    public Response<Publisher> getPublisherByNit(String nit) {
        Publisher publisher = publisherStorage.getPublisherByNit(nit);
        if (publisher == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Editorial no encontrada con NIT: " + nit);
        }
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.OK, "Editorial obtenida", (Publisher) publisher.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.OK, "Editorial obtenida (sin clon)", publisher);
        }
    }
}
