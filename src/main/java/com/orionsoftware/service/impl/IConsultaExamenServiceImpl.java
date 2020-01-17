package com.orionsoftware.service.impl;

import com.orionsoftware.model.ConsultaExamen;
import com.orionsoftware.repo.IConsultaExamenRepo;
import com.orionsoftware.service.IConsultaExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IConsultaExamenServiceImpl implements IConsultaExamenService {

    @Autowired
    private IConsultaExamenRepo repo;

    @Override
    public List<ConsultaExamen> listarExamenesPorConsulta(Integer idconsulta) {
        return repo.listarExamenesPorConsulta(idconsulta);
    }
}
