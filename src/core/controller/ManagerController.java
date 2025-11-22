/*
 * Controlador para Gerentes
 */
package core.controller;

import core.model.Manager;
import core.model.Response;
import core.model.StatusCode;
import core.storage.PersonStorage;

/**
 *
 * @author edangulo
 */
public class ManagerController {
    
    private PersonStorage personStorage;
    
    public ManagerController() {
        this.personStorage = PersonStorage.getInstance();
    }
    
    public Response<Manager> createManager(long id, String firstname, String lastname) {
        // Validar ID: único, >= 0, máximo 15 dígitos
        if (id < 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del gerente debe ser mayor o igual a 0");
        }
        
        if (id > 999999999999999L) { // 15 dígitos
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del gerente no puede tener más de 15 dígitos");
        }
        
        if (personStorage.existsManagerById(id)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un gerente con el ID: " + id);
        }
        
        // Validar campos no vacíos
        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El nombre del gerente no puede estar vacío");
        }
        
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El apellido del gerente no puede estar vacío");
        }
        
        Manager manager = new Manager(id, firstname.trim(), lastname.trim());
        personStorage.addManager(manager);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Gerente creado exitosamente", (Manager) manager.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Gerente creado exitosamente (sin clon)", manager);
        }
    }
    
    public Response<java.util.List<Manager>> getAllManagers() {
        java.util.List<Manager> managers = personStorage.getAllManagers();
        
        // Patrón Prototype: Clonar cada gerente antes de retornar
        java.util.List<Manager> clonedManagers = new java.util.ArrayList<>();
        for (Manager manager : managers) {
            try {
                clonedManagers.add((Manager) manager.clone());
            } catch (CloneNotSupportedException e) {
                clonedManagers.add(manager); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Gerentes obtenidos", clonedManagers);
    }
    
    public Response<Manager> getManagerById(long id) {
        Manager manager = personStorage.getManagerById(id);
        if (manager == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Gerente no encontrado con ID: " + id);
        }
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.OK, "Gerente obtenido", (Manager) manager.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.OK, "Gerente obtenido (sin clon)", manager);
        }
    }
}
