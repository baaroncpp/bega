package com.bwongo.commons.models.exceptions;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/11/23
 **/
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message, Object ... messageConstants){
        super(String.format(message, messageConstants));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
