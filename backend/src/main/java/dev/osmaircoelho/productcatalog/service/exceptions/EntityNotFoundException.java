package dev.osmaircoelho.productcatalog.service.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String msg){
        super(msg);
    }
}

