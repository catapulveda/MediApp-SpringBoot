package com.orionsoftware.repo;

import com.orionsoftware.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IConsultaRepo extends JpaRepository<Consulta, Integer> {

    //TRAEMOS LA CONSULTA SEGUN LOS PARAMETROS DEL NOMBRE O DOCUMENTO
    @Query("FROM Consulta c WHERE c.paciente.documento = :documento or LOWER(c.paciente.nombres) LIKE %:nombreCompleto% or LOWER(c.paciente.apellidos) LIKE %:nombreCompleto%")
    List<Consulta> buscar(@Param("documento") String documento, @Param("nombreCompleto") String nombreCompleto);

    //RETORNA LAS CONSULTAS QUE SE ENCUENTRE ENTRE LA FECHA QUE SE PASA COMO PARAMETRO
    @Query("from Consulta c where c.fecha between :fechaConsulta and :fechaSgte")
    List<Consulta> buscarFecha(@Param("fechaConsulta") LocalDateTime fechaConsulta, @Param("fechaSgte") LocalDateTime fechaSgte);

    //LLAMADA AL PROCEDIMIENTO ALMACENADO PARA RETORNAR LA CANTIDAD Y LA FECHA DE LAS CONSULTAS
    @Query(value = "select * from fn_listarResumen()", nativeQuery = true)
    List<Object[]> listarResumen();

}
