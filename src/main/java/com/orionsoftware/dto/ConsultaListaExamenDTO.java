package com.orionsoftware.dto;

import com.orionsoftware.model.Consulta;
import com.orionsoftware.model.Examen;

import java.util.List;

public class ConsultaListaExamenDTO {

    private Consulta consulta;
    private List<Examen> listaExamen;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Examen> getListaExamen() {
        return listaExamen;
    }

    public void setListaExamen(List<Examen> listaExamen) {
        this.listaExamen = listaExamen;
    }
}
