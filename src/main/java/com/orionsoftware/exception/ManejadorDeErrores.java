package com.orionsoftware.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//@ControllerAdvice -> Es una capa transversal a todo las capas del proyecto, intercepta cualquier excepcion
//@RestController -> Para que pueda exponer el codigo de los errores como un servicio REST
@ControllerAdvice
@RestController
public class ManejadorDeErrores extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasLasExcepciones(Exception ex, WebRequest request){
        //Creamos la estructura de la excepcion generada
        ExceptionResponse er = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        //Enviamos los datos excepcion como REST
        return new ResponseEntity<ExceptionResponse>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /* Esta clase va a interceptar los errores de tipo ModelNotFoundException -> Recurso no encontrado
    @@ExceptionHandler -> La Anotacion es porque es una clase de excepcion
    * param ex: La excepcion que se ha creado
    * param request: la trama de la peticion actual
    * */
    @ExceptionHandler(ModelNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ModelNotFoundException ex, WebRequest request){
        //Creamos la estructura de la excepcion generada
        ExceptionResponse er = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        //Enviamos los datos excepcion como REST
        return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
    }

    //validar la estructura del INSERT--- debe cumplirese las restricciones establecidas con las anotaciones en el modelo
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse er = new ExceptionResponse(new Date(), "Validacion fallida", request.getDescription(false));
        return new ResponseEntity<Object>(er, HttpStatus.BAD_REQUEST);
    }
}
