package com.orionsoftware.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
extends RuntimeException permite añadir excepciones personalizadas.
Se debe crear un constructor con un mensaje como parametro y enviarlo a la case padre RuntimeException con super(mensaje)
* */
//@ResponseStatus -> Anotacion que determina el tipo de excepcion
//@ResponseStatus(HttpStatus.NOT_FOUND)  -> Ya no se utuliza porque ya esta declarada el tipo de respuesta en el ManejadorDeErrores
public class ModelNotFoundException extends RuntimeException{

    public ModelNotFoundException(String message) {
        super(message);
    }
}
