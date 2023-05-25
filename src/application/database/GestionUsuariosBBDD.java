package application.database;

import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.control.LogInController;
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
		String query = "INSERT IGNORE INTO usuario(id_usuario, usuario, nombre, apellido, contrasena, email, rol) "
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

	public Vector<Integer> getIdUsuariosByRol(int rol) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT id_usuario FROM usuario WHERE rol=%d";
		query = String.format(query, rol);
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
	
	public int setAdminACargoUsuario(Usuario usuario, Vector<Integer> ids_admin) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "INSERT IGNORE INTO admin(id_admin, usuario) "
				+ "VALUES (?, ?)";
		int idUsuario = getIdUsuarioByUsuario(usuario.getUsuario());
		Connection connection = checkConnection(mdb);
		PreparedStatement preparedStatement;
		try {
			for (Integer idAdmin : ids_admin) {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, idAdmin);
				preparedStatement.setInt(2, idUsuario);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.commit();
			}
			return REG_OK;
		} catch (SQLException e) {
			e.printStackTrace();
			return REG_ERROR_ESCRITURA;
		}
	}
	
	public int setTecnicoACargoUsuario(Usuario usuario, Vector<Integer> ids_tecnico) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "INSERT IGNORE INTO tecnico(id_tecnico, usuario) "
				+ "VALUES (?, ?)";
		int idUsuario = getIdUsuarioByUsuario(usuario.getUsuario());
		Connection connection = checkConnection(mdb);
		PreparedStatement preparedStatement;
		try {
			for (Integer idTecnico : ids_tecnico) {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, idTecnico);
				preparedStatement.setInt(2, idUsuario);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.commit();
			}
			return REG_OK;
		} catch (SQLException e) {
			e.printStackTrace();
			return REG_ERROR_ESCRITURA;
		}
	}
	
	public Vector<Integer> getTecnicoACargoUsuario(String usuario) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		int id_usuario = getIdUsuarioByUsuario(usuario);
		System.out.println(usuario + " " + id_usuario);
		String query = String.format("SELECT id_tecnico FROM tecnico WHERE usuario=%d", id_usuario);
		Vector<Integer> tecnicosIds = new Vector<Integer>();
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				tecnicosIds.add(rs.getInt("id_tecnico"));
			}
			rs.close();
			statement.close();
			System.out.println(String.valueOf(tecnicosIds));
			return tecnicosIds;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<Vector<String>> getUsuariosACargoTecnico(String tecnico) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		int id_tecnico = getIdUsuarioByUsuario(tecnico);
		System.out.println(tecnico + " " + id_tecnico);
		String query = String.format("SELECT usuario FROM tecnico WHERE id_tecnico=%d", id_tecnico);
		Vector<Vector<String>> clienteAndIds = new Vector<Vector<String>>();
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Vector<String> vAux = new Vector<String>();
				int idUsuario = rs.getInt("usuario");
				vAux.add(getUsuarioById(idUsuario));
				vAux.add(String.valueOf(idUsuario));
				clienteAndIds.add(vAux);
			}
			rs.close();
			statement.close();;
			return clienteAndIds;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int deleteCliente(String cliente) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		int id_usuario = getIdUsuarioByUsuario(cliente);
		String query = String.format("DELETE FROM usuario WHERE id_usuario=%d", id_usuario);
		Connection connection = checkConnection(mdb);
		Statement statement;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public int deleteHistorial(String cliente) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		int id_usuario = getIdUsuarioByUsuario(cliente);
		String query = String.format("DELETE FROM sensores WHERE usuario=%d", id_usuario);
		Connection connection = checkConnection(mdb);
		Statement statement;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public String getUsuarioById(int id) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = String.format("SELECT usuario FROM usuario WHERE id_usuario=%d", id);
		String usuario = "";
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				usuario = rs.getString("usuario");
			}
			rs.close();
			statement.close();
			return usuario;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<String> getAdminsACargoUsuario(String cliente) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		int id_usuario = getIdUsuarioByUsuario(cliente);
		String query = String.format("SELECT id_admin FROM admin WHERE usuario=%d", id_usuario);
		Vector<String> adminsACargo = new Vector<String>();
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				adminsACargo.add(getUsuarioById(rs.getInt("id_admin")));
			}
			rs.close();
			statement.close();
			return adminsACargo;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Vector<Integer> getUsuariosACargoAdmin(String usuario) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		int id_admin = getIdUsuarioByUsuario(usuario);
		String query = String.format("SELECT usuario FROM admin WHERE id_admin=%d", id_admin);
		Vector<Integer> usuariosACargo = new Vector<Integer>();
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				usuariosACargo.add(rs.getInt("usuario"));
			}
			rs.close();
			statement.close();
			return usuariosACargo;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getIdUsuarioByUsuario(String usuario) {
		MariaDBConnectionService mdb = new MariaDBConnectionService();
		String query = "SELECT id_usuario FROM usuario WHERE usuario='%s'";
		query = String.format(query, usuario);
		int idUsuarioRes;
		Connection connection = checkConnection(mdb);
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				idUsuarioRes = rs.getInt("id_usuario");
			} else {
				idUsuarioRes = -1;
			}
			
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
		query = String.format(query, newRol, usId);
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.executeUpdate();
			statement.close();
			connection.commit();
			return REG_OK;
		} catch (SQLException e) {
			e.printStackTrace();
			return REG_ERROR_ESCRITURA;
		}
		
		
	}
	
	private Usuario loginUsuario(String us, String contra) {
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
	
	public int loginUsuarioAux(String us, String contra) {
		Usuario res = loginUsuario(us, contra);
		if (res == null) {
			return LOG_CONTRA_INCORRECTA;
		} else {
			UsuarioActual = res;
			return LOG_OK;
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
	
	
	
	public int getIdUsuarioLoggeado() {
		if (LogInController.USUARIO_LOGUEADO != null) {
	        return getIdUsuarioByUsuario(LogInController.USUARIO_LOGUEADO.getUsuario());
	    } else {
	        throw new IllegalStateException("No user is currently logged in.");
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
	
	
	/*public Vector<Usuario> getUsersContainingAdminUsername(String usernameAdmin) {
		
	}
	 */
}
