package com.orionsoftware.service;

import com.orionsoftware.dto.ConsultaListaExamenDTO;
import com.orionsoftware.dto.ConsultaResumenDTO;
import com.orionsoftware.dto.FiltroConsultaDTO;
import com.orionsoftware.model.Consulta;

import java.util.List;

public interface IConsultaService extends ICRUD<Consulta> {

    Consulta registrarTransaccional(ConsultaListaExamenDTO consultaDTO);

    List<Consulta> buscar(FiltroConsultaDTO filtro);
    List<Consulta> buscarByFecha(FiltroConsultaDTO filtro);

    List<ConsultaResumenDTO> listarResumen();

    byte[] generarReporte();
}
