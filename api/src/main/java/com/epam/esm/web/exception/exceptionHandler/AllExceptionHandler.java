package com.epam.esm.web.exception.exceptionHandler;

import com.epam.esm.exception.*;
import com.epam.esm.web.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(value = SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ResponseError exc(HttpServletResponse resp, HttpServletRequest req, Exception e){
        return new ResponseError("internal server error", 500);
    }


    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ResponseError validationError(ApplicationException e){
        return new ResponseError(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(value = ResourceExistenceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseError resourceDoesNotExist(ApplicationException e){
        return new ResponseError(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseError internalError(ApplicationException e){
        return new ResponseError(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(value = DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ResponseError duplicateResource(ApplicationException e){
        return new ResponseError(e.getMessage(), e.getErrorCode());
    }



}
