package com.bwongo.commons.models.exceptions;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message, Object ... messageConstants){
        super(String.format(message, messageConstants));
    }

    public BadRequestException(String message) {
        super(message);
    }
}
