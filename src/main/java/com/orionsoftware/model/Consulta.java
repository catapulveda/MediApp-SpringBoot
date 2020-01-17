package com.orionsoftware.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsulta;

    //Un paciente puede tener muchas consultas
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false,  foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false,  foreignKey = @ForeignKey(name = "fk_consulta_medico"))
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false,  foreignKey = @ForeignKey(name = "fk_consulta_especialidad"))
    private Especialidad especialidad;

    @JsonSerialize(using = ToStringSerializer.class)//para conversar el texto de la hora desde el front al back... respeta el formato ISO estandar
    private LocalDateTime fecha;

    /*mappedBy = "consulta" -> Representa al nombre del atributo en la clase DetalleConsulta, es decir la llave foranea*/
    @OneToMany(mappedBy = "consulta",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},//temas de atomicidad para que suceda lo mismo en ambas tablas
            fetch = FetchType.LAZY,//No traera los datos de detalles, en este caso, solo trae la consulta pero no la lista de detalles de la consulta
            orphanRemoval = true)//permite dejar eliminar algun registro de la lista de detalles
    public List<DetalleConsulta> detalleConsulta;

    public List<DetalleConsulta> getDetalleConsulta() {
        return detalleConsulta;
    }

    public void setDetalleConsulta(List<DetalleConsulta> detalleConsulta) {
        this.detalleConsulta = detalleConsulta;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consulta)) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(getIdConsulta(), consulta.getIdConsulta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdConsulta());
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "idConsulta=" + idConsulta +
                ", paciente=" + paciente +
                ", medico=" + medico +
                ", especialidad=" + especialidad +
                ", fecha=" + fecha +
                ", detalleConsulta=" + detalleConsulta +
                '}';
    }
}
