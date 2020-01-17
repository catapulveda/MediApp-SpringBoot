package com.orionsoftware.dto;

//CLASE PARA REPRESENTAR LOS DATOS DE LA GRAFICA. SE CREA UN DTO PORQUE ES MAS FACIL EL MANEJO DE LOS DATOS QUE DEVUELVE EL PROCEDIMIENTO ALMACENADO EN EL SERVIDOR POSTGRES
public class ConsultaResumenDTO {

	private Integer cantidad;
	private String fecha;

	public ConsultaResumenDTO() {

	}

	public ConsultaResumenDTO(Integer cantidad, String fecha) {
		super();
		this.cantidad = cantidad;
		this.fecha = fecha;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
