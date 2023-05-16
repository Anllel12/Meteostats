package application.model;

import java.sql.Timestamp;

public class SensorHora {

	private Timestamp hora;

	
	public SensorHora(Timestamp hora) {
		super();
		this.hora = hora;
	}


	@Override
	public String toString() {
		return "SensorHora [hora=" + hora + "]";
	}


	public Timestamp getHora() {
		return hora;
	}


	public void setHora(Timestamp hora) {
		this.hora = hora;
	}
		
	
}
