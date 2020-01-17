package com.orionsoftware.controller;

import com.orionsoftware.exception.ModelNotFoundException;
import com.orionsoftware.model.Medico;
import com.orionsoftware.service.IMedicoService;
import com.orionsoftware.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private IMedicoService service;

    @GetMapping
    public ResponseEntity<List<Medico>> listar(){
        return new ResponseEntity<List<Medico>>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> leerPorId(@PathVariable("id") Integer id){
        Medico obj = service.leerPorId(id);
        if(obj==null){
             throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        return new ResponseEntity<Medico>(obj, HttpStatus.OK);
    }

    @GetMapping("/hateoas/{id}")
    public Resource<Medico> leerPorIdHateoas(@PathVariable("id") Integer id){
        Medico obj = service.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        Resource<Medico> resource = new Resource<Medico>(obj);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).leerPorId(id));
        resource.add(linkTo.withRel("medicos-resource"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Medico m){
        Medico obj = service.registrar(m);
        /*
        Devuelve una URL con el ID registrado para obtenerlo desde el Frontend ej: htttp:localhost/ms/1
        No saturar el ancho de banda devolviendo toda la data registrada.
        * */
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedico()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@RequestBody Medico obj){
        service.registrar(obj);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
        Medico obj = service.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            service.eliminar(id);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}