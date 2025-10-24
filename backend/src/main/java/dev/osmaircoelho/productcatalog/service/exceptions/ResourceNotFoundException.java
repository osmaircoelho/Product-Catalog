package dev.osmaircoelho.productcatalog.service.exceptions;


import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID =1L;
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}