package com.bwongo.authserver.utilities;

import com.bwongo.commons.models.exceptions.BadRequestException;
import com.bwongo.commons.models.exceptions.ExceptionPayLoad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException){

        var httpStatus = HttpStatus.BAD_REQUEST;

        var exceptionPayLoad = new ExceptionPayLoad(
                badRequestException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }
}
