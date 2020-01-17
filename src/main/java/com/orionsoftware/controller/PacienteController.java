package com.orionsoftware.controller;

import com.orionsoftware.exception.ModelNotFoundException;
import com.orionsoftware.model.Paciente;
import com.orionsoftware.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar(){
        return new ResponseEntity<List<Paciente>>(pacienteService.listar(), HttpStatus.OK);
    }

    //OBTIENE UNA LISTA DE PACIENTES SEGUN LA CANTIDAD DE ELEMENTOS QUE QUEREMOS VER
    @GetMapping("/pageable")
    public ResponseEntity<Page<Paciente>> listarPageable(Pageable pageable) {
        Page<Paciente> pacientes = pacienteService.listarPageable(pageable);
        return new ResponseEntity<Page<Paciente>>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> leerPorId(@PathVariable("id") Integer id){
        Paciente obj = pacienteService.leerPorId(id);
        if(obj==null){
             throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        return new ResponseEntity<Paciente>(obj, HttpStatus.OK);
    }

    @GetMapping("/hateoas/{id}")
    public Resource<Paciente> leerPorIdHateoas(@PathVariable("id") Integer id){
        Paciente obj = pacienteService.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        Resource<Paciente> resource = new Resource<Paciente>(obj);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).leerPorId(id));
        resource.add(linkTo.withRel("paciente-resource"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Paciente paciente){
        Paciente obj = pacienteService.registrar(paciente);
        /*
        Devuelve una URL con el ID registrado para obtenerlo desde el Frontend ej: htttp:localhost/pacientes/1
        No saturar el ancho de banda devolviendo toda la data registrada.
        * */
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPaciente()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@RequestBody Paciente obj){
        pacienteService.registrar(obj);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
        Paciente obj = pacienteService.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            pacienteService.eliminar(id);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}