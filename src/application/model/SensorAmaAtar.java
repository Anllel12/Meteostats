package application.model;

import java.sql.Timestamp;

public class SensorAmaAtar {

	private String amanacer;
	private String atardecer;
	
	
	public SensorAmaAtar(String amanacer, String atardecer) {
		super();
		this.amanacer = amanacer;
		this.atardecer = atardecer;
	}


	public String getAmanacer() {
		return amanacer;
	}


	public void setAmanacer(String amanacer) {
		this.amanacer = amanacer;
	}


	public String getAtardecer() {
		return atardecer;
	}


	public void setAtardecer(String atardecer) {
		this.atardecer = atardecer;
	}
	
	
	
}
