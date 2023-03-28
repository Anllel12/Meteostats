package application.model;

import java.sql.Timestamp;

public class MensajeObj {

	private String descripcion;
	private int from;
	private int to;
	private int status;
	private Timestamp fecha;
	
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
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public MensajeObj(String descripcion, int from, int to, int status, Timestamp fecha) {
		super();
		this.descripcion = descripcion;
		this.from = from;
		this.to = to;
		this.status = status;
		this.fecha = fecha;
	}
	
	
	
}
