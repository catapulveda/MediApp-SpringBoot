package com.orionsoftware.model;

import javax.persistence.*;

/**
 * Esta clase define la estruc
 *
 */

@Entity
@IdClass(ConsultaExamenPK.class)//Es para referencias la clase que va a representar los detalles de este esquema de la tabla
public class ConsultaExamen {

    @Id
    @Column(name = "fk_consulta_examen_consulta", nullable = false)
    private Consulta consulta;

    @Id
    private Examen examen;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }
}
