package com.bwongo.commons.models.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
public record ExceptionPayLoad(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
}
