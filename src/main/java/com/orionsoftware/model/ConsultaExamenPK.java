package com.orionsoftware.model;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable//Para que pueda ser llamada o embebida desde la referencia del esquema de la tabla  ConsultaExamen
public class ConsultaExamenPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_examen", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_examen_examen"))
    private Examen examen;

    @ManyToOne
    @JoinColumn(name = "id_consulta", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_examen_consulta"))
    private Consulta consulta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsultaExamenPK)) return false;
        ConsultaExamenPK that = (ConsultaExamenPK) o;
        return examen.equals(that.examen) &&
                consulta.equals(that.consulta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examen, consulta);
    }
}
