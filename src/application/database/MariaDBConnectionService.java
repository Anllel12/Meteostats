package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
<<<<<<< HEAD
import java.sql.Statement;
=======
>>>>>>> 492da052cc6670ceb70cc9473e15f4e39cec2c89

public class MariaDBConnectionService {
	public Connection connection = null;
	public Connection connectDB() {
<<<<<<< HEAD
		String ip = "localhost"; //127.0.0.1
		String port = "3306";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String db = String.format("jdbc:mariadb://%s:%s/pi2_bd_meteostats", ip, port);
			String user = "pi2_meteostats";
			String pass = "pi2_meteostats";
=======
		String ip = "195.235.211.197"; //195.235.211.197 - localhost
		String port = "3306";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String db = String.format("jdbc:mariadb://%s:%s/primeteostats", ip, port); //primeteostats - pi2_bd_meteostats
			String user = "pri_meteostats"; //pri_meteostats
			String pass = "pri_meteostats"; //pri_meteostats
>>>>>>> 492da052cc6670ceb70cc9473e15f4e39cec2c89
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
