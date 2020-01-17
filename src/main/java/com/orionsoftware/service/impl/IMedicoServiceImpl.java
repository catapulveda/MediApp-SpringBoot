package com.orionsoftware.service.impl;

import com.orionsoftware.model.Medico;
import com.orionsoftware.repo.IMedicoRepo;
import com.orionsoftware.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMedicoServiceImpl implements IMedicoService {

    @Autowired
    private IMedicoRepo repo;

    @Override
    public Medico registrar(Medico obj) {
        return repo.save(obj);
    }

    @Override
    public Medico modificar(Medico obj) {
        return repo.save(obj);
    }

    @Override
    public Medico leerPorId(Integer id) {
        return repo.findOne(id);
    }

    //@PreAuthorize("hasAuthority('USER')")
    @PreAuthorize("@restAuthServiceImpl.hasAccess('listar')")
    @Override
    public List<Medico> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.delete(id);
    }
}
