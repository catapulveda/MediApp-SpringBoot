package com.orionsoftware.service.impl;

import com.orionsoftware.model.Especialidad;
import com.orionsoftware.repo.IEspecialidadRepo;
import com.orionsoftware.service.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEspecialidadServiceImpl implements IEspecialidadService {

    @Autowired
    private IEspecialidadRepo repo;

    @Override
    public Especialidad registrar(Especialidad obj) {
        return repo.save(obj);
    }

    @Override
    public Especialidad modificar(Especialidad obj) {
        return repo.save(obj);
    }

    @Override
    public Especialidad leerPorId(Integer id) {
        return repo.findOne(id);
    }

    @Override
    public List<Especialidad> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.delete(id);
    }
}
