/*
 * Controlador para Stands
 */
package core.controller;

import core.model.Stand;
import core.model.Response;
import core.model.StatusCode;
import core.storage.StandStorage;

/**
 *
 * @author edangulo
 */
public class StandController {
    
    private StandStorage standStorage;
    
    public StandController() {
        this.standStorage = StandStorage.getInstance();
    }
    
    public Response<Stand> createStand(long id, double price) {
        // Validar ID: único, >= 0, máximo 15 dígitos
        if (id < 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del stand debe ser mayor o igual a 0");
        }
        
        if (id > 999999999999999L) { // 15 dígitos
            return new Response<>(StatusCode.BAD_REQUEST, "El ID del stand no puede tener más de 15 dígitos");
        }
        
        if (standStorage.existsStandById(id)) {
            return new Response<>(StatusCode.CONFLICT, "Ya existe un stand con el ID: " + id);
        }
        
        // Validar precio: debe ser superior a 0
        if (price <= 0) {
            return new Response<>(StatusCode.BAD_REQUEST, "El precio del stand debe ser superior a 0");
        }
        
        Stand stand = new Stand(id, price);
        standStorage.addStand(stand);
        
        // Patrón Prototype: Retornar una copia del objeto
        try {
            return new Response<>(StatusCode.CREATED, "Stand creado exitosamente", (Stand) stand.clone());
        } catch (CloneNotSupportedException e) {
            return new Response<>(StatusCode.OK, "Stand creado exitosamente (sin clon)", stand);
        }
    }
    
    public Response<java.util.List<Stand>> getAllStands() {
        java.util.List<Stand> stands = standStorage.getAllStands();
        
        // Patrón Prototype: Clonar cada stand antes de retornar
        java.util.List<Stand> clonedStands = new java.util.ArrayList<>();
        for (Stand stand : stands) {
            try {
                clonedStands.add((Stand) stand.clone());
            } catch (CloneNotSupportedException e) {
                clonedStands.add(stand); // Fallback en caso de error
            }
        }
        
        return new Response<>(StatusCode.OK, "Stands obtenidos", clonedStands);
    }
}
