package application.model;

import java.sql.Timestamp;

public class TiempoObj {
	private String ubicacion;
	private int temperatura;
	private int presion;
	private int humedad;
	private int amanecer;
	private int atardecer;
	private Timestamp hora;
	
	
	
	
	@Override
	public String toString() {
		return "Tiempo [ubicacion=" + ubicacion + ", temperatura=" + temperatura + ", presion=" + presion + ", humedad="
				+ humedad + ", amanacer=" + amanecer + ", atardecer=" + atardecer + ", hora=" + hora + "]";
	}

	public TiempoObj(String ubicacion, int temperatura, int presion, int humedad, int amanacer, int atardecer, Timestamp hora) {
		super();
		this.ubicacion = ubicacion;
		this.temperatura = temperatura;
		this.presion = presion;
		this.humedad = humedad;
		this.amanecer = amanacer;
		this.atardecer = atardecer;
		this.hora = hora;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}
	public int getPresion() {
		return presion;
	}
	public void setPresion(int presion) {
		this.presion = presion;
	}
	public int getHumedad() {
		return humedad;
	}
	public void setHumedad(int humedad) {
		this.humedad = humedad;
	}
	/*public int getAmanecer() {
			return amanecer;
	}*/
	public int getAmanecer() {
	    if (amanecer >= 5 && amanecer <= 10) {
	        return amanecer;
	    } else {
	        return 0;
	    }
	}
	public void setAmanecer(int amanacer) {
		this.amanecer = amanacer;
	}
	public int getAtardecer() {
	    if (amanecer >= 15 && amanecer <= 21) {
	        return amanecer;
	    } else {
	        return 0;
	    }
	}
	/*public int getAtardecer() {
		return atardecer;
	}*/
	public void setAtardecer(int atardecer) {
		this.atardecer = atardecer;
	}
	public Timestamp getHora() {
		return hora;
	}
	public void setHora(Timestamp hora) {
		this.hora = hora;
	}

}







