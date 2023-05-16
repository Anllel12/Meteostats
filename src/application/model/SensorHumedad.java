package application.model;

public class SensorHumedad {

	private int humedad;

	public SensorHumedad() {
		
	}
	
	public SensorHumedad(int humedad) {
		this.humedad = humedad;
	}


	@Override
	public String toString() {
		return "SensorHumedad [humedad=" + humedad + "]";
	}	

	public int getHumedad() {
		return humedad;
	}

	public void setHumedad(int humedad) {
		this.humedad = humedad;
	}


	
	
}
