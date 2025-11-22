/*
 * Controlador para Narradores
 */
package core.controller;

import core.model.Narrator;
import core.model.Response;
import core.model.StatusCode;
import core.storage.PersonStorage;

/**
 *
 * @author edangulo
 */
public class NarratorController {
    
    private PersonStorage personStorage;
    
    public NarratorController() {
        this.personStorage = PersonStorage.getInstance();
    }
    
    public Response<Narrator> createNarrator(long id, String firstname, String lastname) {
        // Validar ID: único, >= 0, máximo 15 dígitos
        if (id < 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del narrador debe ser mayor o igual a 0");
        }
        
        if (id > 999999999999999L) { // 15 dígitos
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del narrador no puede tener más de 15 dígitos");
        }
        
        if (personStorage.existsNarratorById(id)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un narrador con el ID: " + id);
        }
        
        // Validar campos no vacíos
        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El nombre del narrador no puede estar vacío");
        }
        
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "El apellido del narrador no puede estar vacío");
        }
        
        Narrator narrator = new Narrator(id, firstname.trim(), lastname.trim());
        personStorage.addNarrator(narrator);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Narrador creado exitosamente", (Narrator) narrator.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.CREATED, "Narrador creado exitosamente (sin clon)", narrator);
        }
    }
    
    public Response<java.util.List<Narrator>> getAllNarrators() {
        java.util.List<Narrator> narrators = personStorage.getAllNarrators();
        
        // Patrón Prototype: Clonar cada narrador antes de retornar
        java.util.List<Narrator> clonedNarrators = new java.util.ArrayList<>();
        for (Narrator narrator : narrators) {
            try {
                clonedNarrators.add((Narrator) narrator.clone());
            } catch (CloneNotSupportedException e) {
                clonedNarrators.add(narrator); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Narradores obtenidos", clonedNarrators);
    }
    
    public Response<Narrator> getNarratorById(long id) {
        Narrator narrator = personStorage.getNarratorById(id);
        if (narrator == null) {
            return new Response<>(StatusCode.NOT_FOUND, "Narrador no encontrado con ID: " + id);
        }
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.OK, "Narrador obtenido", (Narrator) narrator.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.OK, "Narrador obtenido (sin clon)", narrator);
        }
    }
}
