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
	public final static int USUARIO_NO_ENCONTRADO = 1;
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
		String query = "SELECT id_usuario FROM usuario WHERE rol=%d";
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
			e.printStackTrace();
			return usuByRol;
		}
		
		
	}
	
	public int getIdUsuarioByUsuario(String usuario) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT id_usuario FROM usuario WHERE usuario='%s'";
		String.format(query, usuario);
		int idUsuarioRes;
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			idUsuarioRes = rs.getInt("id_usuario");
			rs.close();
			statement.close();
			return idUsuarioRes;
		} catch (SQLException e) {
			e.printStackTrace();
			return USUARIO_NO_ENCONTRADO;
		}
		
		
	}
	
	public Vector<Usuario> getUsuarios() {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT * FROM usuario";
		Connection connection = checkConnection(mdb);
		
		Vector<Usuario> usuarios = new Vector<Usuario>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				String usuario = rs.getString("usuario");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String contrasena = rs.getString("contrasena");
				String email = rs.getString("email");
				int rol = rs.getInt("rol");
				usuarios.add(new Usuario(usuario, nombre, apellido, contrasena, email, rol));
			}
			rs.close();
			statement.close();
			return usuarios;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int updateUsuario(Usuario us, int newRol) {
		int usId = getIdUsuarioByUsuario(us.getUsuario());
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "UPDATE usuario SET rol=%d WHERE id_usuario=%d";
		Connection connection = checkConnection(mdb);
		String.format(query, newRol, usId);
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.execute();
			return REG_OK;
		} catch (SQLException e) {
			e.printStackTrace();
			return REG_ERROR_ESCRITURA;
		}
		
		
	}
	
	public Usuario loginUsuario(String us, String contra) {
		Usuario resUsuario = null;
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = String.format("SELECT * FROM usuario WHERE (usuario='%s' AND contrasena='%s')", us, contra);
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				String usuario = rs.getString("usuario");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String contrasena = rs.getString("contrasena");
				String email = rs.getString("email");
				int rol = rs.getInt("rol");
				resUsuario = new Usuario(usuario, nombre, apellido, contrasena, email, rol);
			}
			rs.close();
			statement.close();
			return resUsuario;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Usuario getUsuarioActual() {
		return UsuarioActual;
	}
	
	public Usuario getUserByUsername(String username) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT * FROM usuario WHERE usuario='%s'";
		Connection connection = checkConnection(mdb);
		
		Usuario resUs = null;
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				String usuario = rs.getString("usuario");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				String contrasena = rs.getString("contrasena");
				String email = rs.getString("email");
				int rol = rs.getInt("rol");
				resUs = new Usuario(usuario, nombre, apellido, contrasena, email, rol);
			}
			rs.close();
			statement.close();
			return resUs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<Vector<String>> getUsuarioAndIdByRol(int rol) {
		Vector<Vector<String>> vectorUsuarioAndId = new Vector<Vector<String>>();
		
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = String.format("SELECT usuario, id_usuario FROM usuario WHERE rol=%d", rol);
		
		Connection connection = checkConnection(mdb);
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				Vector<String> vectorAux = new Vector<String>();
				vectorAux.add(rs.getString("usuario"));
				vectorAux.add(String.valueOf(rs.getInt("id_usuario")));
				vectorUsuarioAndId.add(vectorAux);
			}
			rs.close();
			statement.close();
			return vectorUsuarioAndId;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Vector<Usuario> getUsersContainingAdminUsername(String usernameAdmin) {
		
	}

}
