package com.orionsoftware.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.springframework.hateoas.Resource;

import javax.persistence.*;
import javax.validation.constraints.Size;

@ApiModel(description = "Informacion del paciente")
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    @ApiModelProperty(notes = "Nombres de tener minimo 3 caracteres")
    @Size(min = 3, message = "Nombres de tener minimo 3 caracteres")
    @Column(name = "nombres", nullable = false, length = 70)
    private String nombres;

    @Size(min = 3, max = 70, message = "Apellidos debe tener minimo 3 caracteres")
    @Column(name = "apellidos", nullable = false, length = 70)
    private String apellidos;

    @ApiModelProperty(notes = "Documento debe contener minimo 8 digitos")
    @Size(min = 8, message = "Documento debe contener minimo 8 digitos")
    @Column(name = "documento", nullable = false, length = 10)
    private String documento;

    @ApiModelProperty(notes = "Direccion debe tener minimo 3 caracteres")
    @Size(min = 3, max = 150, message = "Direccion debe tener minimo 3 caracteres")
    @Column(name = "direccion", nullable = true, length = 150)
    private String direccion;

    @ApiModelProperty(notes = "Telefono debe tener minimo 3 digitos")
    @Size(min = 3, max = 10, message = "Telefono debe tener minimo 3 digitos")
    @Column(name = "telefono", nullable = true, length = 15)
    private String telefono;

    @Email(message = "Debe ser un correo valido")
    @Column(name = "email", nullable = true, length = 50)
    private String email;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
