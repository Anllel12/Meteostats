package application.database;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.model.GestionGson;
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

//	public int writeNewMessage(String msg, int from, Vector<String> to) {
//		
//	}
//	
//	public int deleteMessage(MensajeObj msg) {
//	}
//	
//	//TODO: 1 mensaje desde 1 usuario y hacia 1 administrador / 1 tecnico
//	public Vector<MensajeObj> getMessages(Usuario us) {
//		MariaDBConnectionService mdb = new MariaDBConnectionService();
//		String query = "SELECT usuario_to FROM usuario WHERE rol = %d";
//		
//	}
//	
//	public int modifyMessage(MensajeObj msg) {
//	}
//	
//	private String generateDate() {
//		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
//		Date date = new Date(System.currentTimeMillis());
//		return formatter.format(date);
//	}
//	
//	private int vectorToJson(Vector<MensajeObj> msgs) {
//	}
//	
//	private int serializarArrayAJson(MensajeObj msg) {
//		
//	}
//	
//	private Vector<MensajeObj> deserializarJsonArray() {
//	}
}
