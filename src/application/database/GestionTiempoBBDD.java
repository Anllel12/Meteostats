package application.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

		//String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'temp' AND usuario = '"+ gestionUsuarios.getUsuarioById(4) + "'";
		String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'temp'";
		
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
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'humedad'"
				+ "";
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
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'presion'";
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
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT lectura1 FROM sensores WHERE tipo_sensor = 'AmaAtar'";
		Connection connection = checkConnection(mdb);
		Statement statement;

		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {

				
				int amanecer = rs.getInt("lectura1");
				int atardecer = rs.getInt("lectura1");
				
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

		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT fecha FROM sensores";
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
		String ubicacion = gUsuario.getUsuarioById(4);
	    // Obtener las observables listas con la información de los sensores
	    ObservableList<SensorTemp> temperaturaObsList = getTemperatura1();
	    ObservableList<SensorHumedad> humedadObsList = getHumedad();
	    ObservableList<SensorPresion> presionObsList = getPresion();
	    ObservableList<SensorAmaAtar> amaAtarObsList = getAmaAtar();
	    ObservableList<SensorAmaAtar> amaAtarObsList2 = getAmaAtar();
	    ObservableList<SensorHora> horaObsList = getHora();

	    if(ubicacion == null) {
	    	ubicacion = "Ubicacion desconocida";
	    }
	    
	    // Crear listas de sensores para almacenar los valores de los sensores
	    List<SensorTemp> temperaturaList = new ArrayList<>();
	    List<SensorHumedad> humedadList = new ArrayList<>();
	    List<SensorPresion> presionList = new ArrayList<>();
	    List<SensorAmaAtar> amaAtarList = new ArrayList<>();
	    List<SensorAmaAtar> amaAtarList2 = new ArrayList<>();
	    List<SensorHora> horaList = new ArrayList<>();

	    // Recorrer cada lista de observables y obtener los valores de cada sensor
	    for (SensorTemp temp : temperaturaObsList) {
	        temperaturaList.add(temp);
	    }

	    for (SensorHumedad hum : humedadObsList) {
	        humedadList.add(hum);
	    }

	    for (SensorPresion pres : presionObsList) {
	        presionList.add(pres);
	    }

	    for (SensorAmaAtar ama : amaAtarObsList) {
	        amaAtarList.add(ama);
	    }

	    for (SensorAmaAtar ama2 : amaAtarObsList2) {
	        amaAtarList2.add(ama2);
	    }

	    for (SensorHora hora : horaObsList) {
	        horaList.add(hora);
	    }

	    // Crear el objeto TiempoObj con la ultma informacion
	    TiempoObj tiempo = new TiempoObj(ubicacion, 
	                                      temperaturaList.get(temperaturaList.size() - 1).getTemperatura(),
	                                      presionList.get(presionList.size() - 1).getPresion(),
	                                      humedadList.get(humedadList.size() - 1).getHumedad(),
	                                      amaAtarList.get(amaAtarList.size() - 2).getAmanacer(),
	                                      amaAtarList2.get(amaAtarList2.size() - 1).getAtardecer(),
	                                      horaList.get(horaList.size() - 1).getHora());
	    return tiempo;
	}
	


	
	  public List<String> obtenerUsuariosSensores() throws SQLException {
	        List<String> usuarios = new ArrayList<>();
			MariaDBConnectionService mdb = new MariaDBConnectionService();
			Connection connection = checkConnection(mdb);

	        String sql = "SELECT usuario FROM sensores";

	        Statement statement = connection.createStatement();

	        ResultSet resultSet = statement.executeQuery(sql);

	        while (resultSet.next()) {
	            String usuario = resultSet.getString("usuario");
	            usuarios.add(usuario);
	        }

	        resultSet.close();
	        statement.close();

	        return usuarios;
	    }
	  
}


