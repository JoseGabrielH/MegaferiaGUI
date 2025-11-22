/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model;

/**
 * Clase genérica para encapsular respuestas del servidor
 * @author edangulo
 * @param <T> Tipo de dato que contiene la respuesta
 */
public class Response<T> {
    
    private StatusCode status;
    private String message;
    private T data;
    
    /**
     * Constructor para respuestas sin datos
     * @param status Código de estado
     * @param message Mensaje descriptivo
     */
    public Response(StatusCode status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
    
    /**
     * Constructor para respuestas con datos
     * @param status Código de estado
     * @param message Mensaje descriptivo
     * @param data Datos de la respuesta
     */
    public Response(StatusCode status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public StatusCode getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public T getData() {
        return data;
    }
    
    public boolean isSuccess() {
        return status == StatusCode.OK || status == StatusCode.CREATED;
    }
    
    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
