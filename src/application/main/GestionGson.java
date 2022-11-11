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
	
	public boolean registrarUsuario(Usuario u) {
		//TODO: SE REGISTRA PERO NO CORRECTAMENTE EN JSON
		GestionGson gg = new GestionGson();
		return gg.serializarArrayAJson(u);
		
	}
	
	private boolean serializarArrayAJson(Usuario u) {
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		
		try(FileWriter writer = new FileWriter("data/usuarios.json", true)){
			prettyGson.toJson(u, writer);
			return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
		
	}
	
	private Vector<Usuario> deserializarJsonArray() {
		Vector<Usuario> Usuarios = new Vector<Usuario>();
		
		try (Reader reader = new FileReader("Usuarios.json")) {
			Gson gson = new Gson();
			Type tipoListaUsuarios = new TypeToken<Vector<Usuario>>(){}.getType();
			Usuarios = gson.fromJson(reader, tipoListaUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return Usuarios;
	}
	
	

}
