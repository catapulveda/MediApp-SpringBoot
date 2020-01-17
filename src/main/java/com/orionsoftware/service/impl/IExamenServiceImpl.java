package com.orionsoftware.service.impl;

import com.orionsoftware.model.Examen;
import com.orionsoftware.repo.IExamenRepo;
import com.orionsoftware.service.IExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IExamenServiceImpl implements IExamenService {

    @Autowired
    private IExamenRepo repo;

    @Override
    public Examen registrar(Examen obj) {
        return repo.save(obj);
    }

    @Override
    public Examen modificar(Examen obj) {
        return repo.save(obj);
    }

    @Override
    public Examen leerPorId(Integer id) {
        return repo.findOne(id);
    }

    @Override
    public List<Examen> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.delete(id);
    }
}
