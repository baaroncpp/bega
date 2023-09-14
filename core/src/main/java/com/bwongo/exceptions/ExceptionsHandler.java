package com.bwongo.exceptions;

import com.bwongo.commons.models.exceptions.*;
import com.bwongo.commons.models.exceptions.model.ExceptionPayLoad;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/27/23
 **/
@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException, HttpServletRequest request){

        var httpStatus = HttpStatus.BAD_REQUEST;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                badRequestException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request){

        var httpStatus = HttpStatus.NOT_FOUND;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                resourceNotFoundException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {InsufficientAuthenticationException.class})
    public ResponseEntity<Object> handleInsufficientAuthenticationException(InsufficientAuthenticationException insufficientAuthenticationException, HttpServletRequest request){

        var httpStatus = HttpStatus.FORBIDDEN;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                insufficientAuthenticationException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException badCredentialsException, HttpServletRequest request){

        var httpStatus = HttpStatus.UNAUTHORIZED;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                badCredentialsException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {DefaultException.class})
    public ResponseEntity<Object> handleDefaultException(DefaultException defaultException, HttpServletRequest request){

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                defaultException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {AccessDeniedException.class, org.springframework.security.authentication.BadCredentialsException.class, MalformedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<Object> handleSecurityException(Exception ex, HttpServletRequest request){

        ResponseEntity<Object> response = null;

        if(ex instanceof org.springframework.security.authentication.BadCredentialsException){
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "Authentication Failure",
                    HttpStatus.valueOf(401),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, HttpStatus.valueOf(401));
        }

        if(ex instanceof AccessDeniedException){
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "Not Authorized!",
                    HttpStatus.valueOf(403),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, HttpStatus.valueOf(403));
        }

        if(ex instanceof MalformedJwtException){
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "JWT Signature not valid!",
                    HttpStatus.valueOf(403),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, HttpStatus.valueOf(403));
        }

        if(ex instanceof ExpiredJwtException){
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "JWT already expired!",
                    HttpStatus.valueOf(403),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, HttpStatus.valueOf(403));
        }

        return response;
    }
}
