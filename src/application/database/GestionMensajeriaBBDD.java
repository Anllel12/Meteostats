package application.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import application.model.MensajeObj;
import application.model.Usuario;

public class GestionMensajeriaBBDD {
	public static final int ERROR_ESCRITURA = -1;
	public static final int ESCRITURA_OK = 0;
	public static final int MENS_NO_EXISTE = -1;
	public static final int MENS_MODIFICADO = 0;
	public static final int STATUS_INICIAL = 0;
	public static final int STATUS_PENDIENTE = 1;
	public static final int USER_ADMIN = 2;
	public static final int CLIENTE_SUGERENCIA = 10;
	public static final int CLIENTE_ERROR = 11;
	public static final String TO_ADMIN = "TO_ADMIN_GENERICO"; 
	public static final String TO_TECNICO = "TO_TECNICO_GENERICO";

	public int writeNewMessage(MensajeObj m) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "INSERT IGNORE INTO mensajes(id_mensaje, descripcion, fecha, status) "
				+ "VALUES (0, ?, ?, ?)";
		Connection connection = checkConnection(mdb);
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, m.getDescripcion());
			preparedStatement.setTimestamp(2, m.getFecha());
			preparedStatement.setInt(3, m.getStatus());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.commit();
			writeRelationMessage(m);
			return ESCRITURA_OK;
		} catch (SQLException e) {
			e.printStackTrace();
			return ERROR_ESCRITURA;
		}
		
	}
	
	public int writeRelationMessage(MensajeObj m) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "INSERT IGNORE INTO mensajes(usuario_from, usuario_to, mensaje) "
				+ "VALUES (?, ?, ?)";
		Connection connection = checkConnection(mdb);
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, m.getFrom());
			preparedStatement.setInt(2, m.getTo());
			preparedStatement.setInt(3, getMessagesByTime(m.getFecha()));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.commit();
			return ESCRITURA_OK;
		} catch (SQLException e) {
			e.printStackTrace();
			return ERROR_ESCRITURA;
		}
		
	}
	
//	public int deleteMessage(MensajeObj msg) {
//		
//	}
	
//	//TODO: 1 mensaje desde 1 usuario y hacia 1 administrador / 1 tecnico
//	public Vector<MensajeObj> getMessages(Usuario us) {
//		MariaDBConnectionService mdb = new MariaDBConnectionService();
//		String query = "SELECT usuario_to FROM usuario WHERE rol = %d";
//		
//	}
	
	public int getMessagesByTime(Timestamp time) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT id_mensaje FROM mensajes WHERE fecha = %d";
		String.format(query, time);
		int id = 0;
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				id = rs.getInt("id_mensaje");
			}
			rs.close();
			statement.close();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		}
	}
	
//	public int modifyMessage(MensajeObj msg) {
//	}
	
	private Connection checkConnection(MariaDBConnectionService mdb) {
		if (mdb.connection == null) {
			return mdb.connectDB();
		} else {
			return mdb.connection;
		}
	}
}
