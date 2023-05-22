package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBConnectionService {
	public Connection connection = null;
	public Connection connectDB() {
		String ip = "195.235.211.197"; //195.235.211.197 - localhost
		String port = "3306";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String db = String.format("jdbc:mariadb://%s:%s/primeteostats", ip, port); //primeteostats - pi2_bd_meteostats
			String user = "pri_meteostats"; //pri_meteostats
			String pass = "pri_meteostats"; //pri_meteostats
			connection = DriverManager.getConnection(db, user, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}