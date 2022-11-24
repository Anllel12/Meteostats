package application.main;

public class Tiempo {
	private String ubicacion;
	private int temperatura;
	private int presion;
	private int humedad;
	private int amanacer;
	private int atardecer;
	private int hora;
	private int tiempoFuncionando;
	
	@Override
	public String toString() {
		return "Tiempo [ubicacion=" + ubicacion + ", temperatura=" + temperatura + ", presion=" + presion + ", humedad="
				+ humedad + ", amanacer=" + amanacer + ", atardecer=" + atardecer + ", hora=" + hora + ", tiempoFuncionando=" + tiempoFuncionando + "]";
	}

	public Tiempo(String ubicacion, int temperatura, int presion, int humedad, int amanacer, int atardecer, int hora, int tiempoFuncionando) {
		super();
		this.ubicacion = ubicacion;
		this.temperatura = temperatura;
		this.presion = presion;
		this.humedad = humedad;
		this.amanacer = amanacer;
		this.atardecer = atardecer;
		this.atardecer = hora;
		this.atardecer = tiempoFuncionando;
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
	public int getAmanacer() {
		return amanacer;
	}
	public void setAmanacer(int amanacer) {
		this.amanacer = amanacer;
	}
	public int getAtardecer() {
		return atardecer;
	}
	public void setAtardecer(int atardecer) {
		this.atardecer = atardecer;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getTiempoFuncionando() {
		return tiempoFuncionando;
	}
	public void setTiempoFuncionando(int tiempoFuncionando) {
		this.tiempoFuncionando = tiempoFuncionando;
	}
}
