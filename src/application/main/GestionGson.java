package application.main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GestionGson {
	public final static int ROL_USUARIO = 1;
	public final static int ROL_ADMIN = 2;
	public final static int ROL_TECNICO = 3;
	private final static String RUTA_JSON = "data/usuarios.json";
	public final static int REG_ERROR_MISMO_USUARIO = 1;
	public final static int REG_ERROR_ESCRITURA = 2;
	public final static int REG_OK = 0;
	public final static int LOG_USUARIO_NO_ENCONTRADO = 1;
	public final static int LOG_CONTRA_INCORRECTA = 2;
	public final static int LOG_OK = 0;
	private Usuario UsuarioActual = null;
	
	
	public int registrarUsuario(Usuario u) {
		GestionGson gg = new GestionGson();
		return gg.serializarArrayAJson(u);
		
	}
	
	public int loginUsuario(String usuario, String contra) {
		Usuario us;
		if ((us = getUserByUsername(usuario)) == null) {
			return LOG_USUARIO_NO_ENCONTRADO;
		} else {
			if (us.getContrasena().equals(contra)) {
				UsuarioActual = us;
				return LOG_OK;
			} else {
				return LOG_CONTRA_INCORRECTA;
			}
		}
	}
	
	public Usuario getUsuarioActual() {
		return UsuarioActual;
	}
	
	public Usuario getUserByUsername(String username) {
		Vector<Usuario> usuarios = deserializarJsonArray();
		for (Usuario us : usuarios) {
			if (us.getUsuario().equals(username)) {
				return us;
			}
		}
		return null;
	}
	
	private int serializarArrayAJson(Usuario u) {
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		Vector<Usuario> usuarios = deserializarJsonArray();
		for (Usuario us : usuarios) {
			if (us.getUsuario().equals(u.getUsuario())) {
				return REG_ERROR_MISMO_USUARIO;
			}
		}
		usuarios.add(u);
		try(FileWriter writer = new FileWriter(RUTA_JSON)){
			prettyGson.toJson(usuarios, writer);
			return REG_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return REG_ERROR_ESCRITURA;
        }		
	}
	
	private Vector<Usuario> deserializarJsonArray() {
		Vector<Usuario> Usuarios = new Vector<Usuario>();
		
		try (Reader reader = new FileReader(RUTA_JSON)) {
			Gson gson = new Gson();
			Type tipoListaUsuarios = new TypeToken<Vector<Usuario>>(){}.getType();
			Usuarios = gson.fromJson(reader, tipoListaUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return Usuarios;
	}
}
