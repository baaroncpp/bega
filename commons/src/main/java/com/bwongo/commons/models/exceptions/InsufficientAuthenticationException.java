package com.bwongo.commons.models.exceptions;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/11/23
 **/
public class InsufficientAuthenticationException extends RuntimeException{
    public InsufficientAuthenticationException(String message, Object ... messageConstants){
        super(String.format(message, messageConstants));
    }

    public InsufficientAuthenticationException(String message) {
        super(message);
    }
}
