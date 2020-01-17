package com.orionsoftware.service.impl;

import com.orionsoftware.model.Paciente;
import com.orionsoftware.repo.IPacienteRepo;
import com.orionsoftware.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IPacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteRepo pacienteRepo;

    @Override
    public Paciente registrar(Paciente obj) {
        return pacienteRepo.save(obj);
    }

    @Override
    public Paciente modificar(Paciente obj) {
        return pacienteRepo.save(obj);
    }

    @Override
    public Paciente leerPorId(Integer id) {
        return pacienteRepo.findOne(id);
    }

    @Override
    public List<Paciente> listar() {
        return pacienteRepo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        pacienteRepo.delete(id);
    }

    @Override
    public Page<Paciente> listarPageable(Pageable pageable) {
        return pacienteRepo.findAll(pageable);
    }
}
