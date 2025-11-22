package core.model;
public class Response<T> {
    
    private StatusCode status;
    private String message;
    private T data;
    
    public Response(StatusCode status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
    
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
