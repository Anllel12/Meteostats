package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDBConnectionService {
	public Connection connection = null;
	public Connection connectDB() {
		String ip = "localhost"; //127.0.0.1
		String port = "3306";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String db = String.format("jdbc:mariadb://%s:%s/pi2_bd_meteostats", ip, port);
			String user = "pi2_meteostats";
			String pass = "pi2_meteostats";
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
