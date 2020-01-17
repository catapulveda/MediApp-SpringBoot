package com.orionsoftware.service;

import com.orionsoftware.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPacienteService extends ICRUD<Paciente>{

    Page<Paciente> listarPageable(Pageable pageable);

}
