package com.orionsoftware.repo;

import com.orionsoftware.model.Consulta;
import com.orionsoftware.model.ConsultaExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IConsultaExamenRepo extends JpaRepository<ConsultaExamen, Integer> {

    //@Transactional//CONFIRMA LOS CAMBIOS DE LA SENTENCIA
    @Modifying//SE LE DICE A JPA QUE SERA UNA SENTENCIA DE CAMBIO Y NO DE REGISTRO
    @Query(value = "INSERT INTO consulta_examen(id_consulta, id_examen) VALUES (:idConsulta, :idExamen)", nativeQuery = true)
    Integer registrar(@Param("idConsulta") Integer idConsulta, @Param("idExamen") Integer idExamen);

    //RETORNA LOS EXAMENES DADO UN ID DE CONSULTA
    @Query("from ConsultaExamen ce where ce.consulta.idConsulta = :idConsulta")
    List<ConsultaExamen> listarExamenesPorConsulta(@Param("idConsulta") Integer idconsulta);

}
