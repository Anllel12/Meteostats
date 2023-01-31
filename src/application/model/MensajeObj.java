package application.model;

import java.util.Vector;

public class MensajeObj {

	private String descripcion;
	private int from;
	private Vector<String> to;
	private int status;
	private String fecha;
	
	@Override
	public String toString() {
		return "MensajeObj [descripcion=" + descripcion + ", from=" + from + ", to=" + to + ", status=" + status
				+ ", fecha=" + fecha + "]";
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public Vector<String> getTo() {
		return to;
	}
	public void setTo(Vector<String> to) {
		this.to = to;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public MensajeObj(String descripcion, int from, Vector<String> to, int status, String fecha) {
		super();
		this.descripcion = descripcion;
		this.from = from;
		this.to = to;
		this.status = status;
		this.fecha = fecha;
	}
	
	
	
}
