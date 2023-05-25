package application.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.MariaDBConnectionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SensorTemp {

	private int temperatura;
	
	
	public SensorTemp() {
	}

	public SensorTemp(int temperatura) {
		this.temperatura = temperatura;
	}

	@Override
	public String toString() {
		return "SensorTemp [temperatura=" + temperatura + "]";
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}
	

}
