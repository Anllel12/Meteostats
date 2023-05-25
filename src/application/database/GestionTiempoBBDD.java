package application.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import application.model.SensorAmaAtar;
import application.model.SensorHora;
import application.model.SensorHumedad;
import application.model.SensorPresion;
import application.model.SensorTemp;
import application.model.TiempoObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestionTiempoBBDD {



	private Connection checkConnection(MariaDBConnectionService mdb) {
		if (mdb.connection == null) {
			return mdb.connectDB();
		} else {
			return mdb.connection;
		}
	}
	
	
	/**
	 * coger datos tipo sensor 1
	 * @return
	 */
	public ObservableList<SensorTemp> getTemperatura1(){
		
		ObservableList<SensorTemp> obs = FXCollections.observableArrayList();
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		GestionUsuariosBBDD gestionUsuarios = new GestionUsuariosBBDD();
	    int usuarioId = gestionUsuarios.getIdUsuarioLoggeado(); // Obtener el ID del usuario logueado

		
		
	    String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'DHT11_temp' AND usuario = " + usuarioId;
		//String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'DHT11_temp'";
		
		Connection connection = checkConnection(mdb);
		Statement statement;

		try {
			
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				int temperatura = rs.getInt("lectura1");
				
				SensorTemp t = new SensorTemp(temperatura);
				
				obs.add(t);
			}
			
			rs.close();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return obs;
	}
	
	
	/**
	 * coger datos tipo sensor 2
	 * @return
	 */
	public ObservableList<SensorHumedad> getHumedad(){
		
		ObservableList<SensorHumedad> obs = FXCollections.observableArrayList();
		GestionUsuariosBBDD gestionUsuarios = new GestionUsuariosBBDD();
	    int usuarioId = gestionUsuarios.getIdUsuarioLoggeado(); // Obtener el ID del usuario logueado

		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'DHT11_humedad' AND usuario = " + usuarioId;

		Connection connection = checkConnection(mdb);
		Statement statement;

		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				int humedad = rs.getInt("lectura1");


				SensorHumedad t = new SensorHumedad(humedad);
				
				obs.add(t);
			}
			
			rs.close();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return obs;
	}
	
	
	/**
	 * obtener datos tipo sensor 3
	 * @return
	 */
	public ObservableList<SensorPresion> getPresion(){
		
		ObservableList<SensorPresion> obs = FXCollections.observableArrayList();
		GestionUsuariosBBDD gestionUsuarios = new GestionUsuariosBBDD();
	    int usuarioId = gestionUsuarios.getIdUsuarioLoggeado(); // Obtener el ID del usuario logueado
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'BMP_presion' AND usuario = " + usuarioId;

		Connection connection = checkConnection(mdb);
		Statement statement;

		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				int presion = rs.getInt("lectura1");

				SensorPresion t = new SensorPresion(presion);
				
				obs.add(t);
			}
			
			rs.close();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return obs;
	}
	
	/**
	 * obtener datos tipo sensor 4
	 * @return
	 */
	public ObservableList<SensorAmaAtar> getAmaAtar(){
		
		ObservableList<SensorAmaAtar> obs = FXCollections.observableArrayList();
		
		GestionUsuariosBBDD gestionUsuarios = new GestionUsuariosBBDD();
	    int usuarioId = gestionUsuarios.getIdUsuarioLoggeado(); // Obtener el ID del usuario logueado
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT fecha FROM sensores WHERE tipo_sensor = 'LDR' AND usuario = " + usuarioId;
		
		Connection connection = checkConnection(mdb);
		Statement statement;

		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
	            Timestamp timestamp = rs.getTimestamp("fecha");
	            LocalDateTime localDateTime = timestamp.toLocalDateTime();
	            int horaAma = localDateTime.getHour();
	            int minutosAma = localDateTime.getMinute();
	            String amanecer = String.format("%02d:%02d", horaAma, minutosAma); // Formato "hora:minutos"
	            
	            
	            int horaAtar = localDateTime.getHour();
	            int minutosAtar = localDateTime.getMinute();
	            String atardecer = String.format("%02d:%02d", horaAtar, minutosAtar); // Formato "hora:minutos"
	            
	            
	            int horaAtardecer = localDateTime.getHour();
	            SensorAmaAtar t = new SensorAmaAtar(amanecer, atardecer);
	            obs.add(t);
			}
			
			rs.close();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return obs;
	}
	
	
	/**
	 * obtener datos tipo sensor 4
	 * @return
	 */
	public ObservableList<SensorHora> getHora(){
		
		ObservableList<SensorHora> obs = FXCollections.observableArrayList();

		GestionUsuariosBBDD gestionUsuarios = new GestionUsuariosBBDD();
	    int usuarioId = gestionUsuarios.getIdUsuarioLoggeado(); // Obtener el ID del usuario logueado
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT fecha FROM sensores WHERE usuario = " + usuarioId;
		Connection connection = checkConnection(mdb);
		Statement statement;

		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {

				
				Timestamp hora = rs.getTimestamp("fecha");//cambiar
				
				SensorHora t = new SensorHora(hora);
				
				obs.add(t);
			}
			
			rs.close();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return obs;
	}
	
	
	public List<TiempoObj> obtenerInformacionTiempo() {
	    List<SensorTemp> temperaturaList = getTemperatura1();
	    List<SensorHumedad> humedadList = getHumedad();
	    List<SensorPresion> presionList = getPresion();
	    List<SensorAmaAtar> amaAtarList = getAmaAtar();
	    List<SensorAmaAtar> amaAtarList2 = getAmaAtar();
	    List<SensorHora> horaList = getHora();
	   
	    
	    List<TiempoObj> tiempos = new ArrayList<>();

	    for (int i = 0; i < temperaturaList.size(); i++) {
	        TiempoObj tiempo = new TiempoObj("Ubicación", temperaturaList.get(i).getTemperatura(),
	                presionList.get(i).getPresion(), humedadList.get(i).getHumedad(), amaAtarList.get(i).getAmanacer(),
	                amaAtarList2.get(i).getAtardecer(), horaList.get(i).getHora());
	        tiempos.add(tiempo);
	    }

	    return tiempos;
	}

	
	
	public TiempoObj obtenerInformacionTiempoUltimo() {
	    GestionUsuariosBBDD gUsuario = new GestionUsuariosBBDD();
	    GestionUsuariosBBDD gestionUsuarios = new GestionUsuariosBBDD();
	    int usuarioId = gestionUsuarios.getIdUsuarioLoggeado(); // Obtener el ID del usuario logueado

	    String ubicacion = gUsuario.getUsuarioById(usuarioId);
	    // Obtener las observables listas con la información de los sensores
	    ObservableList<SensorTemp> temperaturaObsList = getTemperatura1();
	    ObservableList<SensorHumedad> humedadObsList = getHumedad();
	    ObservableList<SensorPresion> presionObsList = getPresion();
	    ObservableList<SensorAmaAtar> amaAtarObsList = getAmaAtar();
	    ObservableList<SensorAmaAtar> amaAtarObsList2 = getAmaAtar();
	    ObservableList<SensorHora> horaObsList = getHora();

	    if (ubicacion.isEmpty()) {
	        ubicacion = "Ubicacion desconocida";
	    }

	    // Crear listas de sensores para almacenar los valores de los sensores
	    List<SensorTemp> temperaturaList = new ArrayList<>();
	    List<SensorHumedad> humedadList = new ArrayList<>();
	    List<SensorPresion> presionList = new ArrayList<>();
	    List<SensorAmaAtar> amaAtarList = new ArrayList<>();
	    List<SensorAmaAtar> amaAtarList2 = new ArrayList<>();
	    List<SensorHora> horaList = new ArrayList<>();

	    temperaturaList.addAll(temperaturaObsList);
	    humedadList.addAll(humedadObsList);
	    presionList.addAll(presionObsList);
	    amaAtarList.addAll(amaAtarObsList);
	    amaAtarList2.addAll(amaAtarObsList2);
	    horaList.addAll(horaObsList);

	    // Obtener los últimos valores de las listas o establecerlos como null si las listas están vacías
	    Integer temperatura = temperaturaList.isEmpty() ? null : temperaturaList.get(temperaturaList.size() - 1).getTemperatura();
	    Integer presion = presionList.isEmpty() ? null : presionList.get(presionList.size() - 1).getPresion();
	    Integer humedad = humedadList.isEmpty() ? null : humedadList.get(humedadList.size() - 1).getHumedad();
	    String amanecer = amaAtarList.size() >= 2 ? amaAtarList.get(amaAtarList.size() - 2).getAmanacer() : null;
	    String atardecer = amaAtarList2.isEmpty() ? null : amaAtarList2.get(amaAtarList2.size() - 1).getAtardecer();
	    Timestamp hora = horaList.isEmpty() ? null : horaList.get(horaList.size() - 1).getHora();

	    // Crear el objeto TiempoObj con la última información
	    TiempoObj tiempo = new TiempoObj(ubicacion, temperatura, presion, humedad, amanecer, atardecer, hora);
	    return tiempo;
	}


	  
}




