package com.orionsoftware.service.impl;

import com.orionsoftware.model.Archivo;
import com.orionsoftware.repo.IArchivoRepo;
import com.orionsoftware.service.IArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchivoServiceImpl implements IArchivoService {

	@Autowired
	private IArchivoRepo repo;

	@Override
	public int guardar(Archivo archivo) {
		Archivo ar = repo.save(archivo);
		return ar.getIdArchivo() > 0 ? 1 : 0;
	}

	@Override
	public byte[] leerArchivo(Integer idArchivo) {
		return repo.findOne(idArchivo).getValue();
	}

}
