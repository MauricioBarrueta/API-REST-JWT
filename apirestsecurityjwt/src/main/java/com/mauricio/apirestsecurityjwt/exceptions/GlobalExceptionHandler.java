package com.mauricio.apirestsecurityjwt.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mauricio.apirestsecurityjwt.dto.ErrorDetails;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//! @ControllerAdvice: Permite manejar excepciones (handler), maneja todaas las excepciones de la aplicación
@ControllerAdvice

//* Se extiende de ResponseEntityException para aplicar el método cuando no está valido algun dato
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //! @ExceptionHandler: Anotación encargada de manejar las excepciones que se hayan detallado

    @ExceptionHandler(ResourceNotFoundException.class) 
    //* Maneja la excepción 'NotFoundException'
    public ResponseEntity<ErrorDetails> ResourceNotFoundHandler(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    //* Maneja la excepción 'BadRequest'
    public ResponseEntity<ErrorDetails> AppExceptionHandler(AppException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    //* Maneja la excepción global 'Internal Server Error'
    public ResponseEntity<ErrorDetails> GlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //* Método que indica cuando lo que se está enviando en la petición no es valido, y muestra una excepción
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        //! Método getBindingResult(): Obtiene todos los errores encontrados y se obtienen con getAllErrors()
        ex.getBindingResult().getAllErrors().forEach((error) -> { //* Se utiliza un for-each para recorrer cada error
            String nombreCampo = ((FieldError)error).getField(); //! FieldError: Encapsula el campo con el error y se obtiene mediante el método getField()
            String mensaje = error.getDefaultMessage(); //! getDefaultMessage(): Devuelve el mensaje de error

            errores.put(nombreCampo, mensaje); //* Se mapean ambos campos a la variable
        });

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
