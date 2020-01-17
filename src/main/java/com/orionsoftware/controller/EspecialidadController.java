package com.orionsoftware.controller;

import com.orionsoftware.exception.ModelNotFoundException;
import com.orionsoftware.model.Especialidad;
import com.orionsoftware.service.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

    @Autowired
    private IEspecialidadService service;

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar(){
        return new ResponseEntity<List<Especialidad>>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> leerPorId(@PathVariable("id") Integer id){
        Especialidad obj = service.leerPorId(id);
        if(obj==null){
             throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        return new ResponseEntity<Especialidad>(obj, HttpStatus.OK);
    }

    @GetMapping("/hateoas/{id}")
    public Resource<Especialidad> leerPorIdHateoas(@PathVariable("id") Integer id){
        Especialidad obj = service.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        Resource<Especialidad> resource = new Resource<Especialidad>(obj);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).leerPorId(id));
        resource.add(linkTo.withRel("especialidades-resource"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Especialidad e){
        Especialidad obj = service.registrar(e);
        /*
        Devuelve una URL con el ID registrado para obtenerlo desde el Frontend ej: htttp:localhost/es/1
        No saturar el ancho de banda devolviendo toda la data registrada.
        * */
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdEspecialidad()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@RequestBody Especialidad obj){
        service.registrar(obj);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
        Especialidad obj = service.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            service.eliminar(id);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}