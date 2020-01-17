package com.orionsoftware.controller;

import com.orionsoftware.dto.ConsultaDTO;
import com.orionsoftware.dto.ConsultaListaExamenDTO;
import com.orionsoftware.dto.ConsultaResumenDTO;
import com.orionsoftware.dto.FiltroConsultaDTO;
import com.orionsoftware.exception.ModelNotFoundException;
import com.orionsoftware.model.Archivo;
import com.orionsoftware.model.Consulta;
import com.orionsoftware.model.Paciente;
import com.orionsoftware.service.IArchivoService;
import com.orionsoftware.service.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService service;

    @Autowired
    private IArchivoService serviceArchivo;

    @GetMapping
    public ResponseEntity<List<Consulta>> listar(){
        return new ResponseEntity<List<Consulta>>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> leerPorId(@PathVariable("id") Integer id){
        Consulta obj = service.leerPorId(id);
        if(obj==null){
             throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        return new ResponseEntity<Consulta>(obj, HttpStatus.OK);
    }

    @GetMapping("/hateoas/{id}")
    public Resource<Consulta> leerPorIdHateoas(@PathVariable("id") Integer id){
        Consulta obj = service.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO "+id);
        }
        Resource<Consulta> resource = new Resource<Consulta>(obj);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).leerPorId(id));
        resource.add(linkTo.withRel("medicos-resource"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody ConsultaListaExamenDTO consultaListaExamenDTO){
        Consulta obj = service.registrarTransaccional(consultaListaExamenDTO);
        /*
        Devuelve una URL con el ID registrado para obtenerlo desde el Frontend ej: htttp:localhost/es/1
        No saturar el ancho de banda devolviendo toda la data registrada.
        * */
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsulta()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@RequestBody Consulta obj){
        service.registrar(obj);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
        Consulta obj = service.leerPorId(id);
        if(obj==null){
            throw new ModelNotFoundException("ID NO ENCONTRADO: "+id);
        }else{
            service.eliminar(id);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<Consulta>> buscar(@RequestBody FiltroConsultaDTO filtro) {
        List<Consulta> consultas = new ArrayList<>();

        if (filtro != null) {
            if (filtro.getFechaConsulta() != null) {
                consultas = service.buscarByFecha(filtro);
            } else {
                consultas = service.buscar(filtro);
            }
        }
        return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
    }

    @GetMapping(value = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConsultaDTO> listarHateoas(){

        List<Consulta> consultas = service.listar();
        List<ConsultaDTO> consultaDTOS = new ArrayList<>();

        consultas.stream().forEach(c->{

            ConsultaDTO d = new ConsultaDTO();
            d.setIdConsulta(c.getIdConsulta());
            d.setMedico(c.getMedico());
            d.setPaciente(c.getPaciente());

            //-> genera la url -> localhost:8080/consultas/1
            ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).leerPorId(c.getIdConsulta()));
            d.add(linkTo.withSelfRel());
            consultaDTOS.add(d);

            ControllerLinkBuilder linkTo1 = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PacienteController.class).leerPorId(c.getPaciente().getIdPaciente()));
            d.add(linkTo1.withSelfRel());
            consultaDTOS.add(d);

            ControllerLinkBuilder linkTo2 = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(MedicoController.class).leerPorId(c.getMedico().getIdMedico()));
            d.add(linkTo2.withSelfRel());
            consultaDTOS.add(d);
        });


        return consultaDTOS;

    }

    //Generar una JSON con la cantidad de consultas por fecha, para pasar a la grafica
    @GetMapping(value = "/listarResumen")
    public ResponseEntity<List<ConsultaResumenDTO>> listarResumen() {
        List<ConsultaResumenDTO> consultas = new ArrayList<>();
        consultas = service.listarResumen();
        return new ResponseEntity<List<ConsultaResumenDTO>>(consultas, HttpStatus.OK);
    }

    @GetMapping(value = "/generarReporte", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generarReporte(){
        byte[] data = service.generarReporte();

        return new ResponseEntity<byte[]>(data, HttpStatus.OK);
    }

    /* MULTIPART_FORM_DATA_VALUE -> Indica que viene un archivo adjunto

    */

    @PostMapping(value = "/guardarArchivo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> guardarArchivo(@RequestParam("file") MultipartFile file) throws IOException {
        int rpta = 0;
        Archivo ar = new Archivo();
        ar.setFiletype(file.getContentType());
        ar.setFilename(file.getName());
        ar.setValue(file.getBytes());
        rpta = serviceArchivo.guardar(ar);

        return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
    }

    @GetMapping(value = "/leerArchivo/{idArchivo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> leerArchivo(@PathVariable("idArchivo") Integer idArchivo)  {

        byte[] arr = serviceArchivo.leerArchivo(idArchivo);

        return new ResponseEntity<byte[]>(arr, HttpStatus.OK);
    }


}