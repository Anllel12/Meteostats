package application.model;

public class SensorPresion {

	private int presion;

	public SensorPresion() {
	}

	
	public SensorPresion(int presion) {
		this.presion = presion;
	}


	@Override
	public String toString() {
		return "SensorPresion [presion=" + presion + "]";
	}

	public int getPresion() {
		return presion;
	}

	public void setPresion(int presion) {
		this.presion = presion;
	}



	
	
}
