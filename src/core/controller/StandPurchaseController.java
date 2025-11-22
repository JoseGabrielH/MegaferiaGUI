/*
 * Controlador para Compra de Stands
 */
package core.controller;

import java.util.ArrayList;
import java.util.List;
import core.model.Stand;
import core.model.Publisher;
import core.model.Response;
import core.model.StatusCode;
import core.storage.StandStorage;
import core.storage.PublisherStorage;

/**
 *
 * @author edangulo
 */
public class StandPurchaseController {
    
    private StandStorage standStorage;
    private PublisherStorage publisherStorage;
    
    public StandPurchaseController() {
        this.standStorage = StandStorage.getInstance();
        this.publisherStorage = PublisherStorage.getInstance();
    }
    
    /**
     * Realiza la compra de stands por parte de una o varias editoriales
     */
    public Response<String> purchaseStands(List<Long> standIds, List<String> publisherNits) {
        
        // Validar que haya stands y editoriales
        if (standIds == null || standIds.isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "Debe seleccionar al menos un stand");
        }
        
        if (publisherNits == null || publisherNits.isEmpty()) {
            return new Response<>(StatusCode.BAD_REQUEST, "Debe seleccionar al menos una editorial");
        }
        
        // Validar que no haya duplicados en stands
        if (standIds.size() != new java.util.HashSet<>(standIds).size()) {
            return new Response<>(StatusCode.CONFLICT, "No pueden haber stands duplicados");
        }
        
        // Validar que no haya duplicados en editoriales
        if (publisherNits.size() != new java.util.HashSet<>(publisherNits).size()) {
            return new Response<>(StatusCode.CONFLICT, "No pueden haber editoriales duplicadas");
        }
        
        // Obtener stands y verificar que existan
        List<Stand> stands = new ArrayList<>();
        for (Long standId : standIds) {
            Stand stand = standStorage.getStandById(standId);
            if (stand == null) {
                return new Response<>(StatusCode.NOT_FOUND, "Stand no encontrado con ID: " + standId);
            }
            stands.add(stand);
        }
        
        // Obtener editoriales y verificar que existan
        List<Publisher> publishers = new ArrayList<>();
        for (String publisherNit : publisherNits) {
            Publisher publisher = publisherStorage.getPublisherByNit(publisherNit);
            if (publisher == null) {
                return new Response<>(StatusCode.NOT_FOUND, "Editorial no encontrada con NIT: " + publisherNit);
            }
            publishers.add(publisher);
        }
        
        // Realizar la compra
        for (Stand stand : stands) {
            for (Publisher publisher : publishers) {
                stand.addPublisher(publisher);
                publisher.addStand(stand);
            }
        }
        
        return new Response<>(StatusCode.OK, "Compra de stands realizada exitosamente");
    }
    
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
}
