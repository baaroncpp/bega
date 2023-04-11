package com.bwongo.commons.models.exceptions;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/11/23
 **/
public class BadCredentialsException  extends RuntimeException{
    public BadCredentialsException(String message, Object ... messageConstants){
        super(String.format(message, messageConstants));
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
