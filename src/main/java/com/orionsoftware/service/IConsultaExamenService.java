package com.orionsoftware.service;

import com.orionsoftware.model.ConsultaExamen;

import java.util.List;

public interface IConsultaExamenService {

    List<ConsultaExamen> listarExamenesPorConsulta(Integer idconsulta);

}
