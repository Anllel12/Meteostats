package application.database;

import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.model.Usuario;

public class GestionUsuariosBBDD {
	public final static int ROL_USUARIO = 1;
	public final static int ROL_ADMIN = 2;
	public final static int ROL_TECNICO = 3;
	public final static int REG_ERROR_MISMO_USUARIO = 1;
	public final static int REG_ERROR_ESCRITURA = 2;
	public final static int REG_OK = 0;
	public final static int LOG_USUARIO_NO_ENCONTRADO = 1;
	public final static int LOG_CONTRA_INCORRECTA = 2;
	public final static int LOG_OK = 0;
	private Usuario UsuarioActual = null;
	
	public int registrarUsuario(Usuario u) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "INSERT OR IGNORE INTO usuario(id_usuario, usuario, nombre, apellido, contrasena, email, rol) "
				+ "VALUES (0, ?, ?, ?, ?, ?, ?)";
		Connection connection = checkConnection(mdb);
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, u.getUsuario());
			preparedStatement.setString(2, u.getNombre());
			preparedStatement.setString(3, u.getApellido());
			preparedStatement.setString(4, u.getContrasena());
			preparedStatement.setString(5, u.getEmail());
			preparedStatement.setInt(6, u.getRol());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.commit();
			return REG_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return REG_ERROR_ESCRITURA;
		}
	}
	
	private Connection checkConnection(MariaDBConnectionService mdb) {
		if (mdb.connection == null) {
			return mdb.connectDB();
		} else {
			return mdb.connection;
		}
	}

	public Vector<Integer> getNombreUsuarioByRol(int rol) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT id_usuario FROM usuario WHERE rol = %d";
		String.format(query, rol);
		Vector<Integer> usuByRol = new Vector<Integer>();
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				usuByRol.add(rs.getInt("id_usuario"));
			}
			rs.close();
			statement.close();
			return usuByRol;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return usuByRol;
		}
		
		
	}
	
	public int loginUsuario(String usuario, String contra) {
		
	}
	
	public Usuario getUsuarioActual() {
		return UsuarioActual;
	}
	
	public Usuario getUserByUsername(String username) {
		
	}
	
	public Vector<Usuario> getUsersContainingAdminUsername(String usernameAdmin) {
		
	}
	
	private int serializarArrayAJson(Usuario u) {
				
	}
	
	private Vector<Usuario> deserializarJsonArray() {
		
	}

}
