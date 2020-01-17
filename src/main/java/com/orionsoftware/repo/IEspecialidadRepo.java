package com.orionsoftware.repo;

import com.orionsoftware.model.Especialidad;
import com.orionsoftware.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEspecialidadRepo extends JpaRepository<Especialidad, Integer> {


}